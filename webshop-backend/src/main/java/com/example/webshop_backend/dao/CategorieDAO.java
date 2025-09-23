package com.example.webshop_backend.dao;

import com.example.webshop_backend.dto.CategorieDTO;
import com.example.webshop_backend.model.Categorie;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategorieDAO {
    private CategorieRepository categorieRepository;
    private ProductRepository productRepository;

    public CategorieDAO(CategorieRepository categorieRepository, ProductRepository productRepository) {
        this.categorieRepository = categorieRepository;
        this.productRepository = productRepository;
    }

    public Optional<Categorie> getCategorieById(Long categorie_id){
        return categorieRepository.findById(categorie_id);
    }

    public void createCategorie(CategorieDTO categorieDTO){
        Categorie categorie = new Categorie(categorieDTO.name);
        categorieRepository.save(categorie);
    }

    public Iterable<Categorie> getAllCategories(){
        return categorieRepository.findAll();
    }

    public void deleteCategorie(Long categorie_id){
        if(categorieRepository.existsById(categorie_id)){
            categorieRepository.deleteById(categorie_id);
        } else {
            System.out.println("Categorie bestaat niet");
        }
    }

    public void updateCategorie(Categorie categorie){
        categorieRepository.save(categorie);
    }

}
