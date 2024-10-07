package se.artcomputer.edu.security6.controller;

import se.artcomputer.edu.security6.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public record AllProductsResponse(List<ProductDto> products) {
    public static AllProductsResponse from(List<Product> products) {
        return new AllProductsResponse(products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList()));
    }
}
