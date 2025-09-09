package com.oskar.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;

import com.oskar.springcloud.msvc.items.models.Item;


/**
 * Interfaz del servicio de Items.
 * Define los métodos para obtener Items.
 */
public interface ItemService {
    List<Item> findAll();
    Optional<Item> findById(Long id);
}

