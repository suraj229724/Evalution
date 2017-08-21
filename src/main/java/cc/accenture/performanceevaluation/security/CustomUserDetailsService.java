/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.security;


import cc.accenture.performanceevaluation.dao.UserDao;
import cc.accenture.performanceevaluation.framework.GlobalConstants;
import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.utils.LogUtils;
import cc.altius.utils.IPUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Suraj
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    private Set<String> allowedIpRange;

    public CustomUserDetailsService() {
        this.allowedIpRange = new HashSet<>();
        this.allowedIpRange.addAll(Arrays.asList(GlobalConstants.ALLOWED_IP_RANGE));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        try {
            CustomUserDetails user = this.userDao.getUserDetailsByUsername(username);
            
            if (!user.isActive()) {
                LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, ipAddress, username, "Account disabled"));
            } else if (!user.isAccountNonLocked()) {
                LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, ipAddress, username, "Account locked"));
            } else if (!(user.isOutsideAccess() || checkIfIpIsFromAllowedRange(ipAddress))) {
                user.setActive(false);
                LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, ipAddress, username, "Outside access"));
            } else {
                if (user.isPasswordExpired()) {
                    // only insert the ROLE_BF_PASSWORD_EXPIRED
                    LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_ACCESS, "Credentials are Expired so only put in ROLE_BF_PASSWORD_EXPIRED into Authoirites"));
                    List<String> businessFunctions = new LinkedList<>();
                    businessFunctions.add("ROLE_BF_PASSWORD_EXPIRED");
                    user.setBusinessFunction(businessFunctions);
                } else {
                    user.setBusinessFunction(this.userDao.getBusinessFunctionsForUserId(user.getUserId()));
                }
            }
            return user;
        } catch (EmptyResultDataAccessException erda) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private boolean checkIfIpIsFromAllowedRange(String ipToCheck) {
        for (String curRange : this.allowedIpRange) {
            IPUtils curIpRange = new IPUtils(curRange);
            if (curIpRange.checkIP(ipToCheck)) {
                return true;
            }
        }
        return false;
    }
}
