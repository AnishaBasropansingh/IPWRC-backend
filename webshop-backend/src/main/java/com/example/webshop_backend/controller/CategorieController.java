package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.CategorieDAO;
import com.example.webshop_backend.dao.ProductDAO;
import com.example.webshop_backend.dto.CategorieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorie")
class CategorieController {
    private ProductDAO productDAO;
    private CategorieDAO categorieDAO;

    public CategorieController(CategorieDAO categorieDAO) {
        this.productDAO = productDAO;
        this.categorieDAO = categorieDAO;
    }

    @PostMapping
    public ResponseEntity<String> createCategorie(@RequestBody CategorieDTO categorieDTO){
        this.categorieDAO.createCategorie(categorieDTO);
        return ResponseEntity.noContent().build();
    }
}
