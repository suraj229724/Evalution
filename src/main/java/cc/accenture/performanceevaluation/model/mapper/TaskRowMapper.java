/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model.mapper;

import cc.accenture.performanceevaluation.model.Client;
import cc.accenture.performanceevaluation.model.ShiftTime;
import cc.accenture.performanceevaluation.model.Task;
import cc.accenture.performanceevaluation.model.TaskType;
import cc.accenture.performanceevaluation.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {
        Task task = new Task();
        task.setTaskId(rs.getInt("TASK_ID"));
        Client client = new Client();
        client.setClientId(rs.getInt("CLIENT_ID"));
        client.setClientName(rs.getString("CLIENT_NAME"));
        task.setClient(client);
        TaskType type = new TaskType();
        type.setTaskTypeId(rs.getInt("TASK_TYPE_ID"));
        type.setTaskTypeDesc(rs.getString("TASK_TYPE_DESC"));
        task.setTaskType(type);
        ShiftTime time = new ShiftTime();
        time.setShiftTimeId(rs.getInt("SHIFT_TIME_ID"));
        time.setShiftTime(rs.getString("SHIFT_TIME"));
        task.setShiftTime(time);
        task.setTaskName(rs.getString("TASK_NAME"));
        task.setTaskDate(rs.getTimestamp("TASK_DATE"));
        task.setTaskDetails(rs.getString("TASK_DETAILS"));
        task.setComments(rs.getString("COMMENTS"));
        task.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        User user1 = new User();
        user1.setUserId(rs.getInt("CREATED_BY"));
        user1.setUsername(rs.getString("CREATED_BY_NAME"));
        task.setCreatedBy(user1);
        User user2 = new User();
        user2.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        user2.setUsername(rs.getString("LAST_MODIFIED_NAME"));
        task.setLastModifiedBy(user2);
        task.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        return task;
    }

}
