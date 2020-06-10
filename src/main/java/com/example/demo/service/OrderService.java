package com.example.demo.service;

import com.example.demo.bean.Order;

public interface OrderService {
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
