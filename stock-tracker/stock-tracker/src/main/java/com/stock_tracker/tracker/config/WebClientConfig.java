package com.stock_tracker.tracker.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder,
                               @Value("alpha.vantage.url") String baseUrl) {
        return  builder.baseUrl(baseUrl).build();
    }
}
