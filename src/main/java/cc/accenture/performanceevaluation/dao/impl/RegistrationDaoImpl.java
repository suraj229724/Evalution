/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao.impl;

import cc.accenture.performanceevaluation.dao.RegistrationDao;
import cc.accenture.performanceevaluation.framework.GlobalConstants;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.utils.LogUtils;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Suraj
 */
@Repository
public class RegistrationDaoImpl implements RegistrationDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int registerUser(User user) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        SimpleJdbcInsert userInsert = new SimpleJdbcInsert(this.dataSource).withTableName("user").usingGeneratedKeyColumns("USER_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPass = encoder.encode(user.getPassword());
        params.put("NAME", user.getName());
        params.put("USERNAME", user.getUsername());
        params.put("DESIGNATION", user.getDesignation());
        params.put("LEVEL", user.getLevel());
        params.put("LOCATION", user.getLocation());
        params.put("PASSWORD", hashPass);
        params.put("ACTIVE", 1);
        params.put("EXPIRED", false);
        params.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1));
        params.put("FAILED_ATTEMPTS", 0);
        params.put("OUTSIDE_ACCESS", user.isOutsideAccess());
        params.put("CREATED_BY", null);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", null);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("EMAIL_ID", user.getUsername());
        params.put("PHONE_NO", user.getPhoneNo());
//        System.out.println("params = "+params);
//        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM,"Inser into user : ", params));
        int userId = userInsert.executeAndReturnKey(params).intValue();
        params.clear();

        String sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, new Object[]{userId}));
        this.jdbcTemplate.update(sqlString, userId, "ROLE_EMPLOYEE");

        return userId;
    }
}
