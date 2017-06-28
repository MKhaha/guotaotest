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
}
