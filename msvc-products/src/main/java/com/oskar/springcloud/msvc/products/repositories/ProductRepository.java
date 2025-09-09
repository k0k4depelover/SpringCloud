package com.oskar.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;
import com.oskar.springcloud.msvc.products.entities.Product;

//Esta interfaz extiende CrudRepository para proporcionar operaciones 
//CRUD básicas para la entidad Product
//Esta interfaz no requiere implementación explícita, ya que Spring Data JPA 
//proporciona la implementación en tiempo de ejecución
public interface ProductRepository extends CrudRepository<Product, Long> {
    // Additional query methods can be defined here if needed

}
