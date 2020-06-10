package com.example.demo.bean;

/**
 * 购物车实体类
 */
public class Cart extends BaseEntity{
    private static final long serialVersionUID = 1L;
    //购物车id
    private int cid;
    //用户id
    private int uid;
    //商品id
    private long gid;
    //商品数量
    private int num;


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
