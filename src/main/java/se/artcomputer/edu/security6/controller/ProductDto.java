package se.artcomputer.edu.security6.controller;

import se.artcomputer.edu.security6.model.Product;

public record ProductDto(int id, String name, String description) {

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription());
    }
}
