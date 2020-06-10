package com.example.demo.mapper;

import com.example.demo.bean.User;

/**
 * 程序设计实践中
 *
 */

public interface UserMapper {
    /**
     * 创建一个新用户的插入语句
     * @param user
     * @return
     */
    Integer insert(User user);

    /**
     * 通过用户名查找该用户是否存在
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findByUid(Integer uid);

    int updatePassword(Integer uid, String password);

    int updateInfo(User user);
}
