package com.cortica.interview.db;

import com.cortica.interview.api.ImageStatus;
import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * Created by avgdorom on 7/21/2016.
 */
@Entity(name = ImageEntity.ENTITY_NAME)
@Table(name = ImageEntity.ENTITY_NAME)
@NamedQueries({
        @NamedQuery(name = ImageEntity.QUERY_FIND_ALL, query = "select u from " + ImageEntity.ENTITY_NAME + " u")
})
public class ImageEntity {

    public static final String QUERY_FIND_ALL = "findAll";

    public static final String ENTITY_NAME = "images";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String date;

    private String name;

    private String location;

    private int width;

    private int height;

    private String transformations;

    private String url;

    private String md5;

    private ImageStatus status;

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTransformations() {
        return transformations;
    }

    public void setTransformations(String transformations) {
        this.transformations = transformations;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public ImageStatus getStatus() {
        return status;
    }

    public void setStatus(ImageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("date", date)
                .add("name", name)
                .add("location", location)
                .add("width", width)
                .add("height", height)
                .add("transformations", transformations)
                .add("url", url)
                .add("md5", md5)
                .add("status", status)
                .toString();
    }
}
