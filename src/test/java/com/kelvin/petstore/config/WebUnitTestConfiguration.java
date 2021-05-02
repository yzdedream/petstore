package com.kelvin.petstore.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LocalConfiguration.class)
@ComponentScan("com.kelvin.petstore")
public class WebUnitTestConfiguration {
}
