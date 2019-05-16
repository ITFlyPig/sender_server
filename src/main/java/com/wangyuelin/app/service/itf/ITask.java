package com.wangyuelin.app.service.itf;

import com.wangyuelin.app.bean.SendTaskBean;

import java.util.List;

public interface ITask {

    List<SendTaskBean> getTasks(int status, int currPage, int pageSize);

    void update(SendTaskBean task);//更新一个任务

    SendTaskBean getTaskById(long id);

    void addTask(SendTaskBean taskBean);
}
