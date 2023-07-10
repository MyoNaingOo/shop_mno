package com.mno.shop.controller;


import com.mno.shop.dto.DashBoardDto;
import com.mno.shop.dto.MessageDto;
import com.mno.shop.dto.PostorDto;
import com.mno.shop.entity.Product_order;
import com.mno.shop.entity.User;
import com.mno.shop.service.Product_orderService;
import com.mno.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/postor")
@RequiredArgsConstructor
public class Product_orderController {


    private final Product_orderService productOrderService;
    private final UserService userService;


    @GetMapping("ords")
    public List<Product_order> getPostors(){
      return productOrderService.getPostors();
    }

    @GetMapping("bought")
    public List<Product_order> getPostorsByUser(HttpServletRequest request){
        return productOrderService.getPostorsByUser(request);
    }

    @GetMapping("ord/{id}")
    public Optional<Product_order> getPostor(@PathVariable("id") Long id){
        return productOrderService.getPostor(id);
    }

    @PostMapping("order")
    public Product_order addPostor(@RequestBody PostorDto postorDto, HttpServletRequest request){
        System.out.println(postorDto.toString());
        return  productOrderService.addPostor(postorDto,request);
    }

    @GetMapping("ordered")
    public List<Product_order> orderedByUser(HttpServletRequest request){
        return productOrderService.orderedByOwner(request);
    }

    @PutMapping("accept")
    public void changeAccept(@RequestBody PostorDto postor,HttpServletRequest request){
        System.out.println(postor.getId());
        productOrderService.changeAccept(postor,request);
    }

    @DeleteMapping("delete/{id}")
    public MessageDto deleteOrder(@PathVariable("id") Long id){
        Product_order productOrder =  productOrderService.getPostor(id).orElse(null);

        assert productOrder != null;
        if (!productOrder.isAccept()){
            productOrderService.deleteOrder(id);
            return MessageDto.builder()
                    .message("Order is delete")
                    .build();
        }else {
            return MessageDto.builder()
                    .message("Shop is accepted.So you can not delete")
                    .build();
        }
    }

    @PostMapping("dash")
    public DashBoardDto OrderDash(@RequestBody DashBoardDto dashBoardDto,HttpServletRequest request){
        System.out.println(dashBoardDto.getYear());
        return productOrderService.monthList(dashBoardDto,request);

    }







}
