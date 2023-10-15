package com.mno.shop.service;


import com.mno.shop.dto.PriceDto;
import com.mno.shop.entity.Product;
import com.mno.shop.entity.User;
import com.mno.shop.repo.ProductRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final UserService userService;


    public void addProduct(Product product){

        productRepo.save(product);
    }

    public List<Product> getProducts(){
        return  productRepo.findAll(Sort.by("id").descending());

    }



    public void updateProductPrice(PriceDto priceDto){
            productRepo.changPrice(
                    priceDto.getProduct_id(),
                    priceDto.getPrice_original(),
                    priceDto.getPrice_discount(),
                    priceDto.getPercent_discount(),
                    LocalDate.now(),
                    LocalDate.now()
            );
    }

    public Optional<Product> getProduct(Long id){
        return productRepo.findById(id);
    }

    public Optional<Product> getproByName(String name){
        return productRepo.findByName(name);
    }

    public void deleteProduct(Long id){

        productRepo.deleteById(id);
    }

    public List<Product> getAllByOwner(User user) {
        return productRepo.findAllByOwner(user,Sort.by("id").descending());
    }

    public List<Product> findByEmail(String gmail){
        User owner = userService.userfindByGmail(gmail);
        return getAllByOwner(owner);
    }

    public List<Product> findByName(String name){
        User owner = userService.userfindByName(name);
        Pageable pageable = PageRequest.of(0,3);
        return productRepo.findAllByOwner(owner,pageable);
    }

    public void changeSold_out(Long id,boolean sold_out){
        productRepo.ChangeSold_out(id,sold_out);
    }

    public List<Product> findProduct(@PathVariable("find") String find) {
        List<Product> productList = new ArrayList<>();
        List<Product> products = productRepo.findAll(Sort.by("id").descending());
        for (Product product: products) {
            String proname =  product.getName().toLowerCase();
            String procate = product.getCategory().toLowerCase();
            String proowner = product.getOwner().getName().toLowerCase();
            String probrand = product.getBrand().toLowerCase();
            Pattern pattern = Pattern.compile(find.toLowerCase());
            Matcher pronameMatcher = pattern.matcher(proname);
            Matcher procateMatcher = pattern.matcher(procate);
            Matcher proownerMatcher = pattern.matcher(proowner);


            if (procateMatcher.find() | pronameMatcher.find() | proownerMatcher.find() ){
                productList.add(product);
            }

        }

        return productList;
    }

}
