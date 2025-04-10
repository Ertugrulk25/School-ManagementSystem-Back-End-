package com.techproed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

//@Configuration
public class RestTemplateConfig {

  //  @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new StringHttpMessageConverter(StandardCharsets.UTF_8)));
        return restTemplate;
    }
} //yazı formatından kaynaklanan sorunu çözmek için kullanmak için bu class ı oluturdum ama şu anda ihtiyaç yok