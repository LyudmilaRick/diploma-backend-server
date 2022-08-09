package ru.skypro.homework.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.*;
import ru.skypro.homework.exceptions.WebNotFoundException;
import ru.skypro.homework.models.AdsEntity;
import ru.skypro.homework.models.ComEntity;
import ru.skypro.homework.models.ImgEntity;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ComRepository;
import ru.skypro.homework.repository.ImgRepository;
import ru.skypro.homework.repository.UserRepository;

@Service
public class AdsServiceImpl implements AdsService {

    private final UserRepository users;
    private final ImgRepository images;
    private final ComRepository comments;
    private final AdsRepository advertisement;

    public AdsServiceImpl(UserRepository users, ImgRepository images, ComRepository comments, AdsRepository advertisement) {
        this.users = users;
        this.images = images;
        this.comments = comments;
        this.advertisement = advertisement;
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<AdsEntity> adsEntities = advertisement.findAll();
        return convertAdsEntityListToDtoAdsWrfapper(adsEntities);

    }

    @Override
    public ResponseWrapperAdsDto getMeAds(String username) {
        // получит сущность пользователя по его логину
        UserEntity ue = users.getByUsername(username);
        // найти все объявления по идентификатору пользователя, полученному из его сущности 
        List<AdsEntity> list = advertisement.findByAuthorId(ue.getIdUser());
        // смэппить список сущностей найденных объявлений в обёртку ответа
        return convertAdsEntityListToDtoAdsWrfapper(list);
    }

    @Override
    public FullAdsDto getAds(Integer adsKey) {
        Optional<AdsEntity> result = advertisement.findById(adsKey);
        if (result.isPresent()) {
            FullAdsDto full = convertAdsEntityToDtoFullAds(result.get());
            //ImgEntity image = images.findAllByAdsId(adsKey);
            List<ImgEntity> list = images.findAllByAdsId(adsKey);
            if (!list.isEmpty()) {
                full.setImage(list.get(0).getId());
            }
            return full;
        }
        throw new WebNotFoundException("Ads '" + adsKey + "' not found.");
    }

    @Override
    public AdsDto addAds(String username, CreateAdsDto body) {
        UserEntity author = users.getByUsername(username);
        OffsetDateTime created = LocalDateTime.now().atOffset(ZoneOffset.UTC);
        Double price = body.getPrice().doubleValue();

        AdsEntity entity = new AdsEntity(null, author, created, price, body.getTitle(), body.getDescription());
        AdsEntity result = advertisement.saveAndFlush(entity);

        // связывание сущностей картинки и объявления по ключам, если uuid картинки передан в запросе и она существет в БД
        // если uuid картинки передан в запросе, а в БД её нет, то возвращается ошибка
        String uuidImage = LinkImageToAdsByKey(body.getImage(), result);

        return convertAdsEntityToAdsDto(result, uuidImage);
    }

    @Override
    public AdsDto updateAds(String username, Integer adsKey, AdsDto body) {
        UserEntity author = users.getByUsername(username);

        advertisement.findById(adsKey)
                .orElseThrow(() -> new WebNotFoundException("Ads '" + adsKey + "' not found."));

        AdsEntity entity = new AdsEntity(adsKey, author, null, body.getPrice().doubleValue(), body.getTitle(), null);
        AdsEntity result = advertisement.saveAndFlush(entity);

        // связывание сущностей картинки и объявления по ключам, если uuid картинки передан в запросе и она существет в БД
        // если uuid картинки передан в запросе, а в БД её нет, то возвращается ошибка
        String uuidImage = LinkImageToAdsByKey(body.getImage(), result);

        return convertAdsEntityToAdsDto(result, uuidImage);
    }

    @Override
    public void deleteAds(Integer adsKey) {
        advertisement.findById(adsKey)
                .orElseThrow(() -> new WebNotFoundException("Ads '" + adsKey + "' not found."));

        advertisement.deleteById(adsKey);
        comments.deleteByAdsId(adsKey);
        images.deleteByAdsId(adsKey);
    }

    @Override
    public ResponseWrapperCommentDto getAllComments(Integer adsKey) {
        List<ComEntity> comEntity = comments.getAllByAds(adsKey);
        ResponseWrapperCommentDto comment = new ResponseWrapperCommentDto();
        List<AdsComment> adsComments = new ArrayList<>(comEntity.size());
        for (ComEntity item : comEntity) {
            adsComments.add(convertComEntityToDtoAdsComments(item));
        }
        comment.setCount(comEntity.size());
        comment.setResults(adsComments);
        return comment;
    }

    @Override
    public AdsComment getComment(Integer adsKey, Integer comKey) {
        return convertComEntityToDtoAdsComments(comments.getByKeys(adsKey, comKey));
    }

