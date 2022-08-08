package ru.skypro.homework.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.*;

/**
 * Класс сущности пользователя сайта.
 */
@Entity
@Table(name = "ads_review")
public class ComEntity implements Serializable {

    /**
     * Уникальный идентификатор записи комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    /**
     * Идентификатор записи объявления к которому относится комментарий.
     */
    @ManyToOne
    @JoinColumn(name = "id_ads")
    private AdsEntity ads;

    /**
     * Пользователь, создавший комментарий.
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity author;

    /**
     * Время создания комментария.
     */
    private OffsetDateTime created;

    /**
     * Текст комментария.
     */
    private String text;

    public ComEntity(Integer id, AdsEntity ads, UserEntity author, OffsetDateTime created, String text) {
        this.id = id;
        this.ads = ads;
        this.author = author;
        this.created = created;
        this.text = text;
    }

    public ComEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdsEntity getAds() {
        return ads;
    }

    public void setAds(AdsEntity ads) {
        this.ads = ads;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
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
        final ComEntity other = (ComEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ComEntity{" + "id=" + id + ", ads=" + ads + ", author=" + author + ", created=" + created + ", text=" + text + '}';
    }

}
