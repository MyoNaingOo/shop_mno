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
public class PostorDto {

    private Long id;
    private int payment_price;
    private LocalDate order_date;
    private Long product_id;
    private int bulk;
    private String color;
    private String size;
    private LocalDate startDate;
    private LocalDate endDate;
}
