package com.wangyuelin.app.message;

import com.wangyuelin.app.bean.Code;
import com.wangyuelin.app.utils.TextUtil;

import java.util.concurrent.ConcurrentHashMap;

public class CodeCache {
    private static ConcurrentHashMap<String, Code> codeMap = new ConcurrentHashMap<String, Code>();

    /**
     * 据手机号，获取要发送的验证码
     * @param phone
     * @return
     */
    public static Code getCode(String phone) {
        String codeStr = getRandomCode();
        Code code = new Code(codeStr, System.currentTimeMillis(), phone);
        codeMap.put(phone, code);
        return code;
    }


    /**
     * 获取四位的随机吗
     * @return
     */
    public static String getRandomCode() {
        return String.valueOf((int)((Math.random()*9+1)*1000));
    }

    /**
     * 校验手机号和验证码是否正确
     * @param phone
     * @param codeNumber
     * @return
     */
    public static boolean validate(String phone, String codeNumber) {
        Code code = codeMap.get(phone);
        if (code != null && TextUtil.equals(codeNumber, code.getCode())) {//手机号和验证码正确
            return true;
        }
        return false;
    }


}
