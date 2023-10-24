package com.assignment.ekart.productms.repository;

import com.assignment.ekart.productms.entity.AllProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<AllProductEntity,Integer> {
}
