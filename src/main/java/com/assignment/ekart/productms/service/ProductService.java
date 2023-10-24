package com.assignment.ekart.productms.service;

import com.assignment.ekart.productms.model.ProductDetails;

import java.util.List;

public interface ProductService {
    public List<ProductDetails> getAllProducts();
    public ProductDetails getProductById(Integer productId) throws Exception;
    public String addProduct(ProductDetails product);
    public String deleteProduct();
}
