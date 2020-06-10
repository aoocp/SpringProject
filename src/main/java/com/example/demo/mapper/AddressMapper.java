package com.example.demo.mapper;

import com.example.demo.bean.Address;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    public Integer insert(Address address);

    public Integer countByUid(Integer uid);

    public List<Address> findByUid(Integer uid);

    public Address findByAid(Integer aid);

    public int deleteByAid(Integer aid);

    public Integer updateDefault(Integer aid);

    public Integer updateNonDefault(Integer uid);

    Address findLastModified(Integer uid);
}
