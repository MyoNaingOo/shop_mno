package com.mno.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private Long product_id;
    private int price_original;
    private int price_discount;
    private int percent_discount;
    private boolean sold_out;
    private LocalDate created_at;
    private LocalDate delete_at;
}
