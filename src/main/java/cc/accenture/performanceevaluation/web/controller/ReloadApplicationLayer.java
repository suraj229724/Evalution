/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import cc.accenture.performanceevaluation.framework.ApplicationSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Suraj
 */
@Controller
public class ReloadApplicationLayer {

    @RequestMapping("/admin/reloadApplicationLayer")
    public String reloadApplicationLayer() {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        applicationSession.reloadAll();
        return "redirect:../home/home.htm?msg=msg.reloadSuccess";
    }
}
