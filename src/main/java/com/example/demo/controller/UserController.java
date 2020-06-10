package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Resource
    private UserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(@RequestBody User user){
        userService.reg(user);
        JsonResult<Void> jr = new JsonResult<>();
        jr.setState(SUCCESS);
        jr.setMessage("注册成功");
        return jr;
    }

    @RequestMapping("log")
    public JsonResult<User> log(String username, String password, HttpSession session){
        System.out.println(username+" "+password);
        User user = userService.log(username, password);

        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
        return new JsonResult<User>(user);
    }

    /**
     * 获得用户的信息
     * @param session
     * @return
     */
    @GetMapping("get_info")
    public JsonResult<User> getInfo(HttpSession session) {
        Integer uid = getUidFromSession(session);
        User user = userService.getByUid(uid);
        return new JsonResult<User>(user);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(@RequestParam("old_password") String oldPassword, @RequestParam("new_password") String newPassword, HttpSession session) {
        //从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //执行修改
        userService.changePassword(uid, username, oldPassword, newPassword);

        JsonResult<Void> jr = new JsonResult<Void>();
        jr.setState(SUCCESS);
        jr.setMessage("操作成功");
        return jr;
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(@RequestBody User user, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //设置为修改数据的用户名和id
        user.setUid(uid);
        user.setUsername(username);

        userService.changeInfo(user);

        JsonResult<Void> jr = new JsonResult<Void>();
        jr.setState(SUCCESS);
        jr.setMessage("操作成功");
        return jr;
    }

}