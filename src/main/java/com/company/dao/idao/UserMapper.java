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

}