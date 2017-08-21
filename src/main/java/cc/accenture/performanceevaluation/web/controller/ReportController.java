/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import cc.accenture.performanceevaluation.exception.CouldNotBuildExcelException;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.model.dto.AccessLogReportDTO;
import cc.accenture.performanceevaluation.service.ReportService;
import cc.accenture.performanceevaluation.service.UserService;
import cc.altius.utils.DateUtils;
import cc.altius.utils.POI.POICell;
import cc.altius.utils.POI.POIRow;
import cc.altius.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Suraj
 */
@Controller
public class ReportController {

    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/report/reportAccessLogExcel.htm")
    public void getAccessLogExcelReport(HttpServletRequest request, HttpServletResponse response, Errors errors, ModelMap modelMap) throws CouldNotBuildExcelException {
        try {
            String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
            int success = ServletRequestUtils.getIntParameter(request, "success", -1);

            List<AccessLogReportDTO> reportList = this.reportService.getAccessLogReport(startDate, stopDate, userId, success);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=AccessLog-" + startDate + "_to_" + stopDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Access Log report");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Access dt");
            headerRow.addCell("IP");
            headerRow.addCell("Username");
            headerRow.addCell("Success");
            headerRow.addCell("Outcome");

            mySheet.addRow(headerRow);

            for (AccessLogReportDTO data : reportList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getAccessDate(), POICell.TYPE_DATETIME);
                dataRow.addCell(data.getIpAddress(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getUsername(), POICell.TYPE_TEXT);
                dataRow.addCell((data.getSuccess() == 1 ? "Success" : "Failed"), POICell.TYPE_TEXT);
                dataRow.addCell(data.getOutcome(), POICell.TYPE_TEXT);

                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(io));
            throw new CouldNotBuildExcelException(io.getMessage());
        } catch (Exception e) {
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(e));
            throw new CouldNotBuildExcelException(e.getMessage());
        }
    }

    @RequestMapping(value = "/report/reportAccessLog.htm")
    public String showAccessLogReport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
        int success = ServletRequestUtils.getIntParameter(request, "success", -1);
        List<AccessLogReportDTO> reportList = this.reportService.getAccessLogReport(startDate, stopDate, userId, success);
        System.out.println("reportList = "+reportList);
        modelMap.addAttribute("reportList", reportList);

        List<User> userList = this.userService.getUserList(false, "");
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("stopDate", stopDate);
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("success", success);
        return "report/reportAccessLog";
    }
}
