package com.wsy.mybatis_plus_demo.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.extern.slf4j.Slf4j;


public class KeyGenerator {
    public static void main(String[] args)throws Exception {
        String password = "root123456";
        String[] arr = ConfigTools.genKeyPair(512);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
    }
}
