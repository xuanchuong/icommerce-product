package com.icommerce.product.bootstrap;

import com.icommerce.product.application.configuration.ApplicationConfiguration;
import com.icommerce.product.bootstrap.configuration.ApplicationProperties;
import com.icommerce.product.data.jpa.configuration.DataJpaConfiguration;
import com.icommerce.product.message.producer.configuration.MessageProducerConfiguration;
import com.icommerce.product.rest.configuration.RestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties({ApplicationProperties.class})
@Import({
        DataJpaConfiguration.class,
        ApplicationConfiguration.class,
        MessageProducerConfiguration.class,
        RestConfiguration.class
})
@EnableAutoConfiguration
public class ProductApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class, args);
    }
}
