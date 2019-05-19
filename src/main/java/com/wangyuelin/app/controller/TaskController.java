package com.wangyuelin.app.controller;

import com.wangyuelin.app.bean.ListResp;
import com.wangyuelin.app.bean.Resp;
import com.wangyuelin.app.bean.SendTaskBean;
import com.wangyuelin.app.bean.User;
import com.wangyuelin.app.service.itf.ITask;
import com.wangyuelin.app.service.itf.IUser;
import com.wangyuelin.app.token.TokenUtil;
import com.wangyuelin.app.utils.Constant;
import com.wangyuelin.app.utils.TaskStatus;
import com.wangyuelin.app.utils.TextUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("task")
public class TaskController {
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ITask iTask;


    @RequestMapping("/getTasks")
    @ResponseBody
    public Resp getTasks(int status, int curPage, int pageSize) {

        List<SendTaskBean> taskBeans = iTask.getTasks(status, curPage, pageSize);
        boolean hasMore = true;
        if (taskBeans == null || taskBeans.size() < pageSize) {//没有更多了
            hasMore = false;
        }
        ListResp listResp = new ListResp();
        listResp.setHasMore(hasMore);
        listResp.setTasks(taskBeans.size() == 0 ? null : taskBeans);

        return new Resp(Constant.RespCode.SUCCESS, Constant.MSG.SUCCESS, listResp);
    }

    @RequestMapping("/addTask")
    @ResponseBody
    public Resp addTask(String name, String phone) {
        String errorMsg = null;
        if (TextUtil.isEmpty(name)) {
            errorMsg = "任务名称不能为空";
        } else if (TextUtil.isEmpty(phone)) {
            errorMsg = "接受手机号不能为空";
        }
        if (!TextUtil.isEmpty(errorMsg)) {
            return new Resp(Constant.RespCode.ERROR, errorMsg, null);
        }

        SendTaskBean taskBean = new SendTaskBean();
        taskBean.setName(name);
        taskBean.setRecvPhone(phone);
        taskBean.setStatus(TaskStatus.WAIT_SEND);
        taskBean.setTime(System.currentTimeMillis());

        iTask.addTask(taskBean);

        return new Resp(Constant.RespCode.SUCCESS, "任务添加成功", null);

    }

    @RequestMapping("/updateTask")
    @ResponseBody
    public Resp updateTask(Long id, Integer status, String selTime, String selLocation) {
        SendTaskBean taskBean = iTask.getTaskById(id);
        if (taskBean == null) {
            return new Resp(Constant.RespCode.ERROR, "您要更新的任务不存在", null);
        }

        if (!TextUtil.isEmpty(selTime)) {
            taskBean.setUserSelTime(selTime);
        }
        if (!TextUtil.isEmpty(selLocation)) {
            taskBean.setUserSelLocation(selLocation);
        }
        if (status != null && ( status == TaskStatus.WAIT_SEND || status == TaskStatus.SENDING || status == TaskStatus.SENDED)) {
            taskBean.setStatus(status);
        }

        iTask.update(taskBean);

        return new Resp(Constant.RespCode.SUCCESS, "更新任务成功", null);

    }

}
