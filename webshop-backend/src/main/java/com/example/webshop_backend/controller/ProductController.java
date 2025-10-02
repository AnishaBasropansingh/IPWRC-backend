package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.CategorieDAO;
import com.example.webshop_backend.dao.ProductDAO;
import com.example.webshop_backend.dto.ProductDTO;
import com.example.webshop_backend.model.Categorie;
import com.example.webshop_backend.model.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
class ProductController {
    private ProductDAO productDAO;
    private CategorieDAO categorieDAO;

    public ProductController(ProductDAO productDAO, CategorieDAO categorieDAO) {
        this.productDAO = productDAO;
        this.categorieDAO = categorieDAO;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        this.productDAO.createProduct(productDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long product_id) {
        try {
            productDAO.deleteProduct(product_id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        List<Product> products = (List<Product>) productDAO.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Long product_id) {
        Optional<Product> product = productDAO.getProductById(product_id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long product_id, @RequestBody ProductDTO productDTO, Principal principal) {
        Optional<Product> existingProduct = this.productDAO.getProductById(product_id);
        if (existingProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Categorie> categorie = this.categorieDAO.getCategorieById(productDTO.categorie_id);
        if (categorie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Product pr = new Product(productDTO.name, productDTO.description, productDTO.price, productDTO.stock, categorie.get());
        pr.setProduct_id(existingProduct.get().getProduct_id());

        this.productDAO.updateProduct(pr);

        return ResponseEntity.ok(pr);
    }
}
