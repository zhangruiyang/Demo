package com.neuedu.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车实体类
 * */
@Data
public class CartVO {
    //购物车信息集合
    private List<CartProductVO> cartProductVOList;
    //是否全选
    private boolean isallchecked;
    //总价格
    private BigDecimal carttotalprice;
}
