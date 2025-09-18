package com.scaler.advancedecommapplication.controllers;

import com.scaler.advancedecommapplication.dtos.ProductRequestDto;
import com.scaler.advancedecommapplication.dtos.ProductResponseDto;
import com.scaler.advancedecommapplication.services.ProductService;
import com.scaler.advancedecommapplication.services.SelfProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier("productService") SelfProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<ProductResponseDto>(productService.createProduct(productRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,@RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(id, productRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id){
               return ResponseEntity.ok(productService.getAllProductsById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable Long id){
        return  productService.deleteProductById(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchAllProductsByKeyword(keyword));
    }
}
