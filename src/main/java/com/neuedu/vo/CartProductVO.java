package com.neuedu.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class CartProductVO implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;//商品状态
    private BigDecimal productTotalPrice;
    private Integer productStock;//商品库存
    private Integer productChecked;
    private String  limitQuantity;//商品的库存描述信息
}
