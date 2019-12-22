package com.company.avtion;

import com.company.commons.Const;
import com.company.commons.Result;
import com.company.commons.ServerRes;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Muser/")
public class UserManagerAction {
    @Autowired
    UserService userService;

        @RequestMapping(value = "login.do",method = RequestMethod.POST)
        @ResponseBody
        public ServerRes<User> login(@RequestParam String username,
                                     @RequestParam String password,
                                     HttpSession session){
            ServerRes<User> result = userService.login(username,password);
            if(result.getStatus() == Result.LOGIN_SUCCESS .getStatus()){
                User user = result.getData();
                if(user.getRole() == Const.Role.ROLE_ADMIN){
                    session.setAttribute(Const.CURRENT_USER,user);
                }else{
                    return ServerRes.error(Result.USER_IS_NOT_MANAGER_ROLE);
                }
            }
            return result;
        }

    }
