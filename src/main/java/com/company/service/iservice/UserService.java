package com.company.service.iservice;

import com.company.commons.ServerRes;
import com.company.dao.pojo.User;


public interface UserService {
    public ServerRes<User> login(String username, String password);

    /**
     * 检查用户名或邮箱是否已存在，不存在才能注册，会返回校检成功
     * @param validName
     * @param type
     * @return
     */

    public ServerRes checkValid(String validName,String type);

    /**
     * 实现注册功能
     * @param user
     * @return
     */
    public ServerRes registry(User user);

    /**
     * 根据用户问题获得更改密码的问题
     * @param username
     * @return
     */
    public ServerRes<String> getQuestionByUsername(String username);

    /**
     * 验证用户预留答案是否正确
     * @param username
     * @param question
     * @param anser
     * @return
     */
    public ServerRes<String> checkAnser(String username,String question,String anser);

    /**
     * 正确回答预设问题之后，根据令牌对密码进行重置
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    public ServerRes<String> forgetResetPassword(String username,String passwordNew,String forgetToken);
}
