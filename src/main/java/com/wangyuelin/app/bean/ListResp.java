package com.wangyuelin.app.bean;

import java.util.List;

public class ListResp {
    private boolean hasMore;
    private List<SendTaskBean> tasks;

    public boolean isHasMore() {
        return hasMore;
    }

    public List<SendTaskBean> getTasks() {
        return tasks;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setTasks(List<SendTaskBean> tasks) {
        this.tasks = tasks;
    }
}
