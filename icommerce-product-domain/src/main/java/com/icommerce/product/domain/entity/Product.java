package com.icommerce.product.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Builder(toBuilder = true)
@ToString
@FieldDefaults(makeFinal = true)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer version;

    private String title;

    private Integer price;

    private String brand;

    private byte[] image;

    private String imageContentType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
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
