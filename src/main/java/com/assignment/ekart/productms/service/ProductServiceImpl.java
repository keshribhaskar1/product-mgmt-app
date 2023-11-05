package com.assignment.ekart.productms.service;

import com.assignment.ekart.productms.entity.AllProductEntity;
import com.assignment.ekart.productms.model.ProductDetails;
import com.assignment.ekart.productms.repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.assignment.ekart.productms.config.Constant.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepo productRepo;
    @Override
    public List<ProductDetails> getAllProducts() {
        List<AllProductEntity> ape = productRepo.findAll();
        List<ProductDetails> pdl = new ArrayList<>();
        try{
            if(ape.isEmpty()){
                throw new Exception(PRODUCT_NOT_FOUND);
            }else{
                for(AllProductEntity p : ape) {
                    ProductDetails pd = new ProductDetails();
                    pd.setProductId(p.getProductId());
                    pd.setName(p.getName());
                    pd.setBrand(p.getBrand());
                    pd.setDescription(p.getDescription());
                    pd.setCategory(p.getCategory());
                    pd.setPrice(p.getPrice());
                    pd.setAvailableQuantity(p.getAvailableQuantity());
                    pdl.add(pd);
                }
            }
        }catch (Exception e){
            ProductDetails pd = ProductDetails.builder().error(e.getMessage()).build();
            pdl.add(pd);
        }
        return pdl;
    }

    @Override
    public ProductDetails getProductById(Integer productId) throws Exception {
        Optional<AllProductEntity> productData = productRepo.findById(productId);
        try{
            AllProductEntity product = productData.orElseThrow(() -> new Exception(PRODUCT_NOT_FOUND));
            ProductDetails pd = ProductDetails.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .brand(product.getBrand())
                    .price(product.getPrice())
                    .category(product.getCategory())
                    .description(product.getDescription())
                    .availableQuantity(product.getAvailableQuantity())
                    .build();
            return pd;
        }catch (Exception e){
            ProductDetails pdError = ProductDetails.builder().error(e.getMessage()).build();
            return  pdError;
        }


    }

    @Override
    public String addProduct(ProductDetails product) {
        try{
            AllProductEntity pe = AllProductEntity.builder()
                    .name(product.getName())
                    .availableQuantity(product.getAvailableQuantity())
                    .brand(product.getBrand())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .productId(product.getProductId())
                    .category(product.getCategory())
                    .build();
            productRepo.save(pe);
            return SUCCESS_ADD_PRODUCT;
        }catch (Exception e){
            return FAILED_ADD_PRODUCT + e.getMessage();
        }
    }

    @Override
    public String deleteProduct() {
        List<AllProductEntity> productData = productRepo.findAll();
        if(productData.isEmpty()){
            return PRODUCT_NOT_FOUND;
        }else{
            productRepo.deleteAll();
            return DELETION_SUCCESS;
        }
    }

    @Override
    public String deleteProductById(Integer id) {
        try{
            Optional<AllProductEntity> prod  = productRepo.findById(id);
            if(prod.isPresent()){
                productRepo.deleteById(id);
                return DELETION_SUCCESS;
            }else{
                throw new SQLException(PRODUCT_NOT_FOUND);
            }
        }catch (Exception e){
            return DELETION_FAILED + e.getMessage();
        }
    }

    public ResponseEntity<String> updateProducts(int productId, int availQty){
        try{
            Optional<AllProductEntity> productData = productRepo.findById(productId);
            if(productData.isPresent()){
                AllProductEntity productEntity = productData.get();
                productEntity.setAvailableQuantity(availQty);
                productRepo.save(productEntity);
            }
            return new ResponseEntity<>(UPDATE_SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(UPDATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
