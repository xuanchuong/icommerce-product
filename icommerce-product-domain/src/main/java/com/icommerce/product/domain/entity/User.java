package com.icommerce.product.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@FieldDefaults(makeFinal = true)
public class User {

    private static final long serialVersionUID = 1L;

    private String id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    @Builder.Default
    private boolean activated = false;

    private String langKey;

    private String imageUrl;

    private String createdBy;

    @Builder.Default
    private Instant createdDate = Instant.now();

    private String lastModifiedBy;

    @Builder.Default
    private Instant lastModifiedDate = Instant.now();

    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            "}";
    }
}
