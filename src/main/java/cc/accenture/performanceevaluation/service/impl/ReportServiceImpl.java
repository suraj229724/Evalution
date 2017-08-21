/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.service.impl;

import cc.accenture.performanceevaluation.dao.ReportDao;
import cc.accenture.performanceevaluation.model.dto.AccessLogReportDTO;
import cc.accenture.performanceevaluation.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Suraj
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public List<AccessLogReportDTO> getAccessLogReport(String startDate, String stopDate, int userId, int success) {
        return this.reportDao.getAccessLogReport(startDate, stopDate, userId, success);
    }
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
     * @return Returns the list of access log report by calling a method from
     * reportDao
     */
}
