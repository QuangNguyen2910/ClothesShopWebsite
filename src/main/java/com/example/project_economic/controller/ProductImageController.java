package com.example.project_economic.controller;

import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductImageController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]>findProductById(@PathVariable Long id){
        ProductEntity product=this.productService.findByIdInRescontroller(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(product.getImage_type()))
                .body(product.getData());
    }
}

//