package com.example.demo.service;

import com.example.demo.bean.Cart;
import com.example.demo.bean.CartVO;
import com.example.demo.service.ex.AccessDeniedException;
import com.example.demo.service.ex.CartNotFoundException;
import com.example.demo.service.ex.UpdateException;

import java.util.List;

public interface CartService {
    /**
     * 添加商品到购物车
     * @param cart
     * @param uid
     * @param username
     */
    public void addToCart(Cart cart, Integer uid, String username);

    /**
     * 通过uid返回该用户的购物车
     * @param uid
     * @return
     */
    public List<CartVO> getByUid(Integer uid);

    /**
     *购物车商品加一
     * @param cid
     * @param uid
     * @param username
     * @return
     * @throws CartNotFoundException
     * @throws AccessDeniedException
     * @throws UpdateException
     */
    public Integer increase(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException;

    /**
     * 购物车商品减一
     * @param cid
     * @param uid
     * @param username
     * @return
     * @throws CartNotFoundException
     * @throws AccessDeniedException
     * @throws UpdateException
     */
    public Integer reduce(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException;

    /**
     * 通过多个购物车id查询有关显示购物车相关的数据集合
     *
     * @param cids 多个购物车数据id
     * @param uid  用户id
     * @return匹配购物车数据集合，如果没有匹配的数据则返回null
     */
    public List<CartVO> getByCids(Integer[] cids, Integer uid);
}
