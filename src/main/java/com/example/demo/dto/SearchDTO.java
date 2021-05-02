package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private Integer pageRows = 10;
    private Integer pageNo = 1;
    @NotNull
    private String name;
    @NotNull
    private String vacationYear;
}
