package com.mmall.controller;

import com.mmall.util.weixin.SignUtil;
import com.mmall.util.weixin.TokenThread;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-20.
 */
@Controller
@RequestMapping("/weixin/")
public class WeixinController {


    @RequestMapping(value = "getVerification.do",method = RequestMethod.GET)
    @ResponseBody
    public Map getVerCode(HttpServletRequest request) throws Exception {
        Map map = new HashMap();
        String url = request.getHeader("referer");
        String nonce = SignUtil.create_nonce_str();
        String timestamp = SignUtil.create_timestamp();
        String ticket = TokenThread.jsapiTicket.getTicket();
        String signature = SignUtil.getSignature(ticket,nonce,timestamp,url);
        map.put("signature", signature);
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        return map;
    }
}
