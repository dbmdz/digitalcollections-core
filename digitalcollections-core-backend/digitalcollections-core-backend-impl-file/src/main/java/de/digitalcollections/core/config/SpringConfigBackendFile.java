package de.digitalcollections.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {
  "de.digitalcollections.core.backend.impl.file"
})
@PropertySource(value = {
  "classpath:de/digitalcollections/core/config/SpringConfigBackendFile-${spring.profiles.active}.properties"
})
public class SpringConfigBackendFile {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
