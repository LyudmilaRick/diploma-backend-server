package ru.skypro.homework.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * Класс сущности пользователя сайта.
 */
@Entity
@Table(name = "ads_images")
public class ImgEntity implements Serializable {

    /**
     * Идентификатор записи картинки (UUID).
     */
    @Id
    @Column(name = "id", unique = true)
    private String id;

    /**
     * Объявление к которому относится картинка.
     */
    @ManyToOne
    @JoinColumn(name = "id_ads")
    private AdsEntity ads;

    /**
     * Размер файла изображения.
     */
    private Long size;

    /**
     * Краткое описание картинки (заголовок).
     */
    private String title;

    /**
     * Содержимое картинки (файл изображения).
     */
    @Lob
    private byte[] context;

    public ImgEntity(String id, AdsEntity ads, Long size, String title, byte[] context) {
        this.id = id;
        this.ads = ads;
        this.size = size;
        this.title = title;
        this.context = context;
    }

    public ImgEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdsEntity getAds() {
        return ads;
    }

    public void setAds(AdsEntity ads) {
        this.ads = ads;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContext() {
        return context;
    }

    public void setContext(byte[] context) {
        this.context = context;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final ImgEntity other = (ImgEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ImgEntity{" + "id=" + id + ", ads=" + ads + ", size=" + size + ", title=" + title + ", context=" + context.length + '}';
    }
    
}
