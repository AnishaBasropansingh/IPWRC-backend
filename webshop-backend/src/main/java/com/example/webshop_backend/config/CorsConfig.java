package com.example.webshop_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(
            "http://localhost:4200",
            "http://127.0.0.1:4200",
            "https://webshop-frontend-6bmtc.ondigitalocean.app"
        )
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("Content-Type", "Authorization", "X-Requested-With")
        .allowCredentials(true)
        .maxAge(86400);
  }
}
