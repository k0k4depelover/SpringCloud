package com.oskar.springcloud.msvc.items.controllers;
import org.springframework.web.bind.annotation.RestController;
import com.oskar.springcloud.msvc.items.models.Item;
import com.oskar.springcloud.msvc.items.services.ItemService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Controlador REST que expone los endpoints del microservicio de Items
@RestController
public class ItemController {
    
    // Inyección de la capa de servicio (la lógica de negocio).
    // Aquí no hacemos consultas directas, solo delegamos al servicio.
    private final ItemService service;

    // Constructor para inyección de dependencias automática (Spring lo resuelve solo).
    public ItemController(ItemService service) {
        this.service = service;
    }
    
    // Endpoint: GET /items
    // Devuelve la lista completa de Items (cada Item es un producto + cantidad).
    @GetMapping
    public List<Item> list() {
        return service.findAll();
    }
    
    // Endpoint: GET /items/{id}
    // Devuelve un solo Item por su id (consultado en msvc-products).
    // Se usa ResponseEntity para manejar respuestas con diferentes códigos HTTP.
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Item> itemOptional = service.findById(id);

        if (itemOptional.isPresent()) {
            // Si existe el producto, devolvemos HTTP 200 (OK) + el objeto en JSON
            return ResponseEntity.ok(itemOptional.get());
        }
        
        // Si no existe, devolvemos HTTP 404 (Not Found) + un mensaje personalizado
        return ResponseEntity.status(404)
            .body(Collections.singletonMap("message", 
                "No existe el producto en el microservicio de msvc-products"));
    }
}
