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
@RequestMapping("iuser")
public class UserAction {
    @Autowired
    UserService userService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)

    public @ResponseBody  ServerRes<User> login(
           @RequestParam  String username,
           @RequestParam (value = "password",required = true)String password,
           HttpSession session){
        ServerRes<User> result = userService.login(username,password);
        if(result.getStatus() == Result.LOGIN_SUCCESS.getStatus()){
            session.setAttribute(Const.CURRENT_USER,result.getData());
        }
        return result;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerRes logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerRes.success(Result.LOGOUT_SUCCESS);
    }
    //1.3用户注册
    @RequestMapping(value = "registry.do",method = RequestMethod.POST)
    public @ResponseBody ServerRes registUser(User user){
        return userService.registry(user);
    }

    //1.4 获取用户信息
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerRes<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return  ServerRes.success(Result.GET_USER_INFO_SUCCESS , user);
        }
        return ServerRes.error(Result.GET_USER_INFO_ERROR);
    }
    //1.5 忘记密码，获取用户提示信息
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerRes<String> forgetPasswordGetQuestion(String username){
        return userService.getQuestionByUsername(username);
    }
}
