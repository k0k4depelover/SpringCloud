package com.oskar.springcloud.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.oskar.springcloud.msvc.items.models.Product;


@FeignClient(url="localhost:8001")
public class ProductsFeignClient {
    
    @GetMapping
    List<Product> findAll();

}
