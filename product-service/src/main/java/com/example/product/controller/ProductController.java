package com.example.product.controller;


import com.example.product.model.Product;
import com.example.product.model.User;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController // Marchează clasa ca un Controller REST
@RequestMapping("/products") // Definește calea de bază pentru toate endpoint-urile
public class ProductController {

    private final ProductService productService; // Acum injectăm ProductService
    private final RestTemplate restTemplate;
    @Value("${user.service.url}")
    private String userServiceUrl;

    @Autowired // Injectează instanța de ProductService
    public ProductController(ProductService productService,RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    // Endpoint pentru a verifica dacă serviciul rulează
    @GetMapping("/status") // Ex: GET http://localhost:3002/products/status
    public String getStatus() {
        return "Product Service is running!";
    }

    // Endpoint pentru a obține toate produsele
    @GetMapping // Ex: GET http://localhost:3002/products
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("GET /products request received by controller.");
        List<Product> products = productService.getAllProducts(); // Apelăm ProductService
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Endpoint pentru a obține un produs după ID
    @GetMapping("/{id}") // Ex: GET http://localhost:3002/products/1
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        System.out.println("GET /products/" + id + " request received by controller.");
        return productService.getProductById(id) // Apelăm ProductService
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pentru a crea un produs nou
    @PostMapping // Ex: POST http://localhost:3002/products
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        System.out.println("POST /products request received by controller. Product data: " + product);
        Product savedProduct = productService.createProduct(product); // Apelăm ProductService
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); // 201 Created
    }

    // Endpoint pentru a actualiza un produs existent
    @PutMapping("/{id}") // Ex: PUT http://localhost:3002/products/1
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        System.out.println("PUT /products/" + id + " request received by controller. Product data: " + product);
        product.setId(id); // Asigurăm că ID-ul din path este folosit pentru actualizare
        return productService.updateProduct(id, product) // Apelăm ProductService
                .map(updatedProduct -> new ResponseEntity<>(updatedProduct, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pentru a șterge un produs
    @DeleteMapping("/{id}") // Ex: DELETE http://localhost:3002/products/1
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        System.out.println("DELETE /products/" + id + " request received by controller.");
        if (productService.deleteProduct(id)) { // Apelăm ProductService
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user-details/{id}")
    public ResponseEntity<User> getUserDetailsFromUserService(@PathVariable String id) {
        System.out.println("GET /products/user-details/" + id + " request received.");

        // Simulează o logica de business pentru a lega un produs de un utilizator
        // În realitate, am avea o bază de date cu relații
        Product product = productService.getProductById(id).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Metoda principală care poate eșua
        return this.restTemplate.getForEntity(userServiceUrl + "/users/" + id, User.class);
    }
}