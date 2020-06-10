package com.example.demo.mapper;

import com.example.demo.bean.Goods;

import java.util.List;

public interface GoodsMapper {
    List<Goods> findHotList();

    Goods findById(Long id);
}
