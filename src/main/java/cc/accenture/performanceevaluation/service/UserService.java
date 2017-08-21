/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.service;

import cc.accenture.performanceevaluation.model.Password;
import cc.accenture.performanceevaluation.model.Role;
import cc.accenture.performanceevaluation.model.User;
import java.util.List;

/**
 *
 * @author Suraj
 */
public interface UserService {

    public int updateFailedAttemptsByUserId(String userName);

    public int resetFailedAttemptsByUserId(int userId);

    public List<Role> getRoleList();

    public boolean existUserByUsername(String username);

    public int addUser(User user);

    public User getUserByUserId(int userId);

    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId);

    public User getUserByUsername(String username);

    public void updateUser(User user);

    public String resetPasswordByUserId(int userId);

    public boolean confirmPassword(Password password);

    public void updatePassword(Password password, int offset);

    public List<String> getBusinessFunctionsForUserId(int userId);

    public List<User> getUserList(boolean active, String roleId);
}
