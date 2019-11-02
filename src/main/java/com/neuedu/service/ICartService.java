package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Cart;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ICartService {
    /***
     * 添加商品到购物车
     */
    public ServerResponse addProductToCart(Integer userId,Integer productId,Integer count);
    /**
     * 购物车列表
     */

    public ServerResponse list(Integer userId);
    /**
     * 更新购物车中某个商品的数量
     */
    public ServerResponse update(Integer userId,Integer productId,Integer count);
    /**
     * 移除购物车中某个商品
     */
    public ServerResponse delete_product(Integer userId,String productIds);
    /**
     * 根据用户id查看用户已选中的商品
     */
    public  ServerResponse<List<Cart>> findCartsByUseridCheck(Integer userId);
    /**
     * 批量删除购物车中的商品
     */
    public ServerResponse deleteBatch(List<Cart> cartList);
}
