package com.wangyuelin.app.utils;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-25 下午3:46
 */
public class Constant {

    public interface RespCode {
        int SUCCESS = 1000;
        int ERROR = 1001;

    }

    public interface MSG {
        String SUCCESS = "数据请求成功";
        String ERROE = "数据请求失败";
    }

    public interface Page{
        String P_404 = "404";
        String ERROR = "error";
    }


    public interface Urls{
        String JINGYAN = "jingyan.baidu.com";//百度经验
        String BAIJIAHAO = "http://baijiahao.baidu.com";//百度百家号
    }

    public interface Code {
        int RESP_SUCCESS = 0;//网络请求成功
        int RESP_ERROR = -1;//网络请求失败
        int RESP_REGISTERED = 10001;//手机号已被注册
        int RESP_UNREGISTER = 10002;//还未注册
    }

    public interface Message {
        String RESP_SUCCESS = "网络请求成功";
    }


}