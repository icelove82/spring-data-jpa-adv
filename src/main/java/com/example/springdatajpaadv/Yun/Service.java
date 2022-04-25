package com.example.springdatajpaadv.Yun;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class Service {
    private final Repository repository;

    public List<Total> getData() {
        return repository.findYun();
    }
}
