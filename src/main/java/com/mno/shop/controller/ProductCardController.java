package com.mno.shop.controller;


import com.mno.shop.config.JwtService;
import com.mno.shop.dto.CardDto;
import com.mno.shop.entity.Product;
import com.mno.shop.entity.ProductCard;
import com.mno.shop.entity.User;
import com.mno.shop.service.ProductCardService;
import com.mno.shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/card")
@RequiredArgsConstructor
public class ProductCardController {

    private final ProductCardService productCardService;
    private final JwtService jwtService;
    private final ProductService productService;


    @PostMapping("add")
    private void add(@RequestBody ProductCard card, HttpServletRequest request){
        User user = jwtService.getuser(request);
        ProductCard productCard = ProductCard.builder()
                .user(user)
                .product_id(card.getProduct_id())
                .build();
          productCardService.add(productCard);

    }

    @DeleteMapping("delete/{id}")
    public void deletePro(@PathVariable("id") Long id){

       productCardService.delete(id);
    }

    @GetMapping("all")
    public List<CardDto> getAllByUser(HttpServletRequest request){
        User user = jwtService.getuser(request);
        List<ProductCard> productCards = productCardService.getAllByUser(user);
        List<CardDto> cardDtos=new ArrayList<>();

        for (ProductCard productCard:productCards){
            Product product = productService.getProduct(productCard.getProduct_id()).orElse(null);
            if (product!= null) {
                User resuser = User.builder()
                        .name(product.getOwner().getName())
                        .gmail(product.getOwner().getGmail())
                        .role(product.getOwner().getRole())
                        .build();
                Product resProduct = Product.builder()
                        .id(product.getId())
                        .date(product.getDate())
                        .price_original(product.getPrice_original())
                        .brand(product.getBrand())
                        .post_img(product.getPost_img())
                        .post_text(product.getPost_text())
                        .category(product.getCategory())
                        .name(product.getName())
                        .size(product.getSize())
                        .color(product.getColor())
                        .owner(resuser)
                        .build();
                ProductCard productCard1 = ProductCard.builder()
                        .user(resuser)
                        .product_id(product.getId())
                        .build();

                CardDto cardDto = CardDto.builder()
                        .productCard(productCard)
                        .product(resProduct)
                        .build();
                cardDtos.add(cardDto);


            }

        }


        return cardDtos;
    }


}
