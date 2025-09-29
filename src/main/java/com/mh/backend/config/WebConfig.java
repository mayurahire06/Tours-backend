package com.mh.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // 👇 Map /uploads/** to the physical uploads folder inside project root
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:uploads/");
                // If absolute path: "file:/C:/Users/you/project/backend/uploads/"
            }

              //it is also working
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/uploads/**")
//                        .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
//            }
        };
    }
}
