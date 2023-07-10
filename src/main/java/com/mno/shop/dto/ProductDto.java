package com.mno.shop.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String category;
    private int price;
    private String post_text;
    private String brand;
    private ArrayList<String> post_img;
    private LocalDate date;
    private ArrayList<String> color;
    private ArrayList<String> size;
    private String ownername;
    private int start;
    private int end;


    private Long product_id;
    private int price_original;
    private int price_discount;
    private int bulk_discount;
    private boolean sold_out;
    private LocalDate created_at;
    private LocalDate delete_at;

}
