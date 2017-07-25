package com.mmall.weixin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mmall.util.weixin.TokenThread;

/**
 * 初始化servlet
 */
public class InitServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        new Thread(new TokenThread()).start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public InitServlet() {

    }
}
