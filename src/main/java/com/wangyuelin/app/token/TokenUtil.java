package com.wangyuelin.app.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wangyuelin.app.bean.User;

public class TokenUtil {

    /**
     * 生成Token
     * @param user
     * @return
     */
    public static String getToken(User user) {
        String token= JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
