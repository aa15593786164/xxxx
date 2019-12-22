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

    /**
     * 1.8 登录状态下重置密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    public ServerRes<String> resetPassword(String passwordOld,String passwordNew,User user);

    /**
     * 登录后修改用户信息
     * @param user
     * @return
     */
    public ServerRes<User> updateInformation(User user);

    /**
     * 登录后获取用户全部信息
     * @param id
     * @return
     */
    ServerRes<User> getInformation(Integer id);
}
