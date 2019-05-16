package com.wangyuelin.app.mapper;

import com.wangyuelin.app.bean.SendTaskBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {

    List<SendTaskBean> getTasks(@Param("status") int status, @Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    void update(SendTaskBean task);//更新一个任务

    SendTaskBean getTaskById(long id);

    void addTask(SendTaskBean taskBean);//新添加一个任务
}
