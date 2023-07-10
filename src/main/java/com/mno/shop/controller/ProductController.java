package com.mno.shop.controller;

import com.mno.shop.config.JwtService;
import com.mno.shop.dto.PriceDto;
import com.mno.shop.dto.ProductDto;
import com.mno.shop.entity.Product;
import com.mno.shop.entity.User;
import com.mno.shop.service.ProductService;
import com.mno.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("add")
    public void addProduct(@RequestBody ProductDto productDto, HttpServletRequest request){
        User user = jwtService.getuser(request);

        Product createproduct =Product.builder()
                .name(productDto.getName())
                .post_img(productDto.getPost_img())
                .price_original(productDto.getPrice())
                .color(productDto.getColor())
                .size(productDto.getSize())
                .brand(productDto.getBrand())
                .category(productDto.getCategory())
                .date(LocalDate.now())
                .owner(user)
                .post_text(productDto.getPost_text())
                .build();
        productService.addProduct(createproduct);

    }

    @GetMapping("products")
    public List<Product> getPros(){
        return  productService.getProducts();
    }

    @GetMapping("name/{name}")
    public Optional<Product> getProname(@PathVariable("name") String name){
        return  productService.getproByName(name);
    }

    @GetMapping(value = "product/{id}",consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Product> getPro(@PathVariable("id") Long id){
        return  productService.getProduct(id);
    }

    @DeleteMapping("delete/{id}")
    public void deletePro(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("email/{email}")
    public List<Product> getByOwnerEmail(@PathVariable("email") String email){
        return productService.findByEmail(email);

    }

    @GetMapping("ownername/{name}")
    public List<Product> findByOwnerName(@PathVariable("name") String name){
        return productService.findByName(name);
    }

    @GetMapping("PostByOwner")
    private List<Product> getPostByOwner(HttpServletRequest request){
        User user = jwtService.getuser(request);
        return  productService.getAllByOwner(user);
    }

    @GetMapping("find/{find}")
    private List<Product> findProduct(@PathVariable("find") String find) {

        return productService.findProduct(find);
    }


    @PostMapping("updatePrice")
    private void updatePrice(@RequestBody PriceDto priceDto){
        productService.updateProductPrice(priceDto);
    }



    @PostMapping("changeSold_out")
    private void changeSold_out(@RequestBody ProductDto productDto){
        productService.changeSold_out(productDto.getProduct_id(), productDto.isSold_out());

    }


}
