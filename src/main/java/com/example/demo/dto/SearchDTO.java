package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private Integer pageRows = 10;
    private Integer pageNo = 1;
    private String vacationYear;
}
