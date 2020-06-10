package com.example.demo.service.Impl;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.service.ex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 将用户数据直接插入，并未做任何加密
     * @param user 用户数据对象
     */
    @Override
    public void reg(User user)throws UsernameDuplicateException, InsertException{
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result != null) {
            throw new UsernameDuplicateException("注册失败！ " + username + "已经被占用！");
        }
        //封装日志属性
        Date now = new Date();
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        /**
         * 插入数据库
         */
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("注册失败！写入数据时出现未知错误！请联系系统管理员！");
        }
    }

    @Override
    public User log(String username,String password)throws UserNotFoundException, PasswordNotMatchException{
        User result = userMapper.findByUsername(username);

        if (result == null ||  !result.getUsername().equals(username)) {
            // 是：用户名不存在，抛出UserNotFoundException
            throw new UserNotFoundException("登录失败！ " + username + "不存在！");
        }
        if(!(password.equals(result.getPassword())) ){
            throw new PasswordNotMatchException("用户密码错误！");
        }
        return result;
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);

        if (result != null) {
            result.setPassword(null);
        }
        return result;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword)throws UserNotFoundException, PasswordNotMatchException, UpdateException{
        User user = userMapper.findByUid(uid);
        if (user == null) {
            throw new UserNotFoundException("用户不存在!");
        }
        if(!user.getPassword().equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误，无法进行修改!");
        }
        int rows = userMapper.updatePassword(uid, newPassword);
        if (rows != 1) {
            throw new UpdateException("更新失败!");
        }
    }

    @Override
    public void changeInfo(User user)throws UserNotFoundException, UpdateException {
        User result = userMapper.findByUid(user.getUid());

        if (result == null) {
            throw new UserNotFoundException("用户不存在");
        }
        user.setCreatedUser(user.getUsername());
        int rows = userMapper.updateInfo(user);
        if (rows != 1) {
            throw new UpdateException("更新数据失败！");
        }
    }
}
