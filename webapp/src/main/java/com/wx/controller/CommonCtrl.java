package com.wx.controller;

import com.wx.common.Config;
import com.wx.common.message.BaseResponse;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by jie_r on 2016/11/15.
 */
@RestController
@RequestMapping("/wx")
public class CommonCtrl {

    @RequestMapping(method = RequestMethod.GET)
    public <E> E login(String signature, String timestamp, String nonce, String echostr, HttpSession session) throws Exception {
        // 校验成功返回  echostr，成功成为开发者；否则返回error，接入失败
        if (validSign(signature, Config.TOKEN, timestamp, nonce)) {
            return (E) echostr;
        } else {
            return (E) "Token校验失败，但也欢迎拜访这个网页!";
        }
    }

    //验证sign
    public static boolean validSign(String signature, String tocken, String timestamp, String nonce) {

        String[] paramArr = new String[]{tocken, timestamp, nonce};
        //对token、timestamp、nonce 进行字典排序，并拼接成字符串
        Arrays.sort(paramArr);
        StringBuilder sb = new StringBuilder(paramArr[0]);
        sb.append(paramArr[1]).append(paramArr[2]);
        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(sb.toString().getBytes());// 对接后的字符串进行sha1加密
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将sha1加密后的字符串与  signature 进行比较
        return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
    }

    private static String byteToStr(byte[] byteArray) {
        String rst = "";
        for (int i = 0; i < byteArray.length; i++) {
            rst += byteToHex(byteArray[i]);
        }
        return rst;
    }

    private static String byteToHex(byte b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
