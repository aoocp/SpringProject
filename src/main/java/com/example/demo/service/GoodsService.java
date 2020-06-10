package com.example.demo.service;

import com.example.demo.bean.Goods;

import java.util.List;


public interface GoodsService {
    List<Goods> getHotList();

    Goods getById(Long id);
}
