package com.assignment.ekart.productms.controller;

import com.assignment.ekart.productms.model.ProductDetails;
import com.assignment.ekart.productms.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/productApi")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/product")
    public ResponseEntity<List<ProductDetails>> getAllProducts() throws Exception {
        List<ProductDetails> productDetails = productService.getAllProducts();
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable Integer productId) throws Exception {
        ProductDetails productDetails = productService.getProductById(productId);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<String> addNewProduct(@Valid @RequestBody ProductDetails product) throws Exception {
        String message = productService.addProduct(product);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct() throws Exception {
        String message = productService.deleteProduct();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
