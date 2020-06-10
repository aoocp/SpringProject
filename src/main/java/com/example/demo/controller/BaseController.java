package com.example.demo.controller;

import com.example.demo.controller.ex.ControllerException;
import com.example.demo.service.ex.*;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    /**
     * 成功状态
     */
    protected static final Integer SUCCESS = 0;

    @ExceptionHandler({ControllerException.class, ServiceException.class})
    public JsonResult<Void> handlerException(Throwable e){
        JsonResult<Void> jr = new JsonResult<Void>(e.getMessage());
        if(e instanceof UsernameDuplicateException) {
            //4001-用户名冲突异常类
            jr.setState(4001);
        }else if(e instanceof UserNotFoundException){
            //4002-用户不存在
            jr.setState(4002);
        }else if(e instanceof PasswordNotMatchException){
            //4003-密码不匹配
            jr.setState(4003);
        }else if(e instanceof AddressCountLimitException){
            //4004-地址数量超过上限
            jr.setState(4004);
        }else if(e instanceof AddressNotFoundException){
            //4005-地址找不到
            jr.setState(4005);
        } else if(e instanceof InsertException){
            //5000-插入数据异常
            jr.setState(5000);
        }else if(e instanceof UpdateException){
            //5001-数据更新异常
            jr.setState(5001);
        }else if(e instanceof DeleteException){
            //5002-删除数据异常
            jr.setState(5002);
        }
        return jr;
    }


    /**
     * 从Session中获取当前登录的用户的id
     *
     * @param session
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从Session中获取当前登录的用户名
     *
     * @param session
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
