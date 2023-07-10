package com.mno.shop.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Lob
    @Column(length = 2000)
    private String post_text;

    @Lob
    @Column(length = 1000)
    private ArrayList<String> post_img;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;

    private int price_original;

    @Column(nullable = true)
    private int price_discount;
    @Column(nullable = true)
    private int percent_discount;
    @Column(nullable = true)
    private boolean sold_out;
    @Column(nullable = true)
    private LocalDate created_at;
    @Column(nullable = true)
    private LocalDate delete_at;

    private String brand;
    private String category;

    private List<String> color;
    private List<String> size;




}
