package se.artcomputer.edu.security6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.edu.security6.repository.ProductRepo;

@RestController
@RequestMapping
public class ProductController {
    private final ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/products/all")
    public ResponseEntity<AllProductsResponse> getAllProducts() {
        return ResponseEntity.ok(AllProductsResponse.from(productRepo.findAll()));
    }
}
