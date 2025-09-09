package com.oskar.springcloud.msvc.products.services;
import com.oskar.springcloud.msvc.products.entities.Product;
import java.util.List;
import java.util.Optional;


//Definimos la interfaz ProductService con métodos para encontrar todos 
//los productos y encontrar un producto por su ID
//Solo definimos la interfaz, la implementación se hará en ProductServiceImpl
public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id); // Maneja valores potencialmente nulos
    
}
