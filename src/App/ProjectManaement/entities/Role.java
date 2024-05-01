/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.ProjectManaement.entities;


public class Role {   
    private Integer RoleId;
    private String roleCode;
    private String rolename;

    public Role(Integer RoleId, String roleCode, String rolename) {
        this.RoleId = RoleId;
        this.roleCode = roleCode;
        this.rolename = rolename;
    }

    /**
     * @return the RoleId
     */
    public Integer getRoleId() {
        return RoleId;
    }

    /**
     * @param RoleId the RoleId to set
     */
    public void setRoleId(Integer RoleId) {
        this.RoleId = RoleId;
    }

    /**
     * @return the roleCode
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return the rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename the rolename to set
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    
    
}
