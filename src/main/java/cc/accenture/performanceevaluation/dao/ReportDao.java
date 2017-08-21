/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao;

import cc.accenture.performanceevaluation.model.dto.AccessLogReportDTO;
import java.util.List;

/**
 *
 * @author Suraj
 */
public interface ReportDao {

    public List<AccessLogReportDTO> getAccessLogReport(String startDate, String stopDate, int userId, int success);
}
