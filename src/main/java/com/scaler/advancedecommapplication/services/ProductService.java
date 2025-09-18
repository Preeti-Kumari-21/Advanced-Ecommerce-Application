package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.ProductRequestDto;
import com.scaler.advancedecommapplication.dtos.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getAllProductsById(Long id);

    Boolean deleteProductById(Long id);

    List<ProductResponseDto> searchAllProductsByKeyword(String keyword);
}
