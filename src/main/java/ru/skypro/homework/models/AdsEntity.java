package ru.skypro.homework.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.*;

/**
 * Класс сущности пользователя сайта.
 */
@Entity
@Table(name = "ads_header")
public class AdsEntity implements Serializable {

    /**
     * Уникальный идентификатор записи объявления.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ads", unique = true)
    private Integer idAds;

    /**
     * Пользователь, создавший объявление.
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity author;

    /**
     * Время создания объявления.
     */
    private OffsetDateTime created;

    /**
     * Стоимость товара указанная в объявлении.
     */
    private Double price;

    /**
     * Заголовок к объявлению.
     */
    private String title;

    /**
     * Подпробный текст объявления.
     */
    private String description;

    public AdsEntity() {
    }

    public AdsEntity(Integer id, UserEntity author, OffsetDateTime created, Double price, String title, String description) {
        this.idAds = id;
        this.author = author;
        this.created = created;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Integer getIdAds() {
        return idAds;
    }

    public void setIdAds(Integer idAds) {
        this.idAds = idAds;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdsEntity other = (AdsEntity) obj;
        return Objects.equals(this.idAds, other.idAds);
    }

    @Override
    public String toString() {
        return "AdsEntity{" + "id=" + idAds + ", author=" + author + ", created=" + created + ", price=" + price + ", title=" + title + ", description=" + description + '}';
    }

}
