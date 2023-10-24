package com.assignment.ekart.productms.controller;

import com.assignment.ekart.productms.model.ProductDetails;
import com.assignment.ekart.productms.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(3)
    public void getAllProductsTest() throws Exception {
        String expectedData = "[{\"productId\":1,\"name\":\"Shampoo\",\"description\":\"hair shampoo\",\"category\":\"shampoo\",\"brand\":\"Clinic plus\",\"price\":1.0,\"availableQuantity\":320}]";
        List<ProductDetails> productDetails = new ArrayList<>();
        ProductDetails product = new ProductDetails();
        product.setProductId(1);
        product.setName("Shampoo");
        product.setDescription("hair shampoo");
        product.setCategory("shampoo");
        product.setBrand("Clinic plus");
        product.setPrice(1.0);
        product.setAvailableQuantity(320);
        productDetails.add(product);
        when(productService.getAllProducts()).thenReturn(productDetails);
        ResponseEntity<List<ProductDetails>> responseEntity = productController.getAllProducts();
        HttpStatusCode expected = OK;
        HttpStatusCode actual = responseEntity.getStatusCode();
        List<ProductDetails> body = responseEntity.getBody();
        String actualData = mapper.writeValueAsString(body);
        System.out.println(actualData);
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(expectedData,actualData);
    }

    @Test
    @Order(2)
    public void getProductByIdTest() throws Exception {
        int productId =1;
        String expectedData = "{\"productId\":1,\"name\":\"Shampoo\",\"description\":\"hair shampoo\",\"category\":\"shampoo\",\"brand\":\"Clinic plus\",\"price\":1.0,\"availableQuantity\":320}";
        ProductDetails product = new ProductDetails();
        product.setProductId(1);
        product.setName("Shampoo");
        product.setDescription("hair shampoo");
        product.setCategory("shampoo");
        product.setBrand("Clinic plus");
        product.setPrice(1.0);
        product.setAvailableQuantity(320);
        when(productService.getProductById(productId)).thenReturn(product);
        ResponseEntity<ProductDetails> responseEntity = productController.getProductById(productId);
        HttpStatusCode expected = OK;
        HttpStatusCode actual = responseEntity.getStatusCode();
        ProductDetails body = responseEntity.getBody();
        String actualData = mapper.writeValueAsString(body);
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(expectedData,actualData);
    }

    @Test
    @Order(1)
    public void addNewProductTest() throws Exception {
        String expectedData = "Product added.";
        ProductDetails product = new ProductDetails();
        product.setName("Shampoo");
        product.setDescription("hair shampoo");
        product.setCategory("shampoo");
        product.setBrand("Clinic plus");
        product.setPrice(1.0);
        product.setAvailableQuantity(320);

        when(productService.addProduct(product)).thenReturn("Product added.");
        ResponseEntity<String> responseEntity = productController.addNewProduct(product);

        HttpStatusCode expected = OK;
        HttpStatusCode actual = responseEntity.getStatusCode();
        String actualData = responseEntity.getBody();
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(expectedData,actualData);
    }

    @Test
    public void deleteProductTest() throws Exception {
        String expectedData = "Product deleted.";
        when(productService.deleteProduct()).thenReturn("Product deleted.");
        ResponseEntity<String> responseEntity = productController.deleteProduct();

        HttpStatusCode expected = OK;
        HttpStatusCode actual = responseEntity.getStatusCode();
        String actualData = responseEntity.getBody();
        Assertions.assertEquals(expected,actual);
        Assertions.assertEquals(expectedData,actualData);
    }

}
