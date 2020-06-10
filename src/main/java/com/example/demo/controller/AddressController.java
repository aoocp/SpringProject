package com.example.demo.controller;


import com.example.demo.bean.Address;
import com.example.demo.service.AddressService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {

    @Resource
    private AddressService addressService;

    @PostMapping("addnew")
    public JsonResult<Void> addnew(@RequestBody Address address, HttpSession session) {
        // 从Session中获取uid和username
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务层对象执行增加
        addressService.addnew(address, uid, username);
        // 响应成功
        JsonResult<Void> jr = new JsonResult<Void>();
        jr.setState(SUCCESS);
        jr.setMessage("添加地址成功");
        return jr;
    }

    @GetMapping("/")
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        //从session中获取uid
        Integer uid = getUidFromSession(session);
        List<Address> addresses = addressService.getByUid(uid);
        return new JsonResult<List<Address>>(addresses);
    }

    @RequestMapping("set_default/{aid}")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        addressService.setDefault(aid, uid, username);

        JsonResult<Void> jr = new JsonResult<Void>();
        jr.setState(SUCCESS);
        jr.setMessage("操功作成");
        return jr;
    }

    @RequestMapping("delete/{aid}")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        addressService.delete(aid, uid, username);
        JsonResult<Void> jr = new JsonResult<Void>();
        jr.setState(SUCCESS);
        jr.setMessage("操作成功");
        return jr;
    }
}
