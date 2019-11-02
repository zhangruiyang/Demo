package com.neuedu.service.impl;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    ShippingMapper shippingMapper;
    @Override
    public ServerResponse add(Shipping shipping) {
        if (shipping==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }
        Integer shippingId=shipping.getId();
        if (shippingId==null) {
            //添加
            int result = shippingMapper.insert(shipping);
            if (result <= 0) {
                return ServerResponse.serverResponseByError(ResponseCode.ERROR, "添加地址失败");
            }
        }
                return ServerResponse.serverResponseBySuccess(shipping.getId());

    }

    public ServerResponse update(Shipping shipping){
        if (shipping==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }
        int result=shippingMapper.updateBySelectKey(shipping);
        if (result>0){
            return ServerResponse.serverResponseBySuccess("更新地址成功");
        }
        return ServerResponse.serverResponseByError(ResponseCode.ERROR,"更新地址失败");
    }
    @Override
    public ServerResponse del(Integer shippingId,Integer userId) {
        if (shippingId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }
        int result=shippingMapper.deleteByUserIdSId(shippingId, userId);
        if (result>0){
            return ServerResponse.serverResponseBySuccess();
        }else {
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"删除失败");
        }

    }

    @Override
    public ServerResponse select(Integer shippingId) {
        if (shippingId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"参数必传");
        }
         Shipping shipping=shippingMapper.selectByPrimaryKey(shippingId);
         if (shipping==null){
             return ServerResponse.serverResponseByError(ResponseCode.ERROR,"收货地址不存在");
         }

        return ServerResponse.serverResponseBySuccess(shipping);
    }

    @Override
    public ServerResponse findShippingById(Integer shippingId) {
        if (shippingId==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"shippingId必传");
        }
        Shipping shipping=shippingMapper.selectByPrimaryKey(shippingId);
       // if (shipping==null){
        //    return ServerResponse.serverResponseByError(ResponseCode.ERROR,"收货地址不存在");
        //}
        return ServerResponse.serverResponseBySuccess(shipping);
    }
}
