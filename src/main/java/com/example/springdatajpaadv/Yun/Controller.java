package com.example.springdatajpaadv.Yun;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/yun")
public class Controller {

    private final Service service;

    @GetMapping
    public List<Total> getData() {
        return service.getData();
    }
}
