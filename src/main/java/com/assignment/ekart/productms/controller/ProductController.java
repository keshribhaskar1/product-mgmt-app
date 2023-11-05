package com.assignment.ekart.productms.controller;

import com.assignment.ekart.productms.model.ProductDetails;
import com.assignment.ekart.productms.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assignment.ekart.productms.config.Constant.*;

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
        String status = productDetails.get(0).getError();
        if(StringUtils.isEmpty(status)){
            return new ResponseEntity<>(productDetails,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(productDetails, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable Integer productId) throws Exception {
        ProductDetails productDetails = productService.getProductById(productId);
        String status = productDetails.getError();
        if(StringUtils.isEmpty(status)) {
            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(productDetails, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<String> addNewProduct(@Valid @RequestBody ProductDetails product) throws Exception {
        String message = productService.addProduct(product);
        if(message.equalsIgnoreCase(SUCCESS_ADD_PRODUCT)){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
        }

    }

    @DeleteMapping(value = "/deleteProduct")
    public ResponseEntity<String> deleteProduct() throws Exception {
        String message = productService.deleteProduct();
        if(message.equalsIgnoreCase(DELETION_SUCCESS)){
            return new ResponseEntity<>(message, HttpStatus.OK);
        }else if(message.equalsIgnoreCase(PRODUCT_NOT_FOUND)){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer productId) throws Exception {
        String message = productService.deleteProductById(productId);
        if(message.equalsIgnoreCase(DELETION_SUCCESS)){
            return new ResponseEntity<>(message, HttpStatus.OK);
        }else if(message.equalsIgnoreCase(DELETION_FAILED + PRODUCT_NOT_FOUND)){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateProduct/{productId}/{availQty}")
    public ResponseEntity<String> updateProducts(@PathVariable("productId") int productId,@PathVariable("availQty") int availQty){
        ResponseEntity<String> message = productService.updateProducts(productId,availQty);
        return message;
    }

}
