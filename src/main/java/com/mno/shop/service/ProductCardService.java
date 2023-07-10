package com.mno.shop.service;


import com.mno.shop.entity.ProductCard;
import com.mno.shop.entity.User;
import com.mno.shop.repo.ProductCardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCardService {

    private final ProductCardRepo productCardRepo;


    public void add(ProductCard productCard){
        productCardRepo.save(productCard);
    }

    public void delete(Long id){
        productCardRepo.deleteById(id);
    }

    public List<ProductCard> getAllByUser(User user){
        return productCardRepo.findByUser(user,Sort.by("id").descending());
    }



}