    @Override
    public AdsComment addComment(String username, Integer adsKey, AdsComment body) {
        AdsEntity ads = advertisement.getReferenceById(adsKey);
        UserEntity author = users.getByUsername(username);
        OffsetDateTime created = body.getCreatedAt() != null ? body.getCreatedAt() : LocalDateTime.now().atOffset(ZoneOffset.UTC);

        ComEntity entity = new ComEntity(null, ads, author, OffsetDateTime.MAX, body.getText());
        entity = comments.save(entity);

        return convertComEntityToDtoAdsComments(entity);
    }

    @Override
    public AdsComment updateComment(String username, Integer adsKey, Integer comKey, AdsComment body) {
        ComEntity entity = comments.getByKeys(adsKey, comKey);
        if (entity == null) {
            throw new WebNotFoundException("Comment '" + adsKey + "' for Ads '" + comKey + "' not found.");
        }

        entity.setText(body.getText());
        entity.setAuthor(users.getByUsername(username));

        OffsetDateTime created = body.getCreatedAt() != null ? body.getCreatedAt() : LocalDateTime.now().atOffset(ZoneOffset.UTC);
        entity.setCreated(created);

        entity = comments.save(entity);
        return convertComEntityToDtoAdsComments(entity);
    }

    @Override
    public void deleteComment(Integer adsKey, Integer comKey) {
        ComEntity entity = comments.getByKeys(adsKey, comKey);
        if (entity == null) {
            throw new WebNotFoundException("Comment '" + adsKey + "' for Ads '" + comKey + "' not found.");
        }

        comments.delete(entity);
    }

    @Override
    public String setImage(String name, long size, byte[] bytes) {
        ImgEntity entity = new ImgEntity();

        entity.setSize(size);
        entity.setTitle(name);
        entity.setContent(bytes);
        entity.setId(UUID.randomUUID().toString());

        ImgEntity result = images.saveAndFlush(entity);
        return result.getId();
    }

    @Override
    public byte[] getImage(String imgKey) {
        ImgEntity image = images.getByKey(imgKey);
        if (image != null) {
            return image.getContent();
        }

        return null;
    }

    private String LinkImageToAdsByKey(String uuid, AdsEntity result) throws WebNotFoundException {
        if (result != null) {
            if (uuid != null && uuid.length() > 0) {
                ImgEntity imgEntity = images.getByKey(uuid);
                if (imgEntity == null) {
                    throw new WebNotFoundException("Image '" + uuid + "' not found for Ads '" + result.getTitle() + "'.");
                }
                imgEntity.setAds(result);
                images.save(imgEntity);
                return uuid;
            }
        }
        return null;
    }

    private ResponseWrapperAdsDto convertAdsEntityListToDtoAdsWrfapper(List<AdsEntity> adsEntities) {
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        List<AdsDto> ads = new ArrayList<>(adsEntities.size());
        for (AdsEntity item : adsEntities) {
            ads.add(convertAdsEntityToDtoAds(item));
        }
        wrapperAds.setCount(adsEntities.size());
        wrapperAds.setResults(ads);
        return wrapperAds;
    }

    private AdsDto convertAdsEntityToDtoAds(AdsEntity entity) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(entity.getIdAds());
        adsDto.setAuthor(entity.getAuthor().getIdUser());

        adsDto.setPrice(entity.getPrice().intValue());
        adsDto.setTitle(entity.getTitle());

        List<ImgEntity> list = images.findAllByAdsId(entity.getIdAds());
        if (!list.isEmpty()) {
            adsDto.setImage(list.get(0).getId());
        }

        return adsDto;
    }

    private static AdsComment convertComEntityToDtoAdsComments(ComEntity comment) {
        AdsComment commentDto = new AdsComment();
        commentDto.setPk(comment.getId());
        commentDto.setAuthor(comment.getAuthor().getIdUser());
        commentDto.setCreatedAt(comment.getCreated());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    private static FullAdsDto convertAdsEntityToDtoFullAds(AdsEntity entity) {
        FullAdsDto ads = new FullAdsDto();
        ads.setAuthorFirstName(entity.getAuthor().getFirstName());
        ads.setAuthorLastName(entity.getAuthor().getLastName());
        ads.setEmail(entity.getAuthor().getEmail());
        ads.setPhone(entity.getAuthor().getPhone());
        ads.setPk(entity.getIdAds());
        ads.setPrice(entity.getPrice().intValue());
        ads.setTitle(entity.getTitle());
        ads.setDescription(entity.getDescription());
        //ads.image = null; - берём из репозитория картинок ключ записи.
        return ads;
    }

    private static AdsDto convertAdsEntityToAdsDto(AdsEntity entity, String image) {
        AdsDto ads = new AdsDto();
        ads.setPk(entity.getIdAds());
        ads.setAuthor(entity.getAuthor().getIdUser());
        ads.setPrice(entity.getPrice().intValue());
        ads.setTitle(entity.getTitle());
        ads.setImage(image);
        return ads;
    }

}
