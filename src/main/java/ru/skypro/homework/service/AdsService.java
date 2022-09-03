package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;

public interface AdsService {

    /**
     * Получить список всех объявлений.
     *
     * @return объект, содержащий список объявлений.
     */
    ResponseWrapperAdsDto getAllAds();

    /**
     * Получить список всех собственных объявлений.
     *
     * @param username аккаунт пользователя.
     * @return объект, содержащий список объявлений.
     */
    ResponseWrapperAdsDto getMeAds(String username);

    /**
     * Получить все данные по заданному объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @return объект, содржажий все реквизиты объявления.
     */
    FullAdsDto getAds(Integer adsKey);

    /**
     * Добавляет новоё объявление.
     *
     * @param username аккаунт пользователя.
     * @param body объект с данными из тела запроса.
     * @return объект, содржажий реквизиты объявления.
     */
    AdsDto addAds(String username, CreateAdsDto body);

    /**
     * Изменить данные заданного объявления.
     *
     * @param username аккаунт пользователя.
     * @param adsKey ключ записи объявления.
     * @param body объект с новыми данными из тела запроса.
     * @return объект, содржажий реквизиты объявления.
     */
    AdsDto updateAds(String username, Integer adsKey, AdsDto body);

    /**
     * Удалить объявление по заданному идентификатору.
     *
     * @param adsKey ключ записи объявления.
     */
    void deleteAds(Integer adsKey);

    /**
     * Получить список всех комментариев по заданному объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @return объект, содержащий список всех комментариев.
     */
    ResponseWrapperCommentDto getAllComments(Integer adsKey);

    /**
     * Получить данные комментария по заданному объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @param comKey ключ записи комментария.
     * @return объект, содржажий реквизиты комментария.
     */
    AdsComment getComment(Integer adsKey, Integer comKey);

    /**
     * Добавляет новый комментарий к существующему объявлению.
     *
     * @param username аккаунт пользователя.
     * @param adsKey ключ записи объявления.
     * @param body объект с новыми данными из тела запроса.
     * @return объект, содржажий реквизиты комментария.
     */
    AdsComment addComment(String username, Integer adsKey, AdsComment body);

    /**
     * Изменить содержимое комментария по заданному объявлению.
     *
     * @param username аккаунт пользователя.
     * @param adsKey ключ записи объявления.
     * @param comKey ключ записи комментария.
     * @param body объект с новыми данными из тела запроса.
     * @return объект, содржажий реквизиты комментария.
     */
    AdsComment updateComment(String username, Integer adsKey, Integer comKey, AdsComment body);

    /**
     * Удаляет существующий комментарий к объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @param comKey ключ записи комментария.
     */
    void deleteComment(Integer adsKey, Integer comKey);

    /**
     * Сохраняет на сервере сайта картинку, полученную от клиента.
     * @param name имя файла на фронтенде.
     * @param size размер файла изображения.
     * @param bytes содержимое файла изображения.
     * @return идентификатор, присвоенный изображению при сохранении в БД.
     */
    String setImage(String name, long size, byte[] bytes);

    /**
     * Возвращает клиенту содержимое файла картинки по её ключу.
     * @param imgKey идентификатор изображения в БД.
     * @return содержимое файла изображения.
     */
    byte[] getImage(String imgKey);

    /**
     * Возвращает идентификатор сохраненной картинки по её ключу.
     * как итог операции сохранения этой картинки в БД
     * @param file изображение
     * @return идентиификатор
     */
    String upLoadAdsImg(MultipartFile file);
}
