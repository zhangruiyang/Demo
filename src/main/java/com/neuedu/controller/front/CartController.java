package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.ICartService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    ICartService cartService;
    /**
     * 添加商品到购物车
     * @return
     */
    @RequestMapping( "add/{productId}/{count}")
    public ServerResponse addCart(@PathVariable("productId") Integer productId,
                                  @PathVariable("count") Integer count,
                                  HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.serverResponseByError(ResponseCode.MOT_LOGIN,"未登录");

        }

        return cartService.addProductToCart(user.getId(),productId,count);
    }
    /**
     * 购物车列表
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.serverResponseByError(ResponseCode.MOT_LOGIN,"未登录");

        }
        return cartService.list(user.getId());
    }
    /**
     * 更新购物车某个商品的数量
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session,Integer productId,Integer count){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.serverResponseByError(ResponseCode.MOT_LOGIN,"未登录");

        }
        return cartService.update(user.getId(),productId,count);
    }
    /**
     * 移除购物车中某个商品
     */
    @RequestMapping(value = "/delete_product.do")

    public ServerResponse delete_product(HttpSession session,String productIds){

        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.serverResponseByError("需要登录");
        }
        return cartService.delete_product(user.getId(),productIds);

    }


}
