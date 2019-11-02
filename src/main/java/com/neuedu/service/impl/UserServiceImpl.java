package com.neuedu.service.impl;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserMapper;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl  implements IUserService {
    @Autowired
    private     UserMapper userMapper;

    @Override
    public ServerResponse register(User user) {
        //1.参数的校验
        if (user==null){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
        }

        //2.判断用户名是否存在
        int rs1= userMapper.isexistsusername(user.getUsername());
        if (rs1>0){ //用户名已存在
            return ServerResponse.serverResponseByError(ResponseCode.USERNAME_EXISTS,"用户名已存在");
        }

        //3.判断邮箱是否存在
        int rs2= userMapper.isexistsemail(user.getEmail());
        if (rs2>0){ //邮箱已存在
            return ServerResponse.serverResponseByError(ResponseCode.EMAIL_EXISTS,"邮箱已存在");
        }

        //4.密码加密，设置用户角色
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        //设置角色为普通用户
        user.setRole(RoleEnum.ROLE_USER.getRole());
        //5.注册
        int insertrs = userMapper.insert(user);
        if (insertrs<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"注册失败");

        }
        //6.返回
        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse login(String username, String password,int type) {
        //1.参数的非空校验
        if (username == null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"用户名不能为空");

        }
        if (password == null||password.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"密码不能为空");

        }
        //2.判断用户名是否存在
        int rs=userMapper.isexistsusername(username);
        if (rs<=0){     //用户名不存在
            return ServerResponse.serverResponseByError(ResponseCode.USERNAME_EXISTS,"用户名不存在");
        }
        //3.密码加密
        password = MD5Utils.getMD5Code(password);
        //4.登录
        User user=userMapper.findUserByUsernameAndPassword(username,password);

        if (user==null){    //密码错误
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"密码错误");
        }
        if (type==0){
            if (user.getRole()==RoleEnum.ROLE_USER.getRole()){
                return ServerResponse.serverResponseByError(ResponseCode.ERROR,"没有权限登录");
            }
        }
        return ServerResponse.serverResponseBySuccess(user);
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        //1.参数的非空校验
        if (username == null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"用户名不能为空");
        }
        //2.根据用户名查询问题
        String question=userMapper.forget_get_question(username);
        if (question==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"没有查询到问题");

        }
        //3.返回结果


        return ServerResponse.serverResponseBySuccess(question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //1.参数的非空校验
        if (username == null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"用户名不能为空");
        }
        if (question == null||question.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"问题不能为空");
        }
        if (answer == null||answer.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"答案不能为空");
        }
        //2.根据用户名查询问题
        int result=userMapper.forget_check_answer(username,question,answer);
        if (result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"答案错误");

        }
        //3.返回结果
        //生成Token
        String token=UUID.randomUUID().toString();
        TokenCache.set("username:"+username, token);

        return ServerResponse.serverResponseBySuccess(token);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String newpassword, String forgettoken) {
        //1.参数的非空校验
        if (username == null||username.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"用户名不能为空");
        }
        if (newpassword == null||newpassword.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"新密码不能为空");
        }
        if (forgettoken == null||forgettoken.equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"token不能为空");
        }
        //是否修改自己的账号
        String token = TokenCache.get("username:"+username);
        if (token == null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"修改的不是自己的密码或token过期");

        }
        if (!token.equals(forgettoken)){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"无效的token");
        }
        int result=userMapper.forget_reset_password(username, MD5Utils.getMD5Code(newpassword));
        if (result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"密码修改失败");
        }
        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse update_information(User user) {
        //1.参数的非空校验
        if (user == null){
            return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
        }
         int result=userMapper.UpdateUserByActivate(user);
        if (result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"修改失败");

        }


        return ServerResponse.serverResponseBySuccess();
    }

}
