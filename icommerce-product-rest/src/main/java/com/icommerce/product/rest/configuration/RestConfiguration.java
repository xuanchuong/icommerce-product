package com.icommerce.product.rest.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan({"com.icommerce.product.rest.endpoint"})
public class RestConfiguration {
}
