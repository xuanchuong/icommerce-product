package com.icommerce.product.bootstrap.configuration.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.icommerce.product.data.jpa.entity.ProductJpa;
import com.icommerce.product.domain.entity.Authority;
import com.icommerce.product.domain.vo.Role;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Random;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(Role.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(Role.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "xnchuong", id = "02-add-sample-products")
    public void addSampleProducts(MongoTemplate mongoTemplate) {
        for (int i = 1; i <= 100; i++) {
            ProductJpa product = new ProductJpa();
            product.setBrand("Brand of product " + i);
            product.setTitle("Title of product " + i);
            product.setPrice(new Random().nextInt(100000) + 5000);
            mongoTemplate.save(product);
        }
    }
}
