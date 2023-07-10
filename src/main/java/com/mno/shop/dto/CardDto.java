package com.mno.shop.dto;


import com.mno.shop.entity.Product;
import com.mno.shop.entity.ProductCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private ProductCard productCard;
    private Product product;

}
