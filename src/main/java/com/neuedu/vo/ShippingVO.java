package com.neuedu.vo;

import lombok.Data;

@Data
public class ShippingVO {
    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;
    private String receiverZip;
}
