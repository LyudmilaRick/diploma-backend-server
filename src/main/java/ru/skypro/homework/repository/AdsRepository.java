package ru.skypro.homework.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.AdsEntity;

/**
 * Репозиторий объявлений.
 */
@Repository
public interface AdsRepository extends JpaRepository<AdsEntity, Integer> {

    /**
     * Получить все объявления заданного пользователя.
     *
     * @param id идентификатор пользователя (автора) объявлений.
     * @return список сущностей объявлений.
     */
    @Query(value = "SELECT * FROM ads_header WHERE id_user = ?1", nativeQuery = true)
    List<AdsEntity> findByAuthorId(Integer id);

}
