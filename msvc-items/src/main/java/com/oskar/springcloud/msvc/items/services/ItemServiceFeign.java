package com.oskar.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oskar.springcloud.msvc.items.clients.ProductFeignClient;
import com.oskar.springcloud.msvc.items.models.Item;
import com.oskar.springcloud.msvc.items.models.Product;

import feign.FeignException;


@Service // Marca la clase como servicio de negocio
public class ItemServiceFeign implements ItemService {

    // Inyección automática del cliente Feign
    @Autowired
    private ProductFeignClient client;

    /**
     * Obtiene todos los productos desde el microservicio "msvc-products"
     * y los transforma en Items agregando una cantidad aleatoria.
     */
    @Override
    public List<Item> findAll() {
        return client.findAll() // <-- Aquí Feign llama al microservicio "msvc-products"
            .stream()
            .map(product -> {
                Random random = new Random();
                return new Item(product, random.nextInt(10) + 1); // Combina Product + cantidad
            })
            .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por id desde el microservicio "msvc-products"
     * y lo transforma en un Item. Devuelve Optional.empty() si no existe.
     */
    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.details(id); // <-- Feign hace GET /products/{id}
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));
        } catch (FeignException e) {
            // Si ocurre un error HTTP (404, 500), se devuelve Optional vacío
            return Optional.empty();
        }
    }
}