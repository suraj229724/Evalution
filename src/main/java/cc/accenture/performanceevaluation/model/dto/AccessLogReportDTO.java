/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model.dto;

import java.util.Date;

/**
 *
 * @author Suraj
 */
public class AccessLogReportDTO {

    private String ipAddress;
    private String username;
    private Integer userId;
    private Date accessDate;
    private int success;
    private String outcome;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
