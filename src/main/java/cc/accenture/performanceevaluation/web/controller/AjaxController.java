/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import cc.altius.utils.PassPhrase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Suraj
 */
@Controller
public class AjaxController {

    @RequestMapping(value = "admin/ajaxGeneratePassword.htm", method = RequestMethod.GET)
    public @ResponseBody
    String ajaxGeneratePassword() {
        String json;
        String pass = PassPhrase.getPassword();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pass", pass);

        Gson gson = new Gson();
        Type typeList = new TypeToken<Map>() {
        }.getType();
        json = gson.toJson(map, typeList);
        return json;
    }
}
