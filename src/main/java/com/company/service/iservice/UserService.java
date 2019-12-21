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

    public ServerRes<String> checkAnser(String username,String question,String anser);
}
