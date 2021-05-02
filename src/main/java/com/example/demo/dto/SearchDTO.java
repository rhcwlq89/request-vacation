package com.example.demo.dto;


import lombok.Data;


@Data
public class SearchDTO {
    private Integer pageRows = 10;
    private Integer pageNo = 1;
    private Long memberId;
}
