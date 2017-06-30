package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-6-28.
 */
public interface IUserService {
    ServerResponse<String> sendVerificationCode(String phoneNumber,String verificationCode);
    ServerResponse<String> register(User user,String verificationCode,HttpSession session);
    ServerResponse<User> login(String phone,String password);
    ServerResponse<String> sendResetPasswordVerificationCode(String phone, String verificationCode);
    ServerResponse<String> resetPassword(String phone,String password,String verificationCode,HttpSession session);

    ServerResponse<String> sendcode(String phoneNumber,String verificationCode);
}
