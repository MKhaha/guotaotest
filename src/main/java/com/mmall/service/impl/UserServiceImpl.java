package com.mmall.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.mmall.common.AliyunSms;
import com.mmall.common.ServerResponse;
import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.dao.UserMapper;
import com.mmall.util.MD5Util;
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

    @Override
    public ServerResponse<String> sendVerificationCode(String phone, String verificationCode){
        aliyunSmsService.setPhoneNumber(phone);
        aliyunSmsService.setVerificationcode(verificationCode);
        try {
            //发短信
            SendSmsResponse response = aliyunSmsService.sendSms();
            Thread.sleep(200);
            if(response.getCode() != null && response.getCode().equals("OK")) {
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
    @Override
    public ServerResponse<String> register(User user, String verificationCode, HttpSession session){
        String Code = (String)session.getAttribute(user.getPhone());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(Code)){
            if(verificationCode.equals(Code)){
                int resultCount1 = userMapper.checkPhone(user.getPhone());
                if(resultCount1>0){
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
                else{
                    user.setRole(Const.Role.ROLE_CUSTOMER);
                    user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
                    int resultCount2 = userMapper.insert(user);
                    if (resultCount2>0){
                        return ServerResponse.createBySuccessMessage("注册成功");
                    }
                    else
                        return ServerResponse.createBySuccessMessage("注册失败");
                }
            }
            else{
                return ServerResponse.createByErrorMessage("验证码错误");
            }
        }
        else{
            return ServerResponse.createByErrorMessage("请输入验证码");
        }
    }
}
