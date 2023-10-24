package com.assignment.ekart.productms.service;

import com.assignment.ekart.productms.entity.AllProductEntity;
import com.assignment.ekart.productms.model.ProductDetails;
import com.assignment.ekart.productms.repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                throw new Exception("No product is available.");
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
            AllProductEntity product = productData.orElseThrow(() -> new Exception("Product not available"));
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
            return "Product added.";
        }catch (Exception e){
            return "Product addition failed. "+ e.getMessage();
        }
    }

    @Override
    public String deleteProduct() {
        List<AllProductEntity> productData = productRepo.findAll();
        if(productData.isEmpty()){
            return "Product is not present.";
        }else{
            productRepo.deleteAll();
            return "Product deleted.";
        }
    }
}
