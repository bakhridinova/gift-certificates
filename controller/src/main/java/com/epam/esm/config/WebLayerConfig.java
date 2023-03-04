package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * configuration for controller layer
 */

@Configuration
@EnableWebMvc
@ComponentScan({"com.epam.esm"})
public class WebLayerConfig implements WebMvcConfigurer {

}
