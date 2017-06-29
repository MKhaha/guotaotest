package com.mmall.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.mmall.common.AliyunSms;
import com.mmall.common.ServerResponse;
import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.dao.UserMapper;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-6-28.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private AliyunSms aliyunSmsService;
    @Autowired
    private UserMapper userMapper;


    //登陆
    @Override
    public ServerResponse<User> login(String phone,String password){
        int resultCount = userMapper.checkPhone(phone);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户不存在！");
        }
        password = MD5Util.MD5EncodeUtf8(password);
        User u = userMapper.seleteLogin(phone,password);
        if(u == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        u.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",u);
    }

    //注册时发送验证码
    @Override
    public ServerResponse<String> sendVerificationCode(String phone, String verificationCode) {
        int resultCount = userMapper.checkPhone(phone);
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("手机号已被注册");
        } else {
            return this.sendcode(phone, verificationCode);
        }
    }
    //注册
    @Override
    public ServerResponse<String> register(User user, String verificationCode, HttpSession session){
        String Code = (String)session.getAttribute(user.getPhone());
        if(org.apache.commons.lang3.StringUtils.isBlank(verificationCode)){
            return ServerResponse.createByErrorMessage("请输入验证码");
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(Code)){
            if(verificationCode.equals(Code)){
                int resultCount = userMapper.checkPhone(user.getPhone());
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("手机号已被注册");
                }
                else {
                    user.setRole(Const.Role.ROLE_CUSTOMER);
                    user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
                    int resultCount1 = userMapper.insert(user);
                    if (resultCount1 > 0) {
                        return ServerResponse.createBySuccessMessage("注册成功");
                    } else
                        return ServerResponse.createByErrorMessage("注册失败");
                }
            }
            else{
                return ServerResponse.createByErrorMessage("验证码错误");
            }
        }
        else
            return ServerResponse.createByErrorMessage("请获取正确验证码");
    }
    //重置时发送验证码
    @Override
    public ServerResponse<String> sendResetPasswordVerificationCode(String phone, String verificationCode) {
        int resultCount = userMapper.checkPhone(phone);
        if (resultCount > 0) {
            return this.sendcode(phone, verificationCode);
        } else {
            return ServerResponse.createByErrorMessage("手机号未注册");
        }
    }
    //重置密码
    @Override
    public ServerResponse<String> resetPassword(String phone,String password,String verificationCode,HttpSession session){
        String Code = (String)session.getAttribute(phone);
        if(org.apache.commons.lang3.StringUtils.isBlank(verificationCode)){
            return ServerResponse.createByErrorMessage("请输入验证码");
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(Code)){
            if(verificationCode.equals(Code)){
                int resultCount = userMapper.checkPhone(phone);
                if(resultCount>0){
                    String mdPassword = MD5Util.MD5EncodeUtf8(password);
                    int resultCount1 = userMapper.updateByRestCode(phone,mdPassword);
                    if (resultCount1 > 0) {
                        return ServerResponse.createBySuccessMessage("重置密码成功");
                    } else
                        return ServerResponse.createByErrorMessage("重置密码失败");
                }
                else {
                    return ServerResponse.createByErrorMessage("手机号未注册");
                }
            }
            else{
                return ServerResponse.createByErrorMessage("验证码错误");
            }
        }
        else{
            return ServerResponse.createByErrorMessage("请获取正确验证码");
        }
    }

    @Override
    public  ServerResponse<String> sendcode(String phone, String verificationCode) {
        aliyunSmsService.setPhoneNumber(phone);
        aliyunSmsService.setVerificationcode(verificationCode);
        try {
            //发短信
            SendSmsResponse response = aliyunSmsService.sendSms();
            Thread.sleep(200);
            if (response.getCode() != null && response.getCode().equals("OK")) {
                return ServerResponse.createBySuccessMessage("验证码发送成功");
            } else {
                return ServerResponse.createByErrorMessage("验证码发送失败");
            }

        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("客户端故障");
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("其他故障");
        }
    }
}
