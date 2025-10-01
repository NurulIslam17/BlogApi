package com.nurul.blog.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    // Configure static resource handler to serve uploaded images
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps the URL pattern /uploads/** to the physical directory uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");

        registry.addMapping("/uploads/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET");
    }

}
