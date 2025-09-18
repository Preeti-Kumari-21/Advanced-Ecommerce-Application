package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.ProductRequestDto;
import com.scaler.advancedecommapplication.dtos.ProductResponseDto;
import com.scaler.advancedecommapplication.models.Product;
import com.scaler.advancedecommapplication.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productService")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = convertProductRequestDtoToProduct(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return convertProductToProductResponseDto(savedProduct);
    }

    @Override
    public Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequestDto) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // update fields
                    existingProduct.setName(productRequestDto.getName());
                    existingProduct.setDescription(productRequestDto.getDescription());
                    existingProduct.setPrice(productRequestDto.getPrice());
                    existingProduct.setStockQuantity(productRequestDto.getStockQuantity());
                    existingProduct.setCategory(productRequestDto.getCategory());
                    existingProduct.setImage(productRequestDto.getImage());

                    Product savedProduct = productRepository.save(existingProduct);
                    return convertProductToProductResponseDto(savedProduct);
                });
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> allProducts = productRepository.findByActiveTrue();
        return allProducts.stream()
                .map(this::convertProductToProductResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto getAllProductsById(Long id) {
        return convertProductToProductResponseDto(productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Override
    public Boolean deleteProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false); // Soft delete by setting active to false
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    @Override
    public List<ProductResponseDto> searchAllProductsByKeyword(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::convertProductToProductResponseDto)
                .toList();
    }


    private Product convertProductRequestDtoToProduct(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());
        product.setImage(dto.getImage());
        product.setActive(true); // defaulting to active when creating
        return product;
    }

    private ProductResponseDto convertProductToProductResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategory(product.getCategory());
        dto.setImage(product.getImage());
        dto.setActive(product.isActive());
        return dto;
    }


}
