package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.ImgEntity;

/**
 *
 */
@Repository
public interface ImgRepository extends JpaRepository<ImgEntity, Integer> {
    @Query(value = "SELECT  context  FROM ads_images WHERE id_ads = ?1", nativeQuery = true)
    public byte[] getByKey(String imgKey);
    
}
