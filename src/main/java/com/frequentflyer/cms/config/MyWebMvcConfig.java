package com.frequentflyer.cms.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * Configuring application to serve two frontends as separate Web applications
 * 
 * @author Sasa Radovanovic
 *
 */
@Configuration
public class MyWebMvcConfig {

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html
                registry.addViewController("/backoffice").setViewName(
                        "forward:/backoffice/index.html");
            }
        };
    }

}