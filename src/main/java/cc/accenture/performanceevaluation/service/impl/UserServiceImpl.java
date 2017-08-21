/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.service.impl;

import cc.accenture.performanceevaluation.dao.UserDao;
import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.Password;
import cc.accenture.performanceevaluation.model.Role;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Suraj
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int updateFailedAttemptsByUserId(String userName) {
        return this.userDao.updateFailedAttemptsByUserId(userName);
    }

    @Override
    public int resetFailedAttemptsByUserId(int userId) {
        return this.userDao.resetFailedAttemptsByUserId(userId);
    }

    @Override
    public List<Role> getRoleList() {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return this.userDao.getCanCreateRoleList(curUser.getRole().getRoleId());
    }

    @Override
    public boolean existUserByUsername(String username) {
        if (this.userDao.getUserByUsername(username) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int addUser(User user) {
        try {
            return this.userDao.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public User getUserByUserId(int userId) {
        return this.userDao.getUserByUserId(userId);
    }

    @Override
    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId) {
        return this.userDao.canCreateRoleByRoleId(roleId, canCreateRoleId);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    public String resetPasswordByUserId(int userId) {
        return this.userDao.resetPasswordByUserId(userId);
    }

    @Override
    public boolean confirmPassword(Password password) {
        return this.userDao.confirmPassword(password);
    }

    @Override
    public void updatePassword(Password password, int offset) {
        this.userDao.updatePassword(password, offset);
    }

    @Override
    public List<String> getBusinessFunctionsForUserId(int userId) {
        return this.userDao.getBusinessFunctionsForUserId(userId);
    }

    @Override
    public List<User> getUserList(boolean active, String roleId) {
        return this.userDao.getUserList(active, roleId);
    }
}
