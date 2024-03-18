package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    @JsonProperty("taskId")
    private int taskId;

    @JsonProperty("taskName")
    private String taskName;

    @JsonProperty("taskDetail")
    private String taskDetail;

    @JsonProperty("status")
    private String status;

    @JsonIgnore
    private Task next;

    @JsonIgnore
    private Task previous;

    public Task(int taskId, String taskName, String taskDetail, String status, Task next, Task previous) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDetail = taskDetail;
        this.status = status;
        this.next = next;
        this.previous = previous;
    }

    // Empty Constructor
    public Task() {

    }

    @Override
    public String toString() {
        return "taskId:" + taskId + ", taskName:" + taskName + ", taskDetail:" + taskDetail + ", status" + status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task getNext() {
        return next;
    }

    public void setNext(Task next) {
        this.next = next;
    }

    public Task getPrevious() {
        return previous;
    }

    public void setPrevious(Task previous) {
        this.previous = previous;
    }
}