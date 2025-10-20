package com.example.webshop_backend.utils;

import com.example.webshop_backend.dao.CategorieRepository;
import com.example.webshop_backend.dao.ProductRepository;
import com.example.webshop_backend.model.Categorie;
import com.example.webshop_backend.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategorieRepository categorieRepository;

    public ProductSeeder(ProductRepository productRepository, CategorieRepository categorieRepository) {
        this.productRepository = productRepository;
        this.categorieRepository = categorieRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Check of categorieën al bestaan om dubbele inserts te voorkomen
        if (categorieRepository.count() == 0) {
            Categorie merch = new Categorie("Merch");
            Categorie figueren = new Categorie("Figueren");
            Categorie manga = new Categorie("Manga");

            categorieRepository.save(merch);
            categorieRepository.save(figueren);
            categorieRepository.save(manga);

            System.out.println("Seeder: categorieën aangemaakt!");
        }

        if (productRepository.count() == 0) {
            // FTECH die categorien
            Categorie merch = categorieRepository.findByName("Merch")
                    .orElseThrow(() -> new RuntimeException("Categorie Merch niet gevonden"));
            Categorie figueren = categorieRepository.findByName("Figueren")
                    .orElseThrow(() -> new RuntimeException("Categorie Figueren niet gevonden"));
            Categorie manga = categorieRepository.findByName("Manga")
                    .orElseThrow(() -> new RuntimeException("Categorie Manga niet gevonden"));

            // seeder minimaal 10 producten
            Product p1 = new Product("Anime Hoodie", "Zwarte hoodie met print", 29.99, 10, merch);
            Product p2 = new Product("T-shirt Giyuu", "Officieel T-shirt", 19.99, 15, merch);
            Product p3 = new Product("Jujutsu Kaisen Gojo Figuur", "Daddy's Home", 49.99, 5, figueren);
            Product p4 = new Product("One Piece Luffy Figuur", "Limited Edition", 59.99, 3, figueren);
            Product p5 = new Product("Haikyuu!! Vol.1", "Manga eerste volume", 9.99, 20, manga);
            Product p6 = new Product("Attack on Titan Vol.1", "Manga eerste volume", 10.99, 18, manga);
            Product p7 = new Product("Demon Slayer Poster", "Full color poster", 7.99, 30, merch);
            Product p8 = new Product("My Hero Academia Figuur.", "Collectible figuur", 39.99, 7, figueren);
            Product p9 = new Product("Fairy Tail Vol.3", "Manga volume 3", 11.99, 12, manga);
            Product p10 = new Product("Anime Socks", "Set van 3 paar sokken", 14.99, 25, merch);

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
            productRepository.save(p4);
            productRepository.save(p5);
            productRepository.save(p6);
            productRepository.save(p7);
            productRepository.save(p8);
            productRepository.save(p9);
            productRepository.save(p10);

            System.out.println("Seeder: 10 voorbeeldproducten toegevoegd!");
        }
    }
}
