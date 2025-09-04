package com.oskar.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.oskar.springcloud.msvc.products.entities.Product;
import com.oskar.springcloud.msvc.products.repositories.ProductRepository;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    
    final private ProductRepository repository;
    final private Environment enviroment;

    public ProductServiceImpl(ProductRepository repository, Environment environment){
        this.repository = repository;
        this.enviroment= environment;
    }

    @Override
    @Transactional(readOnly= true) //Sincronizar nuestros cambios con la base de datos
    // Esta anotación asegura que los cambios se realicen de manera atómica
    public List<Product> findAll() {

        return ((List<Product>) repository.findAll()).stream().map(
            product->{
                product.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
                return product;
            }
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly= true)// Indica que el método no realizará modificaciones en la base de datos, solo lecturas. Esto permite a Spring optimizar la transacción
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(
            product->{
                product.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
                return product;
            }
        );
    }

}
