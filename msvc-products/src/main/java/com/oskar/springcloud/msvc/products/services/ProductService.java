package com.oskar.springcloud.msvc.products.services;
import com.oskar.springcloud.msvc.products.entities.Product;
import java.util.List;
import java.util.Optional;
 
public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id); // Maneja valores potencialmente nulos
    
}
