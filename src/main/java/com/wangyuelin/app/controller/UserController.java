package com.wangyuelin.app.controller;

import com.wangyuelin.app.bean.Code;
import com.wangyuelin.app.bean.Resp;
import com.wangyuelin.app.bean.User;
import com.wangyuelin.app.message.CodeCache;
import com.wangyuelin.app.message.MessageSend;
import com.wangyuelin.app.service.itf.IUser;
import com.wangyuelin.app.utils.Constant;
import com.wangyuelin.app.utils.TextUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUser iUser;

//    @Autowired
//    private ITest test;
//
//
//    @RequestMapping("/getOneUser")
//    @ResponseBody
//    public Usert getOneUser(){
//        logger.info("getOneUser");
//        return test.getUser();
//    }
//
//
//    @RequestMapping("/getAll")
//    @ResponseBody
//    public List<Usert> getAll(){
//        logger.info("getOneUser");
//        return test.getAll();
//    }

    @RequestMapping("/login")
    @ResponseBody
    public Resp login(@Param("password") String password, @Param("userName") String userName) {
        return new Resp(Constant.Code.RESP_SUCCESS, Constant.MSG.SUCCESS, "登陆成功");
    }

    @RequestMapping("/send")
    @ResponseBody
    public Resp sendCode(@Param("phone") String phone) {

        Code code = CodeCache.getCode(phone);
        boolean success = MessageSend.send(phone, MessageSend.getMessage(code.getCode()));

        String msg = "";
        int respCode = -1;
        if (success) {
            msg = "短信已发送到您手机";
            respCode = Constant.Code.RESP_SUCCESS;


        } else {
            msg = "发送短信失败";
            respCode = Constant.Code.RESP_ERROR;
        }

        return new Resp(respCode, msg, "");
    }


    @RequestMapping("/register")
    @ResponseBody
    public Resp register(@Param("phone") String phone, @Param("password") String password, @Param("code") String code) {


        //校验参数
        Resp resp = null;
        String errorMsg = null;
        if (TextUtil.isEmpty(phone)) {
            errorMsg = "手机号不能为空";
        }
        if (TextUtil.isEmpty(password)) {
            errorMsg = "密码不能为空";
        }
        if (TextUtil.isEmpty(code)) {
            errorMsg = "验证码不能为空";
        }

        if (!TextUtil.isEmpty(errorMsg)) {
            resp = new Resp(Constant.Code.RESP_ERROR, errorMsg, "");
            return resp;
        }
        //检查验证码是否正确
//        if (!CodeCache.validate(phone, code)) {
//            errorMsg = "输入验证码不正确";
//            resp = new Resp(Constant.Code.RESP_ERROR, errorMsg, "");
//            return resp;
//
//        }


        //先查询手机号是否已被注册
        User user = iUser.getUser(phone);
        if (user != null) {
            errorMsg = "手机号已被注册";
            resp = new Resp(Constant.Code.RESP_REGISTERED, errorMsg, "");
            return resp;
        }

        //将数据写到数据库
        User saveUser = new User();
        saveUser.setName(phone);
        saveUser.setPhone(phone);
        saveUser.setPassword(password);

        iUser.save(saveUser);

        resp = new Resp(Constant.Code.RESP_SUCCESS, "注册成功", "");
        return resp;

    }

}
