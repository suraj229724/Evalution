/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Suraj
 */
@Component
@Scope("application")
public class ApplicationSession {

    @Autowired
    private ApplicationLoadService applicationLoadService;

    public static ApplicationSession getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    public void reloadAll() {
    }
}
