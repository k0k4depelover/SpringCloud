package com.oskar.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.oskar.springcloud.msvc.items.models.Item;
import com.oskar.springcloud.msvc.items.models.Product;

/**
 * Servicio que implementa la interfaz ItemService usando WebClient.
 * 
 * ⚡ OJO: este microservicio (msvc-items) NO accede a la base de datos.
 * Solo consume a otro microservicio (msvc-products) que sí tiene DB.
 */
@Primary // Si hay varias implementaciones de ItemService, Spring usa esta como la principal.
@Service // Marca la clase como componente de negocio (Service) para que Spring la gestione.
public class ItemServiceWebClient implements ItemService { //Implementa la interfaz ItemService

    // Cliente HTTP reactivo para hacer llamadas a otro microservicio
    private final WebClient.Builder client;
    
    // Spring inyecta el WebClient.Builder automáticamente (lo configuras en una clase @Configuration)
    public ItemServiceWebClient(Builder client) {
        this.client = client;
    }

    /**
     * Obtiene todos los productos desde el microservicio "products"
     * y los transforma en Items agregando una cantidad aleatoria.
     */
    @Override
    public List<Item> findAll() {
        return this.client.build()       // Construimos el WebClient
            .get()                       // Hacemos petición GET
            .accept(MediaType.APPLICATION_JSON) // Indicamos que queremos JSON
            .retrieve()                  // Ejecuta la llamada HTTP
            .bodyToFlux(Product.class)   // Convierte el JSON a un flujo (Flux<Product>)
            .map(product -> 
                new Item(product, new Random().nextInt(10) + 1) // Transformamos Product -> Item
            )
            .collectList()               // Lo juntamos en una lista (List<Item>)
            .block();                    // Bloqueamos para esperar la respuesta (API síncrona)
    }

    /**
     * Obtiene un producto por id desde el microservicio "products"
     * y lo transforma en un Item. Si hay error HTTP (404, 500, etc.), devuelve Optional.empty().
     */
    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id); // Parámetro de la URL (ejemplo: /products/{id})

        try {
            return Optional.of(
                this.client.build().get()          // GET
                    .uri("/{id}", params)          // Llamada a /products/{id}
                    .accept(MediaType.APPLICATION_JSON) // Queremos JSON
                    .retrieve()                 // Ejecuta la llamada
                    .bodyToMono(Product.class)     // Un solo objeto Product
                    .map(product -> 
                        new Item(product, new Random().nextInt(10) + 1)
                    ) 
                    .block()                       // Esperamos la respuesta
            ); 
        } catch(WebClientResponseException e) {
            // Si ocurre error HTTP (ejemplo: producto no encontrado)
            return Optional.empty();
        }
    }
}
