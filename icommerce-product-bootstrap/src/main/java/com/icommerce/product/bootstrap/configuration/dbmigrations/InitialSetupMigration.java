package com.icommerce.product.bootstrap.configuration.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.icommerce.product.data.jpa.entity.ProductJpa;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.application.vo.AuthoritiesConstants;
import com.icommerce.product.domain.entity.Authority;
import com.icommerce.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "xnchuong", id = "02-add-sample-products")
    public void addSampleProducts(MongoTemplate mongoTemplate) {
        for (int i = 1; i <= 100; i++) {
            ProductJpa product = new ProductJpa();
            product.brand("Brand of product " + i);
            product.title("Title of product " + i);
            product.price(new Random().nextInt(100000) + 5000);
            mongoTemplate.save(product);
        }
    }
}
