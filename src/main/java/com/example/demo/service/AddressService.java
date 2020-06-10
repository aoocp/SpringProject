package com.example.demo.service;

import com.example.demo.bean.Address;
import com.example.demo.service.ex.*;

import java.util.List;

public interface AddressService {
    /**
     * 地址的数量最大值
     */
    public static int ADDRESS_MAX_COUNT = 20;

    /**
     * 创建新的地址
     * @param address
     * @param uid
     * @param username
     */
    void addnew(Address address, int uid, String username)throws AddressCountLimitException, InsertException;

    /**
     * 根据uid获取该用户的所有收获地址
     * @param uid
     * @return
     */
    public List<Address> getByUid(Integer uid);

    /**
     * 设置默认收货地址
     * @param aid
     * @param uid
     * @param username
     * @throws AddressNotFoundException
     * @throws AccessDeniedException
     * @throws UpdateException
     */
    public void setDefault(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, UpdateException;

    /**
     * 删除收货地址
     * @param aid
     * @param uid
     * @param username
     * @throws AddressNotFoundException
     * @throws AccessDeniedException
     * @throws DeleteException
     */
    public void delete(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, DeleteException;

    /**
     * 获得aid对应的收货地址
     * @param aid
     * @return
     */
    public Address getByAid(Integer aid);
}
