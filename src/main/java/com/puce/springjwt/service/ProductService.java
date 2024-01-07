package com.puce.springjwt.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puce.springjwt.model.Product;
import com.puce.springjwt.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> listProducts() {
        return repository.findAll();
    }

    public Product getProduct(Integer id) {
        return repository.findById(id).get();
    }

    public List<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Product> findByPriceGreaterThan(float price) {
        return repository.findByPriceGreaterThan(price);
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }

    public void deleteProduct(Integer id) {
        repository.deleteById(id);
    }
}
