package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private Integer pageRows = 10;
    private Integer pageNo = 1;
    private Long memberId;
    private String vacationYear;
}
