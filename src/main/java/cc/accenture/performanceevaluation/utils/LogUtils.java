/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.utils;

import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.altius.utils.StringUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author Suraj
 */
public class LogUtils {

//    @Autowired
    public static Logger systemLogger = Logger.getLogger("systemLogging");
    public static Logger debugLogger = Logger.getLogger("debugLogging");

    public static String buildStringForSystemLog(String tag, Exception e) {
        StringWriter sWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(sWriter));
        return (new StringBuilder(tag).append("','").append(getIpAddress()).append("','").append(getUsername()).append("','").append(StringUtils.escapeQuotes(sWriter.toString())).toString());
    }

    public static String buildStringForSystemLog(String tag, String sqlString, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(tag).append("','").append(getIpAddress()).append("','").append(getUsername()).append("','").append(StringUtils.escapeQuotes(sqlString)).append(" -- parameters( ");
        boolean firstRun = true;
        for (Map.Entry tmpEntry : params.entrySet()) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpEntry.getKey()).append(":").append(StringUtils.escapeQuotes(tmpEntry.getValue().toString()));
            } else {
                sb.append(", ").append(tmpEntry.getKey()).append(":").append(StringUtils.escapeQuotes(tmpEntry.getValue().toString()));
            }
        }
        sb.append(")");
        return (sb.toString());
    }

    public static String buildStringForSystemLog(String tag, String sqlString, Object[] params) {
        StringBuilder sb = new StringBuilder(tag).append("','").append(getIpAddress()).append("','").append(getUsername()).append("','").append(StringUtils.escapeQuotes(sqlString)).append(" -- parameters( ");
        boolean firstRun = true;
        for (Object tmpParam : params) {
            if (firstRun) {
                firstRun = false;
                sb.append(StringUtils.escapeQuotes(tmpParam.toString()));
            } else {
                sb.append(", ").append(StringUtils.escapeQuotes(tmpParam.toString()));
            }
        }
        sb.append(")");
        return (sb.toString());
    }

    public static String buildStringForSystemLog(String tag, String sqlString, List<Object[]> paramList) {
        StringBuilder sb = new StringBuilder(tag).append("','").append(getIpAddress()).append("','").append(getUsername()).append("','").append(StringUtils.escapeQuotes(sqlString)).append(" -- parameters( ");
        boolean firstRun;
        for (Object params[] : paramList) {
            sb.append(" (");
            firstRun = true;
            for (Object tmpParam : params) {
                if (firstRun) {
                    firstRun = false;
                    sb.append(StringUtils.escapeQuotes(tmpParam.toString()));
                } else {
                    sb.append(", ").append(StringUtils.escapeQuotes(tmpParam.toString()));
                }
            }
            sb.append(")");
        }
        sb.append(")");
        return (sb.toString());
    }

    public static String buildStringForSystemLog(String tag, String ipAddress, String username, String message) {
        return new StringBuilder(tag).append("','").append(ipAddress).append("','").append(username).append("','").append(StringUtils.escapeQuotes(message)).toString();
    }

    public static String buildStringForSystemLog(String tag, String message) {
        return new StringBuilder(tag).append("','").append(getIpAddress()).append("','").append(getUsername()).append("','").append(StringUtils.escapeQuotes(message)).toString();
    }

    private static String getIpAddress() {
        try {
            return ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
        } catch (NullPointerException n) {
            return "0.0.0.0";
        }
    }

    private static int getUserId() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == CustomUserDetails.class) {
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            } else {
                return 0;
            }
        } catch (NullPointerException n) {
            return 0;
        }
    }

    private static String getUsername() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == CustomUserDetails.class) {
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            } else {
                return "";
            }
        } catch (NullPointerException n) {
            return "blank";
        }
    }
}
