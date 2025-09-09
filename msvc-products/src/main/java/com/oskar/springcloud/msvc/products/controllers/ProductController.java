package com.oskar.springcloud.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import com.oskar.springcloud.msvc.products.services.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.oskar.springcloud.msvc.products.entities.Product;



@RestController


public class ProductController {
    final private ProductService service;

    public ProductController(ProductService service) {
        this.service=service;
    }
    //Definimos los endpoints para listar todos los productos y obtener detalles de un producto por su ID
    //Utilizamos ResponseEntity para manejar las respuestas HTTP de manera más flexible
    @GetMapping
    public ResponseEntity<?> list () {
        return ResponseEntity.ok(this.service.findAll());
    }
    // Endpoint para obtener detalles de un producto por su ID
    //Lo especificamos en la ruta del endpoint con {id}
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Product> productOptional= service.findById(id);
        
        if(productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
            // Handle the case where the product is not found
            // This could throw an exception or return a default value
        return ResponseEntity.notFound().build();
    }
    
}
