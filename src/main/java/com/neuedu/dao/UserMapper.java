package com.neuedu.dao;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);


    //判断用户名是否存在
    int isexistsusername(@Param("username") String username);
    //判断邮箱是否存在
    int isexistsemail(@Param("email") String email);
    //根据用户名和密码查询
    User findUserByUsernameAndPassword(@Param("username")String username,@Param("password")String password);
    /**
     * 根据username获取密保问题
     */

    public String forget_get_question(@Param("username") String username);

    /**
     * 提交答案
     */

    public int forget_check_answer(@Param("username")String username,
                                   @Param("question")String question,
                                   @Param("answer")String answer);

    /**
     * 修改密码
     */

    public int forget_reset_password(@Param("username")String username,
                                     @Param("password")String newpassword);


    /**
     * 修改用户个人信息
     */
    public int UpdateUserByActivate(@Param("user") User user);
}