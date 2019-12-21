package com.company.service.impl;

import com.company.commons.Const;
import com.company.commons.ServerRes;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import com.company.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    public void login() {
        ServerRes<User> res1  = userService.login("wangmeili","tiger");
        System.err.println("***********************"+res1);
        ServerRes<User> res2  = userService.login("wangmeili11111","tiger");
        System.err.println("***********************"+res2);
        ServerRes<User> res3  = userService.login("wangmeili","tiger11");
        System.err.println("***********************"+res3);

    }
    @Test
    public void MD5EncodeUtf8(){
        String originPassword = "123456";
        System.err.println(MD5Util.MD5EncodeUtf8(originPassword));
    }

    @Test
    public void checkValid() {
        ServerRes sr11 = userService.checkValid("xxxx", Const.ValidType.USERNAME);
        System.err.println(sr11);
        ServerRes sr12 = userService.checkValid("xxxx11", Const.ValidType.USERNAME);
        System.err.println(sr12);
        ServerRes sr13 = userService.checkValid("4432122334@qq.com", Const.ValidType.EMAIL);
        System.err.println(sr13);
        ServerRes sr14 = userService.checkValid("4334@qq.com", Const.ValidType.EMAIL);
        System.err.println(sr14);
        ServerRes sr15 = userService.checkValid("xx", "type");
        System.err.println(sr15);


    }

    @Test
    public void registry() {
        User user =new User("qian","123456","4432124@qq.com","123345234","Q","A");
        ServerRes sr = userService.registry(user);
        System.err.println("**********************88"+sr);
    }

    @Test
    public void getQuestionByUsername() {
        ServerRes uRes1 = userService.getQuestionByUsername("zdfsg");
        ServerRes uRes2 = userService.getQuestionByUsername("wangmeili");
        ServerRes uRes3 = userService.getQuestionByUsername("sun");
        System.err.println("**************"+uRes1);
        System.err.println(uRes2);
        System.err.println("&&&&&&&&&&&&&&&&&&&&&&&"+uRes3);
    }

    @Test
    public void checkAnser() {
        ServerRes uRes1 = userService.checkAnser("qian","Q","A");
        ServerRes uRes2 = userService.checkAnser("qian","Q","c");
        System.err.println(uRes1);
        System.err.println(uRes2);
    }

    @Test
    public void forgetResetPassword() {
        ServerRes uRes1 = userService.forgetResetPassword("qian","112244","dc7804de-524b-414a-9aee-f86308723914");
        System.err.println(uRes1);
    }
}