package com.example.demo.service.Impl;

import com.example.demo.bean.Address;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.service.AddressService;
import com.example.demo.service.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource private AddressMapper addressMapper;

    /**
     * 增加新地址
     */
    @Override
    public void addnew(Address address, int uid, String username)throws AddressCountLimitException, InsertException {
        int count = countByUid(uid);
        if (count > ADDRESS_MAX_COUNT) {
            throw new AddressCountLimitException("地址数量超过上限");
        }
        //如果是第一次创建的地址，设为默认地址
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        address.setUid(uid);

        Date now = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        //将地址插入
        insert(address);
    }

    /**
     * 根据用户id获取所有的收货地址
     */
    @Override
    public List<Address> getByUid(Integer uid) {
        return findByUid(uid);
    }

    @Override
    @Transactional
    public void setDefault(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, UpdateException {
        Address address = findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("错误：该地址不存在");
        }
        if (address.getUid() != uid) {
            throw new AccessDeniedException("地址操作对象不是该用户所有");
        }
        updateNonDefault(uid);
        updateDefault(aid);
    }

    /**
     * 删除
     * @param aid
     * @param uid
     * @param username
     * @throws AddressNotFoundException
     * @throws AccessDeniedException
     * @throws DeleteException
     */
    @Override
    @Transactional
    public void delete(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, DeleteException {
        Address address = findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("选择删除的地址不存在");
        }
        if (address.getUid() != uid) {
            throw new AccessDeniedException("地址操作对象不是该用户所有");
        }
        delete(aid);
        //判断删除的是否是默认地址
        if (address.getIsDefault() == 0) {
            return;
        }

        int count = countByUid(uid);
        if (count == 0) {
            return;
        }
        //设置新的默认地址
        Address lastModifiedAddress = findLastModified(uid);
        updateDefault(lastModifiedAddress.getAid());
    }

    @Override
    public Address getByAid(Integer aid) {
        return findByAid(aid);
    }


    /**
     * 查找uid所对应用户的地址数量
     * @param uid
     * @return
     */
    private Integer countByUid(Integer uid) {
        return addressMapper.countByUid(uid);
    }

    /**
     * 插入
     * @param address
     */
    private void insert(Address address) {
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("数据库异常，无法插入数据");
        }
    }

    /**
     * 通过uid查找该用户所有的地址
     * @param uid
     * @return
     */
    private List<Address> findByUid(Integer uid) {
        return addressMapper.findByUid(uid);
    }

    /**
     * 通过aid查找对应的地址
     * @param aid
     * @return
     */
    private Address findByAid(Integer aid) {
        return addressMapper.findByAid(aid);
    }

    /**
     * 通过aid删除对应的地址
     * @param aid
     */
    private void delete(Integer aid){
        int rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("数据库异常，删除数据失败");
        }
    }

    /**
     * 更新为 非 默认地址
     * @param uid
     * @throws UpdateException
     */
    private void updateNonDefault(Integer uid) throws UpdateException {
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows == 0) {
            throw new UpdateException("更新-将所有收货地址设为非默认地址，失败！");
        }
    }
    /**
     * 更新为默认地址
     */
    private void updateDefault(Integer aid) throws UpdateException {
        Integer rows = addressMapper.updateDefault(aid);
        if (rows != 1) {
            throw new UpdateException("更新-将指定地址设为默认，失败！");
        }
    }

    /**
     * 获得最新的地址
     * @param uid
     * @return
     */
    private Address findLastModified(Integer uid) {
        return addressMapper.findLastModified(uid);
    }
}
