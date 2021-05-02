package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseMessage<T> {
    private T data;
    private String message = "";
}
