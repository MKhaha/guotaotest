package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.DigitalVerificationCode;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String phone,String password,HttpSession session){
        ServerResponse<User> response = iUserService.login(phone,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        if(session.getAttribute(Const.CURRENT_USER)!=null){
            return ServerResponse.createByErrorMessage("服务异常");
        }
        return ServerResponse.createBySuccessMessage("注销成功");
    }

    //********************************************************************************************************************
    @RequestMapping(value = "sendVerificationCode.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> sendVerificationCode(String phone, HttpSession session) {
        String verificationCode = DigitalVerificationCode.getVerificationCode();
        ServerResponse<String> response = iUserService.sendVerificationCode(phone, verificationCode);
        if (response.isSuccess()) {
            session.setAttribute(phone, verificationCode);
        }
        return response;
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user, String verificationCode, HttpSession session){
        return iUserService.register(user, verificationCode, session);
    }
    //********************************************************************************************************************
    @RequestMapping(value = "sendResetVerificationCode.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> sendResetPasswordVerificationCode(String phone, HttpSession session) {
        String verificationCode = DigitalVerificationCode.getVerificationCode();
        ServerResponse<String> response = iUserService.sendResetPasswordVerificationCode(phone, verificationCode);
        if (response.isSuccess()) {
            session.setAttribute(phone, verificationCode);
        }
        return response;
    }
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(String phone,String password,String verificationCode,HttpSession session){
        return iUserService.resetPassword(phone,password,verificationCode,session);
    }
}
