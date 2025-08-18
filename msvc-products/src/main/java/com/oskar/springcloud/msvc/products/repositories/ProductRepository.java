package com.oskar.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;
import com.oskar.springcloud.msvc.products.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // Additional query methods can be defined here if needed

}
