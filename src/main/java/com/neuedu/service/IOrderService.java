package com.neuedu.service;

import com.neuedu.common.ServerResponse;

import java.util.Map;

public interface IOrderService {
    public ServerResponse creatOrder(Integer userId,Integer shippingId);
    /**
     * 支付接口
     */
    public ServerResponse pay(Integer userId,Long orderNo);
    /**
     * 支付回调接口
     */
    public String callback(Map<String,String> requestParams);




}
