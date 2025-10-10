package com.example.webshop_backend.dao;

import com.example.webshop_backend.dto.ProductDTO;
import com.example.webshop_backend.model.Categorie;
import com.example.webshop_backend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class ProductDAO {
    private ProductRepository productRepository;
    private CategorieRepository categorieRepository;

    public ProductDAO(ProductRepository productRepository, CategorieRepository categorieRepository) {
        this.productRepository = productRepository;
        this.categorieRepository = categorieRepository;
    }

    public ResponseEntity<String> createProduct(ProductDTO productDTO){
        Optional<Categorie> categorie = categorieRepository.findById(productDTO.categorie_id);
        if(categorie.isPresent()) {
            Product product = new Product(productDTO.name, productDTO.description, productDTO.price, productDTO.stock, categorie.get());
            this.productRepository.save(product);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "categorie niet gevonden");
        }
        return ResponseEntity.ok().build();
    }

    public void deleteProduct(Long product_id) {
        if (productRepository.existsById(product_id)) {
            productRepository.deleteById(product_id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product bestaat niet");
        }
    }

    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long product_id){
        return productRepository.findById(product_id);
    }

    public void updateProduct(Product product){
        productRepository.save(product);
    }

}