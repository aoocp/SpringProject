package com.example.demo.mapper;

import com.example.demo.bean.Cart;
import com.example.demo.bean.CartVO;


import java.util.List;

public interface CartMapper {

    Cart findByUidAndGid(Integer uid, Long gid);

    Integer updateNum(Integer cid, Integer num);

    Integer insert(Cart cart);

    List<CartVO> findByUid(Integer uid);

    Cart findByCid(Integer cid);

    List<CartVO> findByCids(Integer[] cids);

    void delete(Integer cid);
}
