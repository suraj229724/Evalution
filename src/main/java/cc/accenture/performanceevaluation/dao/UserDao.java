/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.dao;

import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.Password;
import cc.accenture.performanceevaluation.model.Role;
import cc.accenture.performanceevaluation.model.User;
import java.util.List;

/**
 *
 * @author Suraj
 */
public interface UserDao {

    public int updateFailedAttemptsByUserId(String userName);

    public User getUserByUsername(String username);

    public int resetFailedAttemptsByUserId(int userId);

    public CustomUserDetails getUserDetailsByUsername(String username);

    public List<String> getBusinessFunctionsForUserId(int userId);

    public List<Role> getCanCreateRoleList(String roleId);

    public int addUser(User user);

    public User getUserByUserId(int userId);

    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId);

    public void updateUser(User user);

    public String resetPasswordByUserId(int userId);

    public boolean confirmPassword(Password password);

    public void updatePassword(Password password, int offset);

    public List<User> getUserList(boolean active, String roleId);

}
