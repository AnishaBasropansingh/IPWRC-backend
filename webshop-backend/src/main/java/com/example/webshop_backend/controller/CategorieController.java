package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.CategorieDAO;
import com.example.webshop_backend.dao.ProductDAO;
import com.example.webshop_backend.dto.CategorieDTO;
import com.example.webshop_backend.model.Categorie;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorie")
class CategorieController {
    private ProductDAO productDAO;
    private CategorieDAO categorieDAO;

    public CategorieController(ProductDAO productDAO, CategorieDAO categorieDAO) {
        this.productDAO = productDAO;
        this.categorieDAO = categorieDAO;
    }

    @PostMapping
    public ResponseEntity<String> createCategorie(@RequestBody CategorieDTO categorieDTO){
        this.categorieDAO.createCategorie(categorieDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategorie(@PathVariable(name = "id") Long categorie_id){
        try{
            categorieDAO.deleteCategorie(categorie_id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Categorie>> getAllCategories(){
        List<Categorie> categorieList = (List<Categorie>) categorieDAO.getAllCategories();
        return ResponseEntity.ok(categorieList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable(name = "id") Long categorie_id, @RequestBody CategorieDTO categorieDTO){
        Optional<Categorie> existingCategorie = this.categorieDAO.getCategorieById(categorie_id);
        if(existingCategorie.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Categorie categorie = new Categorie(categorieDTO.name);
        categorie.setCategorie_id(existingCategorie.get().getCategorie_id());
        System.out.println(categorieDTO.name);
        this.categorieDAO.updateCategorie(categorie);
        return ResponseEntity.ok(categorie);
    }
}
