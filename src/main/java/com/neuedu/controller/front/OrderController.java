package com.neuedu.controller.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.IOrderService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    IOrderService orderService;
    /**
     * 创建订单接口
     * */
    @RequestMapping("{shippingid}")
    public ServerResponse creatOrder(@PathVariable("shippingid") Integer shippingId, HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.serverResponseByError("需要登录");
        }

        return orderService.creatOrder(user.getId(),shippingId);
    }
    /**
     * 支付接口
     */
    @RequestMapping("pay/{orderNo}")
    public ServerResponse pay(@PathVariable("orderNo") Long orderNo,HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.serverResponseByError("需要登录");
        }

        return orderService.pay(user.getId(),orderNo);
    }

    /**
     * 支付宝服务器回调商家服务器接口
     * @return
     */
    @RequestMapping("callback.do")
    public String alipay_callback(HttpServletRequest request){
        Map<String,String[]> callbackParam=request.getParameterMap();
        Map<String,String> signParams= Maps.newHashMap();
        Iterator<String> iterator=callbackParam.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String[] values=callbackParam.get(key);
            StringBuffer sb=new StringBuffer();
            if (values!=null&&values.length>0){
                for (int i=0;i<values.length;i++){
                 sb.append(values[i]);
                 if (i!=values.length-1){
                     sb.append(",");
                 }
            }
            }
            signParams.put(key,sb.toString());

        }
        System.out.println(signParams);
      //验签
        try {
            signParams.remove("sign_type");
            boolean result=AlipaySignature.rsaCheckV2(signParams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if (result){
                  //验证通过
                System.out.println("通过");
                return orderService.callback(signParams);
            }else{
                return "fail";
            }


        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


        return "success";
    }
}
