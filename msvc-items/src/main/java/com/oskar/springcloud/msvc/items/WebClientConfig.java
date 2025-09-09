package com.oskar.springcloud.msvc.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // Marca la clase como configuración de Spring
public class WebClientConfig {

    // Toma la URL base del microservicio desde application.properties
    @Value("${config.baseurl.endpoint.msvc-products}")
    private String url;

    /**
     * Define un bean de WebClient.Builder que se puede inyectar en servicios
     * @LoadBalanced activa el balanceo de carga automático entre instancias registradas
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        // Crea un WebClient con la URL base del microservicio de productos
        return WebClient.builder()
                        .baseUrl(url);
    }
}
