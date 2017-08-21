/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.security.applicationListener;

import cc.accenture.performanceevaluation.framework.GlobalConstants;
import cc.accenture.performanceevaluation.service.UserService;
import cc.accenture.performanceevaluation.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author Suraj
 */
public class BadCredentialsEvent implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String name = (String) e.getAuthentication().getPrincipal();
        System.out.println("test = "+name);
        this.userService.updateFailedAttemptsByUserId(name);
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, ((WebAuthenticationDetails) e.getAuthentication().getDetails()).getRemoteAddress(), name, "Incorrect password"));
    }
}
