package com.neuedu.common;

public enum PaymentEnum {
    ONLINE(1,"线上支付"),
    PAYMENT_OFFLINE(2,"货到付款"),

    ;
    private  int  code;
    private String desc;
    private PaymentEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }
    public  static  PaymentEnum codeOf(Integer code){
        for(PaymentEnum paymentEnum: values()){
            if(code==paymentEnum.getCode()){
                return  paymentEnum;
            }
        }
        return  null;

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
