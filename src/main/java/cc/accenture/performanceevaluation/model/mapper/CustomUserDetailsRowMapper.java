/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model.mapper;

import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Suraj
 */
public class CustomUserDetailsRowMapper implements RowMapper<CustomUserDetails> {

    @Override
    public CustomUserDetails mapRow(ResultSet rs, int i) throws SQLException {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUserId(rs.getInt("USER_ID"));
        customUserDetails.setUsername(rs.getString("USERNAME"));
        customUserDetails.setPassword(rs.getString("PASSWORD"));
        customUserDetails.setActive(rs.getBoolean("ACTIVE"));
        customUserDetails.setExpired(rs.getBoolean("EXPIRED"));
        customUserDetails.setFailedAttempts(rs.getInt("FAILED_ATTEMPTS"));
        customUserDetails.setExpiresOn(rs.getDate("EXPIRES_ON"));
        customUserDetails.setOutsideAccess(rs.getBoolean("OUTSIDE_ACCESS"));
        customUserDetails.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));

        Role role = new Role();
        role.setRoleId(rs.getString("ROLE_ID"));
        role.setRoleName(rs.getString("ROLE_NAME"));
        customUserDetails.setRole(role);

        return customUserDetails;
    }
}