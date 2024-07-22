
package App.projectManagement.form;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.HashMap;
import java.sql.Statement;
import java.util.ArrayList;

public class DBconnection {

    static Connection con = null;

    //conection Portand dont reweriting the code 
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_management", "root", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //combouser manager 
    public HashMap<String, Integer> PopulateCombouser() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(" SELECT UserID, UserName FROM userp u, role r WHERE u.RoleId = r.roleId AND r.roleName = 'Manager' ");
            ItemCombo cmi;

            while (rs.next()) {
                cmi = new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(), cmi.getValue());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    //combouser manager 
    public HashMap<String, Integer> PopulateComboprojet() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(" SELECT projectID , projectName FROM project ;");
            ItemCombo cmi;

            while (rs.next()) {
                cmi = new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(), cmi.getValue());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //combouser manager 
    public HashMap<String, Integer> PopulateCombousers() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(" SELECT UserID, UserName FROM userp;  ");
            ItemCombo cmi;

            while (rs.next()) {
                cmi = new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(), cmi.getValue());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //combouser manager 
    public HashMap<String, Integer> PopulateComboRole() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(" SELECT roleID, roleName FROM role ; ");
            ItemCombo cmi;

            while (rs.next()) {
                cmi = new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(), cmi.getValue());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //combouser manager 
    public HashMap<String, Integer> PopulateCombotask() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(" SELECT `taskID`, `taskName` FROM `task` ");
            ItemCombo cmi;

            while (rs.next()) {
                cmi = new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(), cmi.getValue());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //permiss
    public static ArrayList<String> permission(String email) {
        ArrayList<String> taskAppNames = new ArrayList<>();
        try (Connection con = DBconnection.getConnection()) {
            String query = "SELECT p.TaskAppName FROM userp u "
                    + "INNER JOIN userpermission up ON u.UserID = up.UserID "
                    + "INNER JOIN permission p ON up.PermissionID = p.PermissionID "
                    + "WHERE u.UserEmail = ?";
            try (PreparedStatement prs = con.prepareStatement(query)) {
                prs.setString(1, email);

                try (ResultSet rs = prs.executeQuery()) {
                    boolean hasDashboardPermission = false;
                    while (rs.next()) {
                        String permission = rs.getString("TaskAppName");
                        taskAppNames.add(permission);

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskAppNames;
    }

}
