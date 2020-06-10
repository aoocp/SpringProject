package com.example.demo.service;

import com.example.demo.bean.User;

public interface UserService {
    /**
     * 注册
     * @param user 用户数据对象
     */
    public void reg(User user);

    public User log(String username,String password);

    public User getByUid(Integer uid);
    
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    void changeInfo(User user);
}
