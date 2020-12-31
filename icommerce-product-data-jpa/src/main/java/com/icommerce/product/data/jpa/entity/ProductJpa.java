package com.icommerce.product.data.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Document(collection = "product")
@Data
@ToString
@Getter
@Setter
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
