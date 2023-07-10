package com.mno.shop.service;


import com.mno.shop.config.JwtService;
import com.mno.shop.dto.DashBoardDto;
import com.mno.shop.dto.PostorDto;
import com.mno.shop.entity.Product;
import com.mno.shop.entity.Product_order;
import com.mno.shop.entity.User;
import com.mno.shop.repo.Product_order_Repo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Product_orderService {

    private final Product_order_Repo orderRepo;
    private final UserService userService;
    private final JwtService jwtService;
    private final ProductService productService;



    public List<Product_order> getPostors(){
        return orderRepo.findAll();
    }

    public List<Product_order> getPostorsByUser(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);
        return orderRepo.findAllByUser(user, Sort.by("id").descending());
    }

    public Optional<Product_order> getPostor(Long id){
        System.out.println(orderRepo.findById(id).toString());
        return orderRepo.findById(id);
    }

    public Product_order addPostor(PostorDto postorDto, HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);
        System.out.println(postorDto.getProduct_id()+" "+postorDto.getBulk());
        Product product = productService.getProduct(postorDto.getProduct_id()).orElse(null);

        System.out.println(postorDto.toString());

        Product_order postor = Product_order.builder()
                .order_date(LocalDate.now())
                .order_bulk(postorDto.getBulk())
                .color(postorDto.getColor())
                .size(postorDto.getSize())
                .payment_price(postorDto.getPayment_price())
                .product(product)
                .accept(false)
                .user(user)
                .build();
        System.out.println("save" + postor.toString());
       return orderRepo.save(postor);
    }

    public List<Product_order> orderedByOwner(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);

        List<Product> findProducts = productService.getAllByOwner(user);
        List<Product_order> postorList = new ArrayList<>();
        for (Product product:findProducts){
            List<Product_order> postors = orderRepo.findAllByProduct(product,Sort.by("id").descending());

            if (postors != null){
                postorList.addAll(postors);
            }
        }
        return postorList;

    }


    public void changeAccept(PostorDto postor, HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        User user = userService.userfindByEmail(userEmail);

        Product_order postor1= orderRepo.findById(postor.getId()).orElse(null);

        assert postor1 != null;
        if (user == postor1.getProduct().getOwner()){
            if(!postor1.isAccept()){
                orderRepo.changeAccept(postor1.getId(),true);
            }else {
                orderRepo.changeAccept(postor1.getId(),false);

            }

        }

    }


    public DashBoardDto monthList(DashBoardDto dashBoardDto, HttpServletRequest request){
        List<Product_order> orders = orderedByOwner(request);

        ArrayList<Integer> data = new ArrayList<>();
        ArrayList<Month> months = new ArrayList<>(Arrays.stream(Month.values()).toList());
        ArrayList<String> labels = new ArrayList<>();
        List<Product_order> orderList = new ArrayList<>();
        for (Month mon: months) {
            labels.add(mon.name());
        }
        for (Product_order product_order: orders) {
                if (product_order.getOrder_date().getYear() == dashBoardDto.getYear()){
                    orderList.add(product_order);
                }
        }
        for (Month month: months){
            int i = 0;
            for (Product_order order : orderList){
                if (order.getOrder_date().getMonth() == month){
                    i+= 1;
                }
            }
            data.add(i);
        }

        DashBoardDto boardDto = DashBoardDto.builder()
                .labels(labels)
                .data(data)
                .build();



        return boardDto;
    }



    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);

    }




}
