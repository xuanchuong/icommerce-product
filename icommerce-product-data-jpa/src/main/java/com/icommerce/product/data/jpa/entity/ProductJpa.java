package com.icommerce.product.data.jpa.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A Product.
 */
@Document(collection = "product")
@Data
@ToString
public class ProductJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Version
    private Integer version;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("price")
    private Integer price;

    @NotNull
    @Field("brand")
    private String brand;

    @Field("image")
    private byte[] image;

    @Field("image_content_type")
    private String imageContentType;


    public String getTitle() {
        return title;
    }

    public ProductJpa title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductJpa price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public ProductJpa brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public byte[] getImage() {
        return image;
    }

    public ProductJpa image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public ProductJpa imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductJpa)) return false;
        ProductJpa product = (ProductJpa) o;
        return Objects.equals(getId(), product.getId()) &&
            Objects.equals(getVersion(), product.getVersion()) &&
            Objects.equals(getTitle(), product.getTitle()) &&
            Objects.equals(getPrice(), product.getPrice()) &&
            Objects.equals(getBrand(), product.getBrand()) &&
            Arrays.equals(getImage(), product.getImage()) &&
            Objects.equals(getImageContentType(), product.getImageContentType());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getVersion(), getTitle(), getPrice(), getBrand(), getImageContentType());
        result = 31 * result + Arrays.hashCode(getImage());
        return result;
    }
}
