package com.mmall.util.weixin;

import com.mmall.pojo.weixin.AccessToken;
import com.mmall.pojo.weixin.JsapiTicket;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 定时获取微信 access_token 和 jsapiTicket 的线程
 */
public class TokenThread implements Runnable {
    // 第三方用户唯一凭证
    public static String appid = "wxf1a816d98afbb951";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "78cf52fda587e9c759cce185eb24d9ff";
    public static AccessToken accessToken = null;
    public static JsapiTicket jsapiTicket = null;

    public void run() {
        while (true) {
            try {
                TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
                if (null != TokenThread.accessToken) {
                    System.out.println("获取access_token成功，有效时长"+ TokenThread.accessToken.getExpiresIn()+"秒 token:"+ TokenThread.accessToken.getToken());
                    try{
                        TokenThread.jsapiTicket = WeixinUtil.getJsapiTicket(TokenThread.accessToken.getToken());
                        if(TokenThread.jsapiTicket!=null){
                            System.out.println("获取jsapiTicket成功，有效时长"+ TokenThread.jsapiTicket.getExpiresIn()+"秒 ticket:"+ TokenThread.jsapiTicket.getTicket());
                        }
                    }catch(Exception e){
                        // 如果jsapiTicket为null，60秒后再获取
                        Thread.sleep(60 * 1000);
                    }
                    // 休眠7000秒
                    Thread.sleep((TokenThread.accessToken.getExpiresIn() - 200) * 1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    System.out.println(e1);
                }
                System.out.println( e);
            }
        }
    }
}
