/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.service.RegistrationService;
import cc.accenture.performanceevaluation.service.UserService;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Suraj
 */
@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserService userService;
    @Value("#{settings.publicKey}")
    private String publicKey;
    @Value("#{settings.privateKey}")
    private String privateKey;
    @Value("#{settings.globalKey}")
    private String globalKey;

    @RequestMapping(value = "/home/newRegistration.htm", method = RequestMethod.GET)
    public String showRegisterForm(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
        model.addAttribute("capcha", c.createRecaptchaHtml(null, null));
        return "/home/newRegistration";
    }

    @RequestMapping(value = "/home/newRegistration.htm", method = RequestMethod.POST, params = "_cancel")
    public String onCancelRegistration() {
        return "redirect:../home/login.htm?msg=msg.actionCancelled";
    }

    @RequestMapping(value = "/home/newRegistration.htm", method = RequestMethod.POST, params = "btnSubmit")
    public String registrationFormPost(@ModelAttribute("user") User user, Errors errors, ModelMap map, HttpServletRequest request) {
        ReCaptchaResponse reCaptchaResponse = null;
        try {
            ReCaptchaImpl captcha = new ReCaptchaImpl();
            captcha.setPrivateKey(privateKey);
            captcha.setPublicKey(globalKey);//use for live.
//            captcha.setPublicKey(privateKey);//use for local.

            String challenge = request.getParameter("recaptcha_challenge_field");
            String uresponse = request.getParameter("recaptcha_response_field");
            reCaptchaResponse = captcha.checkAnswer(request.getRemoteAddr(), challenge, uresponse);
        } catch (Exception e) {
            map.addAttribute("message", "Captcha Validated failed to get parameter");
            map.addAttribute("user", user);
            //ReCaptcha
            ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
            map.addAttribute("capcha", c.createRecaptchaHtml(null, null));
            //end
            return "/home/newRegistration";
        }
        if (reCaptchaResponse.isValid()) {
            try {
                if (this.userService.existUserByUsername(user.getUsername())) {
                    errors.rejectValue("username", "msg.existingUser");
                    ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
                    map.addAttribute("capcha", c.createRecaptchaHtml(null, null));
                    return "/home/newRegistration";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                errors.rejectValue("username","Sorry your data not saved");
                map.addAttribute("user", user);
                //ReCaptcha
                ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
                map.addAttribute("capcha", c.createRecaptchaHtml(null, null));
                //end
                return "/home/newRegistration";
            }
            String msg = this.registrationService.registerUser(user);
            return "redirect:../home/login.htm?msg=" + msg;
        } else {
            map.addAttribute("message", "Incorrect Captcha! try again");
            map.addAttribute("user", user);
            //ReCaptcha
            ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
            map.addAttribute("capcha", c.createRecaptchaHtml(null, null));
            //end
            return "/home/newRegistration";
        }
    }
}
