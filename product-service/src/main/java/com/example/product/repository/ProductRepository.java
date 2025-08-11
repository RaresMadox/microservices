package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {


    private final List<Product> products = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public ProductRepository() {

        products.add(new Product(String.valueOf(counter.incrementAndGet()), "Laptop Pro", "Un laptop puternic pentru profesioniști.", 1200.00));
        products.add(new Product(String.valueOf(counter.incrementAndGet()), "Mouse Ergonomic", "Mouse wireless cu design ergonomic.", 25.50));
        products.add(new Product(String.valueOf(counter.incrementAndGet()), "Tastatură Mecanică", "Tastatură cu switch-uri mecanice și iluminare RGB.", 80.00));


    }


    public List<Product> findAll()
    {
        return new ArrayList<>(products);
    }


    public Optional<Product> findById(String id)
    {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product save(Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId(String.valueOf(counter.incrementAndGet()));
            products.add(product);
            System.out.println("Product created: " + product);
        } else {
            boolean updated = false;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(product.getId())) {
                    products.set(i, product);
                    updated = true;
                    System.out.println("Product updated: " + product);
                    break;
                }
            }
            if (!updated) {
                products.add(product);
                System.out.println("Product added (but ID existed and not found for update): " + product);
            }
        }
        return product;
    }

    public boolean deleteById(String id) {
        boolean removed = products.removeIf(product -> product.getId().equals(id));
        if (removed) {
            System.out.println("Product with ID " + id + " deleted.");
        } else {
            System.out.println("Product with ID " + id + " not found for deletion.");
        }
        return removed;
    }

}
