package com.wangyuelin.app.service;

import com.wangyuelin.app.bean.SendTaskBean;
import com.wangyuelin.app.mapper.TaskMapper;
import com.wangyuelin.app.service.itf.ITask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITask {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<SendTaskBean> getTasks(int status, int currPage, int pageSize) {
        return taskMapper.getTasks(status, (currPage - 1) * pageSize, pageSize);
    }

    @Override
    public void update(SendTaskBean task) {
        taskMapper.update(task);
    }

    @Override
    public SendTaskBean getTaskById(long id) {
        return taskMapper.getTaskById(id);
    }

    @Override
    public void addTask(SendTaskBean taskBean) {
        taskMapper.addTask(taskBean);
    }
}
