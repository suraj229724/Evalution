/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao.impl;


import cc.accenture.performanceevaluation.dao.ReportDao;
import cc.accenture.performanceevaluation.dao.UserDao;
import cc.accenture.performanceevaluation.model.dto.AccessLogReportDTO;
import cc.accenture.performanceevaluation.model.dto.mapper.AccessLogReportDTORowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Suraj
 */
@Repository
public class ReportDaoImpl implements ReportDao{
 private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    UserDao userDao;

    /**
     * Method to get the report of access log from specified date, userId,
     * success
     *
     * @param startDate statrtDate is a date from when you want to get the
     * report
     * @param stopDate stopDate is a date till when you want to get the report
     * @param userId userId is used to get the report from a particular user
     * @param success success is used to get the report as per the requirement
     * i.e. succeed/failed
     * @param pageNo
     * @return Returns the list of access log report
     */
    @Override
    public List<AccessLogReportDTO> getAccessLogReport(String startDate, String stopDate, int userId, int success) {
        startDate += " 00:00:00";
        stopDate += " 23:59:59";
        StringBuilder sql = new StringBuilder("SELECT access_log.`ACCESS_DATE`, access_log.`IP`, access_log.`USERNAME`, access_log.`USER_ID`, access_log.`SUCCESS`, access_log.`OUTCOME` FROM access_log WHERE "
                + "		access_log.`ACCESS_DATE` BETWEEN :startDate AND :stopDate");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);

        if (userId != -1) {
            sql.append(" AND access_log.`USER_ID`=:userId");
            params.put("userId", userId);
        }

        if (success != -1) {
            sql.append(" AND access_log.`SUCCESS`=:success");
            params.put("success", success);
        }

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

//        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sql.toString(), params));
        return nm.query(sql.toString(), params, new AccessLogReportDTORowMapper());
    }   
}
