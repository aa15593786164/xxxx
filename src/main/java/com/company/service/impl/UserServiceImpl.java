package com.company.service.impl;


import com.company.commons.Const;
import com.company.commons.Result;
import com.company.commons.ServerRes;

import com.company.dao.idao.UserMapper;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import com.company.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

}
