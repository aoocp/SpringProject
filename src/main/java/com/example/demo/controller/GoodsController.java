package com.example.demo.controller;

import com.example.demo.bean.BaseEntity;
import com.example.demo.bean.Goods;
import com.example.demo.service.GoodsService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController extends BaseEntity {
    @Resource
    private GoodsService goodsService;

    @GetMapping("/hot")
    public JsonResult<List<Goods>> getHotList(String parent) {
        return new JsonResult<List<Goods>>(goodsService.getHotList());
    }

    @GetMapping("details/{id}")
    public JsonResult<Goods> getById(@PathVariable("id") Long id) {
        return new JsonResult<Goods>(goodsService.getById(id));
    }
}
