/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class TaskType implements Serializable {

    private Integer taskTypeId;
    private String taskTypeDesc;

    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(int taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTaskTypeDesc() {
        return taskTypeDesc;
    }

    public void setTaskTypeDesc(String taskTypeDesc) {
        this.taskTypeDesc = taskTypeDesc;
    }

    @Override
    public String toString() {
        return "TaskType{" + "taskTypeId=" + taskTypeId + ", taskTypeDesc=" + taskTypeDesc + '}';
    }

}
