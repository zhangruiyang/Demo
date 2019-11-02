package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IShippingService {
    public ServerResponse add(Shipping shipping);
    public ServerResponse update(Shipping shipping);
    public ServerResponse del(Integer shippingId,Integer userId);
    public ServerResponse select(Integer shippingId);
    public ServerResponse findShippingById(Integer shippingId);
}
