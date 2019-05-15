package com.wangyuelin.app.socket;

/**
 * Socket消息
 */
public interface Message {

    int QUERY_SENDER = 1000; //询问是否配送
    int NO_SENDER = 1001; //不配送
    int OK_SENDER = 1002;//配送

}
