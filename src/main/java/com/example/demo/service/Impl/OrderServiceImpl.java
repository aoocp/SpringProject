package com.example.demo.service.Impl;

import com.example.demo.bean.Address;
import com.example.demo.bean.CartVO;
import com.example.demo.bean.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.AddressService;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ex.InsertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private CartService cartService;

    @Resource
    private AddressService addressService;

    @Override
    @Transactional
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        Date now = new Date();

        List<CartVO> cartsList = cartService.getByCids(cids, uid);
        long totalPrice = 0L;
        for (CartVO item : cartsList) {
            totalPrice += item.getPrice() * item.getNum();
        }

        Address address = addressService.getByAid(aid);

        Order order = new Order();
        order.setUid(uid);
        order.setRecvAddress(address.getProvinceName() + address.getCityName() + address.getAreaName() + address.getAddress());
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setTotalPrice(totalPrice);
        order.setState(0);//未支付
        order.setCreatedUser(username);
        order.setCreatedTime(now);

        insertOrder(order);
        System.out.println(order);
        return order;
    }

    /**
     * 插入订单数据
     *
     * @param order 订单数据对象
     */
    private void insertOrder(Order order) {
        System.out.println(order);
        int rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("数据库异常，插入数据失败");
        }
    }
}
