package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.ComEntity;

import java.util.List;

/**
 *
 */
@Repository
public interface ComRepository extends JpaRepository<ComEntity, Integer> {
    @Query(value = "SELECT * FROM ads_review  WHERE id_ads = ?1", nativeQuery = true)
    List<ComEntity> getAllByAds(Integer adsKey);

    @Query(value = "SELECT * FROM ads_review  WHERE id = ?2 AND id_ads = ?1", nativeQuery = true)
    ComEntity getByKeys(Integer adsKey, Integer comKey);

    @Query(value = "DELETE FROM ads_review WHERE id_ads = ?1", nativeQuery = true)
    int deleteByAdsId(Integer adsKey);
}
