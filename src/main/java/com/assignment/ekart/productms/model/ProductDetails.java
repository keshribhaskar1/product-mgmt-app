package com.assignment.ekart.productms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetails {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer productId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String category;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brand;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer availableQuantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
}
