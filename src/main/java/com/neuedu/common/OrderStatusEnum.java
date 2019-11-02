package com.neuedu.common;

public enum OrderStatusEnum {
    ORDER_CANCEL(0,"已取消"),
    ORDER_UN_PAY(10,"未付款"),
    ORDER_PAYED(20,"已付款"),
    ORDER_SEND(40,"已发货"),
    ORDER_SUCCESS(50,"交易成功"),
    ORDER_CLOSED(60,"交易关闭")
    ;
    private  int  status;
    private String desc;
    private OrderStatusEnum(int status,String desc){
        this.status=status;
        this.desc=desc;
    }
    public  static  OrderStatusEnum codeOf(Integer status){

        for(OrderStatusEnum orderStatusEnum: values()){

            if(status==orderStatusEnum.getStatus()){

                return  orderStatusEnum;

            }

        }

        return  null;

    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
