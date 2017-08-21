/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Suraj
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/home/home.htm", method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        return "home/home";
    }
}
