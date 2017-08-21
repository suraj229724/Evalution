/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao.impl;

import cc.accenture.performanceevaluation.dao.TaskDao;
import cc.accenture.performanceevaluation.model.Client;
import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.ShiftTime;
import cc.accenture.performanceevaluation.model.Task;
import cc.accenture.performanceevaluation.model.TaskType;
import cc.accenture.performanceevaluation.model.dto.mapper.AccessLogReportDTORowMapper;
import cc.accenture.performanceevaluation.model.mapper.TaskRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class TaskDaoImpl implements TaskDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Client> getClientList() {
        String sql = "SELECT * FROM `performance_evaluation`.`client` ";
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class));
    }

    @Override
    public List<TaskType> getTaskTypeList() {
        String sql = "SELECT * FROM `performance_evaluation`.`task_type` ";
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TaskType.class));
    }

    @Override
    public List<ShiftTime> getShiftTimeList() {
        String sql = "SELECT * FROM `performance_evaluation`.`shift_time` ";
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ShiftTime.class));
    }

    @Override
    public int addTask(Task task) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("task").usingGeneratedKeyColumns("TASK_ID");
        Map params = new HashMap();
        params.put("CLIENT_ID", task.getClient().getClientId());
        params.put("SHIFT_TIME_ID", task.getShiftTime().getShiftTimeId());
        params.put("TASK_TYPE_ID", task.getTaskType().getTaskTypeId());
        params.put("TASK_DATE", task.getTaskDate());
        params.put("TASK_NAME", task.getTaskName());
        params.put("TASK_DETAILS", task.getTaskDetails());
        params.put("COMMENTS", task.getComments());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDt);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDt);
        return insert.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<Task> getTaskList(String startDate, String stopDate, int userId) {
        startDate += " 00:00:00";
        stopDate += " 23:59:59";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        String sql = "SELECT t.*,s.`SHIFT_TIME`,c.`CLIENT_NAME`,tt.`TASK_TYPE_DESC`,createdBy.`USERNAME` `CREATED_BY_NAME`,modifiedBy.`USERNAME` `LAST_MODIFIED_NAME` FROM task t "
                + " LEFT JOIN `client` c ON c.`CLIENT_ID`=t.`CLIENT_ID` "
                + " LEFT JOIN task_type tt ON tt.`TASK_TYPE_ID`=t.`TASK_TYPE_ID` "
                + " LEFT JOIN shift_time s ON s.`SHIFT_TIME_ID`=t.`SHIFT_TIME_ID` "
                + " LEFT JOIN `user` `createdBy` ON createdBy.`USER_ID`=t.`CREATED_BY` "
                + " LEFT JOIN `user` `modifiedBy` ON modifiedBy.`USER_ID`=t.`LAST_MODIFIED_BY`"
                + " WHERE t.`CREATED_DATE` BETWEEN :startDate AND :stopDate ";
        if (userId != -1) {
            sql += " AND t.`CREATED_BY`=:userId";
            params.put("userId", userId);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new TaskRowMapper());
    }

    @Override
    public Task getTaskByTaskId(int taskId) {
        String sql = "SELECT t.*,s.`SHIFT_TIME`,c.`CLIENT_NAME`,tt.`TASK_TYPE_DESC`,createdBy.`USERNAME` `CREATED_BY_NAME`,modifiedBy.`USERNAME` `LAST_MODIFIED_NAME` FROM task t "
                + " LEFT JOIN `client` c ON c.`CLIENT_ID`=t.`CLIENT_ID` "
                + " LEFT JOIN task_type tt ON tt.`TASK_TYPE_ID`=t.`TASK_TYPE_ID` "
                + " LEFT JOIN shift_time s ON s.`SHIFT_TIME_ID`=t.`SHIFT_TIME_ID` "
                + " LEFT JOIN `user` `createdBy` ON createdBy.`USER_ID`=t.`CREATED_BY` "
                + " LEFT JOIN `user` `modifiedBy` ON modifiedBy.`USER_ID`=t.`LAST_MODIFIED_BY`"
                + " WHERE t.`TASK_ID`=? ";
        Task task = this.jdbcTemplate.queryForObject(sql, new TaskRowMapper(), taskId);
        return task;
    }

    @Override
    public int updateTask(Task task) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String sql = "UPDATE `task` SET `CLIENT_ID`=:client,`SHIFT_TIME_ID`=:shiftTime ,`TASK_TYPE_ID`=:taskType ,"
                + "`TASK_DATE`=:taskDate,`TASK_NAME`=:taskName,`TASK_DETAILS`=:taskDetails,`COMMENTS`=:comments,"
                + "`LAST_MODIFIED_BY`=:modifiedBy,`LAST_MODIFIED_DATE`=:modifiedDate WHERE `TASK_ID`=:taskId ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("client", task.getClient().getClientId());
        params.put("shiftTime", task.getShiftTime().getShiftTimeId());
        params.put("taskType", task.getTaskType().getTaskTypeId());
        params.put("taskDate", task.getTaskDate());
        params.put("taskName", task.getTaskName());
        params.put("taskDetails", task.getTaskDetails());
        params.put("comments", task.getComments());
        params.put("modifiedBy", curUser);
        params.put("modifiedDate", curDt);
        params.put("taskId", task.getTaskId());
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.update(sql, params);
    }

}
