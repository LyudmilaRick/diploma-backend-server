package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.dto.ResponseWrapperComment;

public interface AdsService {

    /**
     * Получить список всех объявлений.
     *
     * @return объект, содержащий список объявлений.
     */
    ResponseWrapperAds getAllAds();

    /**
     * Получить список всех собственных объявлений.
     *
     * @return объект, содержащий список объявлений.
     */
    ResponseWrapperAds getMeAds(String authority, Object credentials, Object details, Object principal);

    /**
     * Получить все данные по заданному объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @return объект, содржажий все реквизиты объявления.
     */
    FullAds getAds(Integer adsKey);

    /**
     * Добавляет новоё объявление.
     *
     * @param body объект с данными из тела запроса.
     * @return объект, содржажий реквизиты объявления.
     */
    Ads addAds(CreateAds body);

    /**
     * Изменить данные заданного объявления.
     *
     * @param adsKey ключ записи объявления.
     * @param body объект с новыми данными из тела запроса.
     * @return объект, содржажий реквизиты объявления.
     */
    Ads updateAds(Integer adsKey, Ads body);

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
    ResponseWrapperComment getAllComments(Integer adsKey);

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
     * @param adsKey ключ записи объявления.
     * @param body объект с новыми данными из тела запроса.
     * @return объект, содржажий реквизиты комментария.
     */
    AdsComment addComment(Integer adsKey, AdsComment body);

    /**
     * Изменить содержимое комментария по заданному объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @param comKey ключ записи комментария.
     * @param body объект с новыми данными из тела запроса.
     * @return объект, содржажий реквизиты комментария.
     */
    AdsComment updateComment(Integer adsKey, Integer comKey, AdsComment body);

    /**
     * Удаляет существующий комментарий к объявлению.
     *
     * @param adsKey ключ записи объявления.
     * @param comKey ключ записи комментария.
     * @return пустой объект.
     */
    Void deleteComment(Integer adsKey, Integer comKey);

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

}
