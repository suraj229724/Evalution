/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.service.impl;

import cc.accenture.performanceevaluation.dao.RegistrationDao;
import cc.accenture.performanceevaluation.framework.GlobalConstants;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.service.RegistrationService;
import cc.accenture.performanceevaluation.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Suraj
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationDao registrationDao;

    @Override
    public String registerUser(User user) {
        try {
            int id = this.registrationDao.registerUser(user);
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "Registration done sucessfully for user id:" + id));
            return "Thank you for Registering.";
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM,e));
            return "Sorry your data not saved.";
        }
    }
}
