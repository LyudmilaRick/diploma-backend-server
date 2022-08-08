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
import ru.skypro.homework.exception.WebNotFoundException;
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
    public ResponseWrapperAds getAllAds() {
        List<AdsEntity> adsEntities = advertisement.findAll();
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        List<Ads> ads = new ArrayList<>(adsEntities.size());
        for (AdsEntity item : adsEntities) {
            ads.add(convertAdsEntityToDtoAds(item));
        }
        wrapperAds.setCount(adsEntities.size());
        wrapperAds.setResults(ads);
        return wrapperAds;
    }

    @Override
    public ResponseWrapperAds getMeAds(String authority, Object credentials, Object details, Object principal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FullAds getAds(Integer adsKey) {
        Optional<AdsEntity> result = advertisement.findById(adsKey);
        if (result.isPresent()) {
            return new FullAds(result.get());
        }
        throw new WebNotFoundException("Ads '" + adsKey + "' not found.");
    }

    @Override
    public Ads addAds(CreateAds body) {
        UserEntity author = new UserEntity();
        author.setIdUser(1);
        //TODO: Как его получить? Что передается в CreateAds.pk? Может не надо в Entity маписть объект?

        OffsetDateTime created = LocalDateTime.now().atOffset(ZoneOffset.UTC);
        Double price = body.getPrice().doubleValue();

        AdsEntity entity = new AdsEntity(null, author, created, price, body.getTitle(), body.getDescription());
        AdsEntity result = advertisement.saveAndFlush(entity);
        return convertAdsEntityToDtoAds(result);
    }

    @Override
    public Ads updateAds(Integer adsKey, Ads body) {
        UserEntity author = users.getReferenceById(body.getAuthor());
        advertisement.findById(body.getPk())
                .orElseThrow(() -> new WebNotFoundException("Ads '" + body.getPk() + "' not found."));

        AdsEntity entity = new AdsEntity(body.getPk(), author, null, body.getPrice().doubleValue(), body.getTitle(), null);
        AdsEntity result = advertisement.saveAndFlush(entity);
        return convertAdsEntityToDtoAds(result);
    }

    @Override
    public void deleteAds(Integer key) {
        advertisement.deleteById(key);
    }

    @Override
    public ResponseWrapperComment getAllComments(Integer adsKey) {
        List<ComEntity> comEntity = comments.getAllByAds(adsKey);
        ResponseWrapperComment comment = new ResponseWrapperComment();
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
    public AdsComment addComment(Integer adsKey, AdsComment body) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AdsComment updateComment(Integer adsKey, Integer comKey, AdsComment body) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Void deleteComment(Integer adsKey, Integer comKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setImage(String name, long size, byte[] bytes) {
        ImgEntity entity = new ImgEntity();

        entity.setSize(size);
        entity.setTitle(name);
        entity.setContext(bytes);
        entity.setId(UUID.randomUUID().toString());

        ImgEntity result = images.saveAndFlush(entity);
        return result.getId();
    }

    @Override
    public byte[] getImage(String imgKey) {
        return images.getByKey(imgKey);
    }

    private AdsComment convertComEntityToDtoAdsComments(ComEntity comment) {
        AdsComment commentDto = new AdsComment();
        commentDto.setPk(comment.getId());
        commentDto.setAuthor(comment.getAuthor().getIdUser());
        commentDto.setCreatedAt(comment.getCreated());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    private Ads convertAdsEntityToDtoAds(AdsEntity entity) {
        Ads adsDto = new Ads();
        adsDto.setPk(entity.getIdAds());
        adsDto.setAuthor(entity.getAuthor().getIdUser());

        adsDto.setPrice(entity.getPrice().intValue());
        adsDto.setTitle(entity.getTitle());
        //TODO: Как сформировать ссылку на кортинку?
        adsDto.setImage("");
        return adsDto;
    }
}
