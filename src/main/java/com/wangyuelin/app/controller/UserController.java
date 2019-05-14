package com.wangyuelin.app.controller;

import com.wangyuelin.app.bean.Resp;
import com.wangyuelin.app.utils.Constant;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

//    @Autowired
//    private ITest test;
//
//
//    @RequestMapping("/getOneUser")
//    @ResponseBody
//    public User getOneUser(){
//        logger.info("getOneUser");
//        return test.getUser();
//    }
//
//
//    @RequestMapping("/getAll")
//    @ResponseBody
//    public List<User> getAll(){
//        logger.info("getOneUser");
//        return test.getAll();
//    }

    @RequestMapping("/login")
    @ResponseBody
    public Resp login(@Param("password") String password,@Param("userName") String userName) {
        return new Resp(Constant.Code.RESP_SUCCESS, Constant.MSG.SUCCESS, "登陆成功");
    }

}
