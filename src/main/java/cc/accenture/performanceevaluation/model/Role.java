/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model;

import java.io.Serializable;

/**
 *
 * @author Suraj
 */
public class Role implements Serializable {

    private String roleId;
    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
