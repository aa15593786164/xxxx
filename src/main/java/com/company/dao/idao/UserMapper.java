package com.company.dao.idao;

import com.company.dao.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(@Param("username") String username,@Param("password") String password);

    int checkUsername(String username);

    int checkEmail(String email);

    /**
     * 忘记密码，根据用户名查询提示问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 忘记密码，根据提示问题，验证答案
     * @param username
     * @param question
     * @param anser
     * @return
     */
    int checkAnser(@Param("username") String username, @Param("question") String question,
                   @Param("anser") String anser);

    /**
     * 验证预设问题答案后，对密码进行修改
     * @param username
     * @param passwordNew
     * @return
     */
   int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);

    /**
     *  登录状态下重置密码
     * @param password
     * @param id
     * @return
     */
    int checkPassword(@Param("password") String password,@Param("userId") Integer id);

    /**
     * 修改用户信息，检验email是否已经被其他用户注册
     * @param email
     * @param id
     * @return
     */
    int checkEmailByUserId(@Param("email") String email,@Param("userId") Integer id);
}