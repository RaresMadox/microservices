package com.example.product.service;


import com.example.product.model.Product;
import com.example.product.model.User;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service // Marchează clasa ca un component de tip Service
public class ProductService {

    private final ProductRepository productRepository; // Injectăm ProductRepository
    private final RestTemplate restTemplate;
    @Value("${user.service.url}")
    private String userServiceUrl;

    @Autowired // Injectează instanța de ProductRepository în constructor
    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    // Metodă pentru a obține toate produsele
    public List<Product> getAllProducts() {
        System.out.println("Fetching all products from Product Service layer.");
        return productRepository.findAll();
    }

    // Metodă pentru a obține un produs după ID
    public Optional<Product> getProductById(String id) {
        System.out.println("Fetching product with ID: " + id + " from Product Service layer.");
        return productRepository.findById(id);
    }

    // Metodă pentru a crea un produs nou
    public Product createProduct(Product product) {
        System.out.println("Creating product: " + product.getName() + " in Product Service layer.");
        return productRepository.save(product);
    }

    // Metodă pentru a actualiza un produs existent
    public Optional<Product> updateProduct(String id, Product productDetails) {
        System.out.println("Updating product with ID: " + id + " in Product Service layer.");
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPrice(productDetails.getPrice());
                    return productRepository.save(existingProduct);
                });
    }

    // Metodă pentru a șterge un produs
    public boolean deleteProduct(String id) {
        System.out.println("Deleting product with ID: " + id + " from Product Service layer.");
        return productRepository.deleteById(id);
    }

    public Optional<User> getUserDetails(String userId) {
        System.out.println("Calling User Service to get details for user ID: " + userId);
        try {
            // Construim URL-ul complet pentru endpoint-ul user-service
            String url = userServiceUrl + "/users/" + userId;
            // Efectuăm apelul GET și mapăm răspunsul la obiectul User
            User user = restTemplate.getForObject(url, User.class);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            System.err.println("Error calling User Service for user ID " + userId + ": " + e.getMessage());
            return Optional.empty(); // Returnează gol în caz de eroare (ex: utilizator inexistent, serviciu indisponibil)
        }
    }
}
