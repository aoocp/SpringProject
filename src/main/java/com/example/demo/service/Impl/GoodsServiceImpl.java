package com.example.demo.service.Impl;

import com.example.demo.bean.Goods;
import com.example.demo.mapper.GoodsMapper;
import com.example.demo.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 获取热销商品业务流程：
     * 1.返回商品列表
     */
    @Override
    public List<Goods> getHotList() {
        return findHotList();
    }

    /**
     * 通过id获取商品详细信息的业务流程：
     * 1.返回商品商品对象
     */
    @Override
    public Goods getById(Long id) {
        return findById(id);
    }



    private List<Goods> findHotList() {
        return goodsMapper.findHotList();
    }

    private Goods findById(Long id) {
        return goodsMapper.findById(id);
    }
}
