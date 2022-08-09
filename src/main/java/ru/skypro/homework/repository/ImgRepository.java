package ru.skypro.homework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.skypro.homework.models.ImgEntity;

/**
 *
 */
@Repository
public interface ImgRepository extends JpaRepository<ImgEntity, Integer> {

    @Query(value = "SELECT * FROM ads_images WHERE id = ?1", nativeQuery = true)
    public ImgEntity getByKey(String imgKey);

    @Query(value = "SELECT * FROM ads_images WHERE id_ads = ?1", nativeQuery = true)
    public List<ImgEntity> findAllByAdsId(Integer adsKey);

    @Query(value = "DELETE FROM ads_images WHERE id_ads = ?1", nativeQuery = true)
    int deleteByAdsId(Integer adsKey);
}
