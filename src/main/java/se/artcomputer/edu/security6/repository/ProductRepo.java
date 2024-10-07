package se.artcomputer.edu.security6.repository;

import se.artcomputer.edu.security6.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
