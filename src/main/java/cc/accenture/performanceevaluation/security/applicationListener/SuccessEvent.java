/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.security.applicationListener;

import cc.accenture.performanceevaluation.framework.GlobalConstants;
import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.service.UserService;
import cc.accenture.performanceevaluation.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author Suraj
 */
public class SuccessEvent implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        CustomUserDetails cud = (CustomUserDetails) e.getAuthentication().getPrincipal();
        this.userService.resetFailedAttemptsByUserId(cud.getUserId());
        try {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, ((WebAuthenticationDetails) e.getAuthentication().getDetails()).getRemoteAddress(), cud.getUsername(), "Success"));
        } catch (Exception e1) {
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, e1));
        }
    }
}
