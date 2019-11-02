package com.neuedu.controller.front;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.User;
import com.neuedu.service.IShippingService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    IShippingService shippingService;
   @RequestMapping(value = "add.do")
   public ServerResponse add(Shipping shipping, HttpSession session){
       User user=(User) session.getAttribute(Const.CURRENT_USER);
       if(user==null){
           return ServerResponse.serverResponseByError("需要登录");
       }
       shipping.setUserId(user.getId());
       return shippingService.add(shipping);
   }
   @RequestMapping(value = "update.do")
   public ServerResponse update(Shipping shipping,HttpSession session){
       User user=(User) session.getAttribute(Const.CURRENT_USER);
       if(user==null){
           return ServerResponse.serverResponseByError("需要登录");
       }
       shipping.setUserId(user.getId());
       return shippingService.update(shipping);
   }
   @RequestMapping(value = "del.do")
   public ServerResponse del(Integer shippingId,HttpSession session) {
       User user = (User) session.getAttribute(Const.CURRENT_USER);
       if (user == null) {
           return ServerResponse.serverResponseByError("需要登录");
       }
       Integer userId=user.getId( );
     return shippingService.del(shippingId,userId);
   }
    /**
     * 选中查看具体的地址
     * */
    @RequestMapping(value = "select.do")

    public ServerResponse select(HttpSession session,Integer shippingId){

        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.serverResponseByError("需要登录");
        }

        return  shippingService.select(shippingId);
    }
}
