package de.digitalcollections.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Services context.
 */
@Configuration
@ComponentScan(basePackages = {
  "de.digitalcollections.core.business.impl.service"
})
public class SpringConfigBusiness {
}
