package com.wsy.mybatis_plus_demo.utils;

import javax.servlet.http.HttpSession;
import com.google.code.kaptcha.Constants;
/**
 * 图片验证码对比
 */
public class CodeUtil {
    public static boolean checkVerifyCode(String verifyCodeActual, HttpSession session){
        String verifyCodeExpected = (String) session.getAttribute((Constants.KAPTCHA_SESSION_KEY));
        if(verifyCodeActual ==null || !verifyCodeActual.equalsIgnoreCase(verifyCodeActual)){
            return false;
        }
        return true;
    }
}
