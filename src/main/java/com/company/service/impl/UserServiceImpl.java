package com.company.service.impl;


import com.company.commons.Const;
import com.company.commons.Result;
import com.company.commons.ServerRes;

import com.company.commons.TokenCache;
import com.company.dao.idao.UserMapper;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import com.company.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {
   @Autowired
   UserMapper userMapper;

    @Override
    public ServerRes<User> login(String username, String password) {
       int resultCount  = userMapper.checkUsername(username);
       if(resultCount == 0){
           return ServerRes.error(Result.USERNAME_IS_NOT_EXIST);
       }
       // TODO:对密码进行MD5处理
        User user = userMapper.login(username, MD5Util.MD5EncodeUtf8(password));
       if(user == null){
           return ServerRes.error(Result.PASSWORD_IS_WRONG);
       }
       user.setPassword(StringUtils.EMPTY);
        return ServerRes.success(Result.LOGIN_SUCCESS,user);
    }

    @Override
    public ServerRes checkValid(String validName, String type) {
        if(StringUtils.isNotBlank(type)){
            if(type.equals(Const.ValidType.USERNAME)){
                int uflag = userMapper.checkUsername(validName);
                if(uflag >0 ){
                    return ServerRes.error(Result.USER_EXIST);
                }
            }else if (type.equals(Const.ValidType.EMAIL)){
                 int eflag = userMapper.checkEmail(validName);
                 if(eflag>0){
                     return ServerRes.error(Result.EMAIL_EXIST);
                 }
            }else{
                return ServerRes.error(Result.PARAMTYPE_ERROR);
            }
        }

        return ServerRes.success(Result.CHECK_SUCCESS);
    }

    @Override
    public ServerRes registry(User user) {
        ServerRes uRes = this.checkValid(user.getUsername(),Const.ValidType.USERNAME);
        if(uRes.getStatus() == Result.USER_EXIST.getStatus()){
            return uRes;
        }
        ServerRes eRes = this.checkValid(user.getEmail(),Const.ValidType.EMAIL);
        if(eRes.getStatus() == Result.EMAIL_EXIST.getStatus()){
            return eRes;
        }
        user.setRole(Const.Role.ROLE_USER);//设置用户默认权限是普通用户
        String originPassword = user.getPassword();
        user.setPassword(MD5Util.MD5EncodeUtf8(originPassword));
        int regCount = userMapper.insert(user);
        return regCount>0?ServerRes.success(Result.REGISTER_SUCCESS):ServerRes.error(Result.REGISTER_FAILED);
    }

    @Override
    public ServerRes<String> getQuestionByUsername(String username) {
        //检测用户名是否存在
        ServerRes uRes = this.checkValid(username,Const.ValidType.USERNAME);
        if(uRes.getStatus() == Result.CHECK_SUCCESS.getStatus()){
            return ServerRes.success(Result.USER_EXIST);
        }
        //检验问题是否为空
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerRes.success(Result.GET_QUESTION_SUCCESS ,question);
        }
        return ServerRes.error(Result.GET_QUESTION_ERROR);
    }

    @Override
    public ServerRes<String> checkAnser(String username, String question, String anser) {
        int resultCount = userMapper.checkAnser(username,question,anser);
        if(resultCount > 0){
            //此时，用户提交的答案是正确的，首先通过UUID算法生成唯一的token值
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,forgetToken);
            return ServerRes.success(Result.GET_TOKEN_SUCCESS,forgetToken);
        }
        return ServerRes.error(Result.GET_TOKEN_ERROR);
    }

    @Override
    public ServerRes<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //1-检验令牌是否存在
        if(StringUtils.isBlank(forgetToken)){
            return ServerRes.error(Result.TOKEN_GET_ERROR);
        }
        //2- 检验用户名是否为空，如果username不存在，直接返回错误信息，否则token_就可以获得gorgetToken，存在安全隐患
        ServerRes uRes = this.checkValid(username,Const.ValidType.USERNAME);
        if(uRes.getStatus() == Result.CHECK_SUCCESS.getStatus()){
            return ServerRes.success(Result.USER_EXIST);
        }
        //3- 从guava缓存中获取Token令牌进行非空校检
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if(StringUtils.isBlank(token)){
            ServerRes.error(Result.TOKEN_TIMEOUT_ERROR);
        }
        //4- 对比前端传来的token与缓存中的token值是否一致，应用StringUtils进行equals比较，增强代码健壮性和安全性
        if(StringUtils.equals(token,forgetToken)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
            if(rowCount > 0 ){
                return ServerRes.success(Result.UPDATE_PASSWORD_SUCCESS);
            }
        }else {
            ServerRes.error(Result.TOKEN_ISNOT_EXIST);
        }
        return ServerRes.error(Result.UPDATE_PASSWORD_ERROR);
    }

    @Override
    public ServerRes<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //验证密码
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerRes.error(Result.PASSWORD_ISPUT_ERROR);
        }
        //更改密码
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount >0){
            return ServerRes.success(Result.PASSWORD_UPDATE_SUCCESS);
        }
        return ServerRes.error(Result.PASSWORD_UPDATE_ERROR);
    }

    @Override
    public ServerRes<User> updateInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return ServerRes.error(Result.EMAIL_IS_ERROR);
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnser(user.getAnser());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return ServerRes.success(Result.UPDATE_USER_INFO_SUCCESS,updateUser);
        }
        return ServerRes.error(Result.UPDATE_USER_INFO_ERROR);
    }

    @Override
    public ServerRes<User> getInformation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return ServerRes.error(Result.NOT_FOUND_USER_ERROR);
        }
        //重置密码
        user.setPassword(StringUtils.EMPTY);
        return ServerRes.success(Result.RESULT_SUCCESS,user);
    }
}
