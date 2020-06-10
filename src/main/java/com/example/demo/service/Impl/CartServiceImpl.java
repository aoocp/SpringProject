package com.example.demo.service.Impl;

import com.example.demo.bean.Cart;
import com.example.demo.bean.CartVO;
import com.example.demo.mapper.CartMapper;
import com.example.demo.service.CartService;
import com.example.demo.service.ex.AccessDeniedException;
import com.example.demo.service.ex.CartNotFoundException;
import com.example.demo.service.ex.InsertException;
import com.example.demo.service.ex.UpdateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;

    @Override
    public void addToCart(Cart cart, Integer uid, String username) {
        Date now = new Date();
        Cart cartTemp = findByUidAndGid(uid, cart.getGid());
        if (cartTemp == null) {
            cart.setUid(uid);
            cart.setCreatedTime(now);
            cart.setNum(1);
            cart.setCreatedUser(username);
            insert(cart);
            return;
        }

        int num = cartTemp.getNum() + cart.getNum();
        int cid = cartTemp.getCid();
        updateNum(cid, num);
    }



    @Override
    public List<CartVO> getByUid(Integer uid) {
        return findByUid(uid);
    }

    @Override
    public Integer increase(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException {
        Cart cart = findByCid(cid);
        if (cart == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }
        if (cart.getUid() != uid) {
            throw new AccessDeniedException("没有更改该用户车的权限");
        }

        Integer newNum = cart.getNum() + 1;
        updateNum(cid, newNum);

        return newNum;
    }

    @Override
    public Integer reduce(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException {
        Cart cart = findByCid(cid);
        if (cart == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }

        if (cart.getUid() != uid) {
            throw new AccessDeniedException("没有更改该用户车的权限");
        }
        Integer newNum = cart.getNum() - 1;
        //判断是否小于等于0
        if (newNum <= 0) {
            //TODO 删除该商品
            delete(cid);
            return 0;
        }
        updateNum(cid, newNum);
        return newNum;
    }


    @Override
    public List<CartVO> getByCids(Integer[] cids, Integer uid) {
        List<CartVO> results = findByCids(cids);

        Iterator<CartVO> it = results.iterator();
        while (it.hasNext()) {
            if (uid != it.next().getUid()) {
                it.remove();
            }
        }
        return results;
    }

    /**
     * 通过uid和商品的gid返回Cart
     * @param uid
     * @param gid
     * @return
     */
    private Cart findByUidAndGid(Integer uid, Long gid) {
        return cartMapper.findByUidAndGid(uid, gid);
    }

    /**
     * 修改购物车中商品的数量
     *
     * @param cid          购物车数据的id
     * @param num          新的商品数量
     * @throws UpdateException
     */
    private void updateNum(Integer cid, Integer num)
            throws UpdateException {
        Integer rows = cartMapper.updateNum(cid, num);
        if (rows != 1) {
            throw new UpdateException("更新商品数量失败！更新数据时出现未知错误！");
        }
    }

    /**
     * 插入购物车数据
     *
     * @param cart 购物车数据
     * @throws InsertException
     */
    private void insert(Cart cart) throws InsertException {
        Integer rows = cartMapper.insert(cart);
        if (rows != 1) {
            throw new InsertException("将商品添加到购物车失败！插入数据时出现未知错误！");
        }
    }

    /**
     * 通过用户id查询有关显示购物车相关的数据
     *
     * @param uid 用户id
     * @return 购物车相关数据，通过类CartVO携带数据
     */
    private List<CartVO> findByUid(Integer uid) {
        return cartMapper.findByUid(uid);
    }

    /**
     * 通过购物车id查询有关显示购物车相关的数据
     *
     * @param cid 购物车数据id
     * @return 匹配购物车数据，如果没有匹配的数据则返回null
     */
    private Cart findByCid(Integer cid) {
        return cartMapper.findByCid(cid);
    }

    /**
     * 通过多个购物车id查询有关显示购物车相关的数据集合
     *
     * @param cids 多个购物车数据id
     * @return 匹配购物车数据集合，如果没有匹配的数据则返回null
     */
    private List<CartVO> findByCids(Integer[] cids) {
        return cartMapper.findByCids(cids);
    }

    /**
     * 删除该商品
     * @param cid
     */
    private void delete(Integer cid) {
        cartMapper.delete(cid);
    }
}
