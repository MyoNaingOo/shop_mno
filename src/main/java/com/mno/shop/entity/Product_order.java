package com.mno.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product_order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    private LocalDate order_date;

    private  int order_bulk;
    private int payment_price;

    private boolean accept;

    private String color;
    private String size;


}
