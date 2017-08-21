/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao;

import cc.accenture.performanceevaluation.model.Client;
import cc.accenture.performanceevaluation.model.ShiftTime;
import cc.accenture.performanceevaluation.model.Task;
import cc.accenture.performanceevaluation.model.TaskType;
import java.util.List;

/**
 *
 * @author altius
 */
public interface TaskDao {
    
    public List<Client> getClientList();
    
    public List<TaskType> getTaskTypeList();
    
    public List<ShiftTime> getShiftTimeList();
    
    public int addTask(Task task);
    
    public List<Task> getTaskList(String startDate, String stopDate, int userId);
    
    public Task getTaskByTaskId(int taskId);
    
    public int updateTask(Task task);
}
