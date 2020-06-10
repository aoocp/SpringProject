package com.example.demo.controller;

import com.example.demo.bean.Cart;
import com.example.demo.bean.CartVO;
import com.example.demo.service.CartService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Resource
    private CartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(@RequestBody Cart cart, HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层对象执行加入购物车
        cartService.addToCart(cart, uid, username);

        JsonResult<Void> jr = new JsonResult<Void>();
        jr.setState(SUCCESS);
        jr.setMessage("添加购物车成功");
        return jr;
    }

    @GetMapping("/")
    public JsonResult<List<CartVO>> get(HttpSession session) {
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);

        List<CartVO> list = cartService.getByUid(uid);

        return new JsonResult<List<CartVO>>(list);
    }

    @GetMapping("get_by_cids")
    public JsonResult<List<CartVO>> getByCids(Integer[] cids, HttpSession session) {
        //判断cids是否为null
        if (cids == null) {
            JsonResult<List<CartVO>> jr = new JsonResult<List<CartVO>>();
            jr.setState(SUCCESS);
            return jr;
        }
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);

        List<CartVO> list = cartService.getByCids(cids, uid);

        return new JsonResult<List<CartVO>>(list);
    }

    @RequestMapping("increase/{cid}")
    public JsonResult<Integer> increase(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从Session中获取username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        Integer num = cartService.increase(cid, uid, username);

        JsonResult<Integer> jr = new JsonResult<Integer>(SUCCESS);
        jr.setData(num);
        return jr;
    }

    @RequestMapping("reduce/{cid}")
    public JsonResult<Integer> reduce(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从Session中获取username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        Integer num = cartService.reduce(cid, uid, username);

        JsonResult<Integer> jr = new JsonResult<Integer>(SUCCESS);
        jr.setData(num);
        return jr;
    }
}
