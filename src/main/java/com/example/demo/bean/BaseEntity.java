package com.example.demo.bean;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;

    public BaseEntity() {
    }

    public BaseEntity(String createdUser,Date createdTime) {
        this.createdTime = createdTime;
        this.createdUser = createdUser;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
