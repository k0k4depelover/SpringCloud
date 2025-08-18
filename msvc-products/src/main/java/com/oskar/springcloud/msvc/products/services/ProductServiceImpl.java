package com.oskar.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;

import com.oskar.springcloud.msvc.products.entities.Product;
import com.oskar.springcloud.msvc.products.repositories.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    
    final private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly= true) //Sincronizar nuestros cambios con la base de datos
    // Esta anotación asegura que los cambios se realicen de manera atómica
    public List<Product> findAll() {

        return (List<Product>) repository.findAll();
    }

    @Override
    @Transactional(readOnly= true)// Indica que el método no realizará modificaciones en la base de datos, solo lecturas. Esto permite a Spring optimizar la transacción
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

}
