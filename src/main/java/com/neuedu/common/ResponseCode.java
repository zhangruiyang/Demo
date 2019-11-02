package com.neuedu.common;
/**
 * 维护状态码
 * */
public class ResponseCode {

    //成功的状态码

    public static final int SUCESS=0;

    //失败的通用状态码
    public static final int ERROR=100;

    /**参数不能为空*/
    public static final int PARAM_NOT_NULL=1;
    /**用户名已存在*/
    public static final int USERNAME_EXISTS=2;
    /**邮箱已存在*/
    public static final int EMAIL_EXISTS=3;
    /**未登录*/
    public static final int MOT_LOGIN=99;

}
