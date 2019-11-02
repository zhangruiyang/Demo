package com.neuedu.controller.backend;

import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.service.IUserService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage")
public class UserManageController {
    @Autowired
    IUserService userService;
    /*登录接口*/
    @RequestMapping(value = "/login.do/{username}/{password}")
    public ServerResponse login(@PathVariable("username")String username,
                                @PathVariable("password")String password,
                                HttpSession session){
        ServerResponse serverResponse=userService.login(username,password, RoleEnum.ROLE_ADMIN.getRole());
        System.out.println(serverResponse);
        //判断是否登录成功
        if (serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

}
