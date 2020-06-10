package com.example.demo.controller;

import com.example.demo.bean.BaseEntity;
import com.example.demo.bean.Order;
import com.example.demo.service.OrderService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    @Resource
    private OrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        //获取用户id和用户名
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        Order order = orderService.create(aid, cids, uid, username);

        return new JsonResult<Order>(order);
    }

}

