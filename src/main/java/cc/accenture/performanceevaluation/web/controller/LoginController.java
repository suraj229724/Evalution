package cc.accenture.performanceevaluation.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private @Value("#{versionProperties['version.major']}")
    String majorVersion;
    private @Value("#{versionProperties['version.minor']}")
    String minorVersion;

    @RequestMapping("/home/login.htm")
    public String showLogin(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
        model.addAttribute("majorVersion", majorVersion);
        model.addAttribute("minorVersion", minorVersion);
        return "/home/login";
    }
}
