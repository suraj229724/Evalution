/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.web.controller;

import cc.accenture.performanceevaluation.model.CustomUserDetails;
import cc.accenture.performanceevaluation.model.Password;
import cc.accenture.performanceevaluation.model.Role;
import cc.accenture.performanceevaluation.model.User;
import cc.accenture.performanceevaluation.service.UserService;
import cc.accenture.performanceevaluation.utils.LogUtils;
import cc.altius.utils.DateUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Suraj
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/addUser.htm", method = RequestMethod.GET)
    public String showAddUserForm(ModelMap model) {
        User user = new User();
        user.setActive(true);
        model.addAttribute("user", user);
        model.addAttribute("roleList", this.userService.getRoleList());
        return "/admin/addUser";
    }

    @RequestMapping(value = "/admin/addUser.htm", method = RequestMethod.POST)
    public String onAddUserSubmit(@ModelAttribute("user") User user, Errors errors, ModelMap model, HttpServletRequest request) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            user = null;
            return "redirect:/admin/userList.htm?msg=msg.actionCancelled";
        } else {
            if (this.userService.existUserByUsername(user.getUsername())) {
                errors.rejectValue("username", "msg.duplicateUser");
                model.addAttribute("roleList", this.userService.getRoleList());
                return "/admin/addUser";
            }
            int userId = this.userService.addUser(user);
            if (userId == 0) {
                errors.rejectValue("username", "msg.userError");
                model.addAttribute("roleList", this.userService.getRoleList());
                return "/admin/addUser";
            } else {
                return "redirect:/admin/userList.htm?msg=msg.userAddedSuccessfully";
            }
        }
    }

    @RequestMapping(value = "/admin/userList.htm", method = RequestMethod.GET)
    public String showUserListPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        String roleId1 = (String) (session.getAttribute("roleId") == null ? "" : session.getAttribute("roleId"));
        String roleId = ServletRequestUtils.getStringParameter(request, "roleId", roleId1);
        session.setAttribute("roleId", roleId);
        List<Role> roleList = this.userService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("userList", this.userService.getUserList(false,roleId));
        model.addAttribute("roleId", roleId);
        return "/admin/userList";
    }

    @RequestMapping(value = "/admin/userFailedAttemptsReset.htm")
    public String resetFailedAttempts(HttpServletRequest request, HttpSession session) {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        String roleId = (String) (session.getAttribute("roleId"));
        session.setAttribute("roleId", roleId);

        int result = this.userService.resetFailedAttemptsByUserId(userId);
        if (result != 0) {
            return "redirect:../admin/userList.htm?msg=Failed attempts reset successfully.";
        } else {
            return "redirect:../admin/userList.htm?error=Sorry! Your data not saved.";
        }
    }

    @RequestMapping(value = "/admin/userPasswordReset.htm")
    public String userPasswordReset(HttpServletRequest request, HttpSession session) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        String roleId = (String) (session.getAttribute("roleId"));
        session.setAttribute("roleId", roleId);
        User user = this.userService.getUserByUserId(userId);
        if (!this.userService.canCreateRoleByRoleId(curUser.getRole().getRoleId(), user.getRole().getRoleId())) {
            return "redirect:/admin/userList.htm?msg=msg.cantEditRole";
        } else {
            String pass = this.userService.resetPasswordByUserId(userId);
            if (pass != null) {
                return "redirect:../admin/userList.htm?msg=Your new password is :" + pass;
            } else {
                return "redirect:../admin/userList.htm?msg=Failed to reset your password.";
            }
        }
    }

    @RequestMapping(value = "/admin/showEditUser.htm", method = RequestMethod.POST)
    public String showEditUserForm(@RequestParam(value = "userId", required = true) int userId, ModelMap model) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = this.userService.getUserByUserId(userId);
        if (!this.userService.canCreateRoleByRoleId(curUser.getRole().getRoleId(), user.getRole().getRoleId())) {
            return "redirect:/admin/userList.htm?error=msg.cantEditRole";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("roleList", this.userService.getRoleList());
            return "/admin/editUser";
        }
    }

    @RequestMapping(value = "/admin/editUser.htm", method = RequestMethod.POST)
    public String onEditUserSubmit(@ModelAttribute("user") User user, Errors errors, ModelMap model, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            if (!this.userService.canCreateRoleByRoleId(curUser.getRole().getRoleId(), user.getRole().getRoleId())) {
                return "redirect:/admin/userList.htm?error=msg.cantEditRole";
            } else {
                // Passed the can create check so go ahead 
                User newUser = this.userService.getUserByUsername(user.getUsername());
                if (newUser != null && newUser.getUserId() != user.getUserId()) {
                    errors.rejectValue("username", "msg.duplicateUser");
                    model.addAttribute("roleList", this.userService.getRoleList());
                    return "/admin/editUser";
                }
                try {
                    this.userService.updateUser(user);
                    return "redirect:/admin/userList.htm?msg=msg.userUpdatedSuccessfully";
                } catch (Exception e) {
//                    LogUtils.systemLogger.info(e);
                    e.printStackTrace();
                    errors.rejectValue("username", "msg.userError");
                    model.addAttribute("roleList", this.userService.getRoleList());
                    return "/admin/editUser";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    @RequestMapping(value = "/admin/editUser.htm", method = RequestMethod.POST, params = "_cancel")
    public String onEditUserCancel(@ModelAttribute("user") User user, ModelMap model) {
        user = null;
        return "redirect:../admin/userList.htm?msg=msg.actionCancelled";
    }

    @RequestMapping(value = "/admin/updateExpiredPassword.htm", method = RequestMethod.GET)
    public String showUpdatePasswordExpiredForm(ModelMap model) {
        Password password = new Password();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LogUtils.debugLogger.debug("Showing form for UpdateExpiredPassword");
        password.setUserId(curUser.getUserId());
        password.setUsername(curUser.getUsername());
        model.addAttribute("password", password);
        return "/admin/updateExpiredPassword";
    }

    @RequestMapping(value = "/admin/updateExpiredPassword.htm", method = RequestMethod.POST)
    public String updatePasswordExpiredOnSubmit(final @ModelAttribute("password") Password password, Errors errors) {
        if (!this.userService.confirmPassword(password)) {
            errors.rejectValue("oldPassword", "msg.oldPasswordNotMatch");
            CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            password.setUserId(curUser.getUserId());
            password.setUsername(curUser.getUsername());
            return "/admin/updateExpiredPassword";
        } else {
            LogUtils.debugLogger.debug("Updating user password");
            this.userService.updatePassword(password, 90);
            // all you need to do now is load the correct Authorities
            Authentication curAuthentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            curUser.setExpiresOn(DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, 90));
            curUser.setBusinessFunction(this.userService.getBusinessFunctionsForUserId(curUser.getUserId()));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(curUser, curAuthentication.getCredentials(), curUser.getAuthorities());
            auth.setDetails(curAuthentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/home/home.htm?msg=msg.passwordUpdated";
        }
    }

    @RequestMapping(value = "/admin/updateExpiredPassword.htm", method = RequestMethod.POST, params = "_cancel")
    public String onUpdateExpiredPasswordCancel() {
        return "redirect:../home/login.htm";
    }

    @RequestMapping(value = "/home/changePassword.htm", method = RequestMethod.GET)
    public String showChangePasswordPage(ModelMap model) {
        Password password = new Password();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        password.setUserId(curUser.getUserId());
        password.setUsername(curUser.getUsername());
        model.addAttribute("password", password);
        return "/home/changePassword";
    }

    @RequestMapping(value = "/home/changePassword.htm", method = RequestMethod.POST)
    public String onChangePasswordSubmit(@ModelAttribute("password") Password password, Errors errors, HttpServletRequest request) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            password = null;
            return "redirect:/home/home.htm?msg=msg.actionCancelled";
        } else {
            if (!this.userService.confirmPassword(password)) {
                errors.rejectValue("oldPassword", "msg.oldPasswordNotMatch");
                return "/home/changePassword";
            } else {
                this.userService.updatePassword(password, 90);
                return "redirect:/home/home.htm?msg=msg.passwordUpdated";
            }
        }
    }
}
