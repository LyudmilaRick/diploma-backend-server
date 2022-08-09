package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.UserEntity;

/**
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    
    @Query(value = "SELECT * FROM user_data WHERE username = ?1", nativeQuery = true)
    public UserEntity getByUsername(String username);

}
