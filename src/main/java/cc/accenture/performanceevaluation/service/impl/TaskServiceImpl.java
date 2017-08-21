/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.service.impl;

import cc.accenture.performanceevaluation.dao.TaskDao;
import cc.accenture.performanceevaluation.model.Client;
import cc.accenture.performanceevaluation.model.ShiftTime;
import cc.accenture.performanceevaluation.model.Task;
import cc.accenture.performanceevaluation.model.TaskType;
import cc.accenture.performanceevaluation.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public List<Client> getClientList() {
        return this.taskDao.getClientList();
    }

    @Override
    public List<TaskType> getTaskTypeList() {
        return this.taskDao.getTaskTypeList();
    }

    @Override
    public List<ShiftTime> getShiftTimeList() {
        return this.taskDao.getShiftTimeList();
    }

    @Override
    public int addTask(Task task) {
        return this.taskDao.addTask(task);
    }

    @Override
    public List<Task> getTaskList(String startDate, String stopDate, int userId) {
        return this.taskDao.getTaskList(startDate, stopDate, userId);
    }

    @Override
    public Task getTaskByTaskId(int taskId) {
        return this.taskDao.getTaskByTaskId(taskId);
    }

    @Override
    public int updateTask(Task task) {
        return this.taskDao.updateTask(task);
    }

}
