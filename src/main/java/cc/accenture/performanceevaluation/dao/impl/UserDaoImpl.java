/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao.impl;

import cc.accenture.performanceevaluation.dao.UserDao;
import cc.accenture.performanceevaluation.framework.GlobalConstants;
import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.Password;
import cc.accenture.performanceevaluation.model.Role;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.model.mapper.CustomUserDetailsRowMapper;
import cc.accenture.performanceevaluation.model.mapper.RoleRowMapper;
import cc.accenture.performanceevaluation.model.mapper.UserRowMapper;
import cc.accenture.performanceevaluation.utils.LogUtils;
import cc.altius.utils.DateUtils;
import cc.altius.utils.PassPhrase;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Suraj
 */
@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int updateFailedAttemptsByUserId(String userName) {
        try {
            String sqlQuery = "UPDATE `user` SET FAILED_ATTEMPTS=FAILED_ATTEMPTS+1 WHERE USERNAME=?";
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlQuery));
            return this.jdbcTemplate.update(sqlQuery, userName);
        } catch (DataAccessException e) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "Could not update failed attempts :" + e));
            return 0;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String sqlString = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM user "
                + " LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID "
                + " WHERE user.USERNAME=?";

        Object params[] = new Object[]{username};
        try {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, params));
            return this.jdbcTemplate.queryForObject(sqlString, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "No User found with username :" + username));
            return null;
        }
    }

    @Override
    public int resetFailedAttemptsByUserId(int userId) {
        try {
            Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
            String sqlreset = "UPDATE `user` SET FAILED_ATTEMPTS=0,LAST_LOGIN_DATE=? WHERE USER_ID=?";
            return this.jdbcTemplate.update(sqlreset, curDt, userId);
        } catch (DataAccessException e) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, e));
            return 0;
        }
    }

    @Override
    public CustomUserDetails getUserDetailsByUsername(String username) {
        String sql = "SELECT u.*, r.* FROM `user` u"
                + " LEFT JOIN `user_role` ur ON u.`USER_ID`=ur.`USER_ID`"
                + " LEFT JOIN  role r ON ur.`ROLE_ID`=r.`ROLE_ID`"
                + " WHERE u.`USERNAME`=?";
        Object params[] = new Object[]{username};
        try {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sql, params));
            return this.jdbcTemplate.queryForObject(sql, params, new CustomUserDetailsRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, erda));
            return null;
        } catch (Exception e) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, e));
            return null;
        }
    }

    @Override
    public List<String> getBusinessFunctionsForUserId(int userId) {
        String sqlString = "SELECT BUSINESS_FUNCTION_ID FROM user_role LEFT JOIN role_business_function ON user_role.ROLE_ID=role_business_function.ROLE_ID WHERE user_role.USER_ID=?";
        return this.jdbcTemplate.queryForList(sqlString, new Object[]{userId}, String.class);
    }

    @Override
    public List<Role> getCanCreateRoleList(String roleId) {
        String sqlString = "SELECT role.* from can_create_roles LEFT JOIN role on can_create_roles.CAN_CREATE_ROLE=role.ROLE_ID where can_create_roles.ROLE_ID=?";
        return this.jdbcTemplate.query(sqlString, new RoleRowMapper(), roleId);
    }

    @Override
    public int addUser(User user) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        SimpleJdbcInsert userInsert = new SimpleJdbcInsert(this.dataSource).withTableName("user").usingGeneratedKeyColumns("USER_ID");
        Map<String, Object> params = new HashMap<>();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPass = encoder.encode(user.getPassword());
        params.put("USERNAME", user.getUsername());
        params.put("PASSWORD", hashPass);
        params.put("ACTIVE", user.isActive());
        params.put("EXPIRED", false);
        params.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1));
        params.put("FAILED_ATTEMPTS", 0);
        params.put("OUTSIDE_ACCESS", user.isOutsideAccess());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("EMAIL_ID", user.getEmailId());
        params.put("PHONE_NO", user.getPhoneNo());

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "Insert into user : ", params));
        int userId = userInsert.executeAndReturnKey(params).intValue();
        params.clear();

        String sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, new Object[]{userId, user.getRole().getRoleId()}));
        this.jdbcTemplate.update(sqlString, userId, user.getRole().getRoleId());

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

        return userId;
    }

    @Override
    public User getUserByUserId(int userId) {
        String sqlString = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM user "
                + " LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID "
                + " WHERE user.USER_ID=?";

        Object params[] = new Object[]{userId};
        try {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, params));
            User user = this.jdbcTemplate.queryForObject(sqlString, params, new UserRowMapper());
            return user;
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "No User found with userId"));
            return null;
        }
    }

    @Override
    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId) {
        String sqlString = "SELECT count(*) from can_create_roles where can_create_roles.ROLE_ID=? and can_create_roles.CAN_CREATE_ROLE=?";
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, new Object[]{roleId, canCreateRoleId}));
        int i = this.jdbcTemplate.queryForObject(sqlString, Integer.class, roleId, canCreateRoleId);
        return i > 0;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        String sqlString;
        Object params[];

        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        sqlString = "UPDATE user SET PHONE_NO=?, USERNAME=?, EMAIL_ID=?, ACTIVE=?, OUTSIDE_ACCESS=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=?  WHERE USER_ID=?";

        params = new Object[]{user.getPhoneNo(), user.getUsername(), user.getEmailId(), user.isActive(), user.isOutsideAccess(), curUser, curDate, user.getUserId()};

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, params));
        this.jdbcTemplate.update(sqlString, params);

        sqlString = "DELETE FROM user_role WHERE USER_ID=?";

        params = new Object[]{user.getUserId()};

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, params));
        this.jdbcTemplate.update(sqlString, user.getUserId());

        sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";

        params = new Object[]{user.getUserId(), user.getRole().getRoleId()};

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, params));
        this.jdbcTemplate.update(sqlString, params);
    }

    @Override
    public String resetPasswordByUserId(int userId) {
        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String pass = PassPhrase.getPassword();
            Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
            Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1);
            String sqlString = "UPDATE user SET PASSWORD=?, FAILED_ATTEMPTS=0, EXPIRES_ON=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=? WHERE user.USER_ID=?";
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(pass);
            Object params[] = new Object[]{hash, offsetDate, curUser, curDt, userId};

            this.jdbcTemplate.update(sqlString, params);
            return pass;
        } catch (DataAccessException e) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, e));
            return null;
        }
    }

    @Override
    public boolean confirmPassword(Password password) {
        String sqlString = "SELECT user.PASSWORD FROM user WHERE user.USER_ID=?";

        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, new Object[]{password.getUserId()}));
        String hash = this.jdbcTemplate.queryForObject(sqlString, String.class, password.getUserId());
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(password.getOldPassword(), hash);
    }

    @Override
    public void updatePassword(Password password, int offset) {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, offset);
            String sqlString = "UPDATE user SET PASSWORD=?, EXPIRES_ON=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=? WHERE user.USER_ID=?";
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(password.getNewPassword());
            Object params[] = new Object[]{hash, offsetDate, password.getUserId(), curDate, password.getUserId()};
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlString, params));
            this.jdbcTemplate.update(sqlString, params);
        } catch (DataAccessException e) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, e));
        }
    }

    @Override
    public List<User> getUserList(boolean active, String roleId) {

        String sql = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM user "
                + " LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID"
                + " where user.USER_ID";

        if (active) {
            sql += " AND user.ACTIVE";
        }

        Map<String, Object> params = new HashMap<String, Object>();

        if (roleId != null && !"".equals(roleId)) {
            sql += " AND user_role.ROLE_ID=:roleId ";
            params.put("roleId", roleId);
        }

        sql += " ORDER BY user.`LAST_MODIFIED_DATE` DESC";

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new UserRowMapper());
    }
}
