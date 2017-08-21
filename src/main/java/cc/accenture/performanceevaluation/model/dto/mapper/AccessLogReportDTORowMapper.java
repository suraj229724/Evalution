/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model.dto.mapper;

import cc.accenture.performanceevaluation.model.dto.AccessLogReportDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Suraj
 */
public class AccessLogReportDTORowMapper implements RowMapper<AccessLogReportDTO> {

    @Override
    public AccessLogReportDTO mapRow(ResultSet rs, int i) throws SQLException {
        AccessLogReportDTO accessLogReportDTO = new AccessLogReportDTO();
        accessLogReportDTO.setIpAddress(rs.getString("IP"));
        accessLogReportDTO.setUsername(rs.getString("USERNAME"));
        accessLogReportDTO.setUserId(rs.getInt("USER_ID"));
        accessLogReportDTO.setAccessDate(rs.getTimestamp("ACCESS_DATE"));
        accessLogReportDTO.setSuccess(rs.getInt("SUCCESS"));
        accessLogReportDTO.setOutcome(rs.getString("OUTCOME"));

        return accessLogReportDTO;
    }
}
