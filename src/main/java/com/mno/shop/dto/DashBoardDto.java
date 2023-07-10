package com.mno.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardDto {
    private ArrayList<Integer> data;
    private ArrayList<String> labels;
    private LocalDate date;
    private int year;



}
