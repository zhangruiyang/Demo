package com.neuedu.vo;

import lombok.Data;

@Data
public class payVO {
    private Long orderNo;
    private String qrcode;
    payVO(){}

    public payVO(Long orderNo, String qrcode) {
        this.orderNo = orderNo;
        this.qrcode = qrcode;
    }
}
