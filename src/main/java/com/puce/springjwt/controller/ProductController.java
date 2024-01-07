package com.puce.springjwt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puce.springjwt.model.Product;
import com.puce.springjwt.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public List<Product> listProducts() {
        return service.listProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        try {
            Product product = service.getProduct(id);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/name/{name}")
    public List<Product> findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping("/products/price/{price}")
    public List<Product> findByPriceGreaterThan(@PathVariable float price) {
        return service.findByPriceGreaterThan(price);
    }

    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product product) {
        service.saveProduct(product);
        return product;
    }

    @PutMapping("products/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Integer id) {
        service.saveProduct(product);
        Product productUpdated = service.getProduct(id);
        return productUpdated;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Producto borrado correctamente");
    }

}
