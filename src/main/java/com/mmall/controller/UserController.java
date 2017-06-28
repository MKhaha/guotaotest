package com.mmall.controller;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.DigitalVerificationCode;
import com.mmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
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
    public ServerResponse<String> login(){
        return ServerResponse.createByErrorMessage("hello error");
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

    /*
    @RequestMapping(value = "checkValid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String phoneNumber, String verificationCode, HttpSession session){
        if(session == null) {
            return ServerResponse.createByErrorMessage("sessionÎª¿Õ");
        }
        String verificationCodeInSession = (String) session.getAttribute(Const.V_CODE);
        return iUserService.checkValid(phoneNumber, verificationCode, verificationCodeInSession);
    }*/
    //********************************************************************************************************************

}
