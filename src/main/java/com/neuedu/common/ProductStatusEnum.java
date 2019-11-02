package com.neuedu.common;

public enum ProductStatusEnum {
    PRODUCT_ONLINE(1,"在售"),

    PRODUCT_OFFLINE(2,"下架"),

    PRODUCT_DELETE(3,"删除")

    ;
    private int status;
    private String desc;
    ProductStatusEnum(int status,String desc){
        this.status=status;
        this.desc=desc;
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
