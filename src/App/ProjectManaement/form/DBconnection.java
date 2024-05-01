
package App.ProjectManaement.form;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.sql.Statement;
public class DBconnection {
    static Connection con=null;
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/project_management","root","");
        } catch (Exception e) {
            e.printStackTrace();
        }
              return con;
    } 
    public HashMap<String,Integer> PopulateCombouser() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
           st=con.createStatement();
           rs=st.executeQuery(" SELECT UserID, UserName FROM userproject u, role r WHERE u.RoleId = r.RoleId AND r.NameRole = 'manager' ");
           ItemCombo cmi;
           
            while (rs.next()) {                
                cmi=new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(),cmi.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }     
        return map;
    }
       public HashMap<String,Integer> PopulateComboprojet() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
           st=con.createStatement();
           rs=st.executeQuery(" SELECT projectID, projectName FROM project ;");
           ItemCombo cmi;
           
            while (rs.next()) {                
                cmi=new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(),cmi.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }     
        return map;
    }
       
       public HashMap<String,Integer> PopulateCombousers() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
           st=con.createStatement();
           rs=st.executeQuery(" SELECT UserID, UserName FROM userproject;  ");
           ItemCombo cmi;
           
            while (rs.next()) {                
                cmi=new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(),cmi.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }     
        return map;
    }
         public HashMap<String,Integer> PopulateComboRole() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
           st=con.createStatement();
           rs=st.executeQuery(" SELECT RoleID, NameRole FROM role ; ");
           ItemCombo cmi;
           
            while (rs.next()) {                
                cmi=new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(),cmi.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }     
        return map;
    }
          public HashMap<String,Integer> PopulateCombotask() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        Connection con = DBconnection.getConnection();
        Statement st;
        ResultSet rs;
        try {
           st=con.createStatement();
           rs=st.executeQuery(" SELECT `taskID`, `taskName` FROM `task` ");
           ItemCombo cmi;
           
            while (rs.next()) {                
                cmi=new ItemCombo(rs.getInt(1), rs.getString(2));
                map.put(cmi.getKey(),cmi.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }     
        return map;
    }  
}
