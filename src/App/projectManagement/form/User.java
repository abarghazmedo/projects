package App.projectManagement.form;



import java.awt.Image;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.SQLException;


public class User extends javax.swing.JFrame {
    
    public void icon(){
          try {
             Image img = ImageIO.read(getClass().getResource("/adminIcons/code.png"));
             this.setIconImage(img);
         } catch (Exception e) {
         }
    }
    
    HashMap<String,Integer> permissionMap=new HashMap<String,Integer>();
        
    String name_user, email_user ,job_title,password_user,role;
    static int id_user,roleId ;
    DefaultTableModel model;
   
   
    public User() {
        initComponents();
        setUserToTable();
        bindcomboRole();
        icon();
        if (!new Dashbord().permissionData.isEmpty() && new Dashbord().permissionData.size() >0){ 
             ArrayList<String> permissionData = new Dashbord().permissionData;
             if(permissionData.size() >0 ){
                  managePermissionLabel(permissionData);
             }
        
        }
       
                 
    }
    
     public void managePermissionLabel(ArrayList<String> per) {
        System.out.println("permissionData dqsh" + per);
        if (per.contains("Project edit") || per.contains("Project view")) {
            jPanel5.setVisible(true);

        }
        if (per.contains("Project hide")) {
            jPanel5.setVisible(false);

        }
        if (per.contains("Task edit") || per.contains("Task view")) {
  
            jPanel14.setVisible(true);

        }
        if (per.contains("Task hide")) {
            jPanel14.setVisible(false);

        }
        if (per.contains("SubTask edit") || per.contains("SubTask view")) {
            jPanel16.setVisible(true);

        }
        if (per.contains("SubTask hide")) {
            jPanel16.setVisible(false);


        }
        if (per.contains("User edit") || per.contains("User view")) {
            jPanel6.setVisible(true);

        }
        if (per.contains("User hide")) {
            jPanel6.setVisible(false);


        }
        if (per.contains("Role edit") || per.contains("Role view")) {
            jPanel7.setVisible(true);

        }
            System.out.println("permissionData.contains(\"Role hide\")"+ per.contains("Role hide"));
        if (per.contains("Role hide")) {    
            jPanel7.setVisible(false);
        }
    }
    
   
       //validaion user if textfiled empty 
    public boolean validateuser() {
        String name = txt_nameUser.getText().trim();
        String Emaill = txt_emailUser.getText().trim();
        String jobTitle = txt_jobtitle.getText().trim();
        Object selectedrole = cmbrol.getSelectedItem();
        String role = selectedrole != null ? selectedrole.toString().trim() : "";
        String password = txt_passwordUser.getText().trim();
      

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Name !");
            return false;
        }
         if (Emaill.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Email!");
            return false;
        }
        if (jobTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter job title !");
            return false;
        }
        if (role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a role !");
            return false;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Password !");
            return false;
        }
      

        return true;
    }

  
    //get Id Permission
    public int getPermission(String name) {
        int permissionID = -1; // Default value in case no permission is found

        try {
            Connection con = DBconnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT `PermissionID` FROM `permission` WHERE TaskAppName like '%" + name + "%'");

            // Check if there is at least one result
            if (rs.next()) {
                // Retrieve the PermissionID from the ResultSet
                permissionID = rs.getInt("PermissionID");
            }
            //System.out.println(permissionID);

            // Close resources to avoid leaks
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionID;
    }

public int addPermission(int userID, int permissionId) {
    int generatedId = -1; // Default value in case the ID retrieval fails

    try (Connection con = DBconnection.getConnection();
         PreparedStatement prs = con.prepareStatement(
             "INSERT INTO `userpermission` (`UserID`, `PermissionID`) VALUES (?, ?)", 
             Statement.RETURN_GENERATED_KEYS)) {

        prs.setInt(1, userID);
        prs.setInt(2, permissionId);

        // Execute the update
        int affectedRows = prs.executeUpdate();

        // Check if the record was inserted successfully
        if (affectedRows > 0) {
            // Retrieve the auto-generated ID
            try (ResultSet generatedKeys = prs.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return generatedId;
}
   
    public boolean updatePermission(int userID, Integer idper) {
    boolean isUpdated = false;

    // SQL statement to update user permissions
    String sql = "UPDATE `userpermission` SET  `PermissionID` = ? WHERE `UserID` = ?";
   

    try (Connection con = DBconnection.getConnection(); PreparedStatement prs = con.prepareStatement(sql)) {
        // Set parameters for the prepared statement
        prs.setInt(1, idper);
        prs.setInt(2, userID);

        // Execute the update
        int affectedRows = prs.executeUpdate();

        // Check if the record was updated successfully
        if (affectedRows > 0) {
            isUpdated = true;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return isUpdated;
}
     //delete id User and ID permission to dataBase in Table userPermission
     public int deletePermission(int userID, int permissionId) {
    try (Connection con = DBconnection.getConnection();
         PreparedStatement prs = con.prepareStatement("DELETE FROM userpermission WHERE UserID = ? AND PermissionID = ?")) {

        prs.setInt(1, userID);
        prs.setInt(2, permissionId);

        return prs.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return -1;
    }
}
     public void deleteAllPermission(int userId){
          try (Connection con = DBconnection.getConnection();
         PreparedStatement prs = con.prepareStatement("DELETE FROM userpermission WHERE UserID = ? ")) {

        prs.setInt(1, userId);
        prs.execute();
    } catch (Exception e) {
        e.printStackTrace();      
    }
         
     }
     
         //check duplicate NameTask
    public boolean checkDuplicate() {
    String name = txt_nameUser.getText().trim();
    boolean isExist = false;

    try {
        Connection con = DBconnection.getConnection();
        String query = "SELECT * FROM userp WHERE UserName = ?";
        PreparedStatement prs = con.prepareStatement(query);
        prs.setString(1, name);
        ResultSet rs = prs.executeQuery();

        if (rs.next()) {
            isExist = true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

      return isExist;
    }
    
    //show details User in table
    public void setUserToTable() {
        try {
            Connection con = DBconnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT u.UserID,u.UserName,u.UserEmail,u.UserJob,r.roleName,u.UserPassword FROM userp "
                    + "u ,role r WHERE r.RoleID=u.RoleID ORDER BY UserID DESC;");

            while (rs.next()) {
                int idUser = rs.getInt(1);
                String nameUser = rs.getString(2);
                String emailUser = rs.getString(3);
                String jobtitle = rs.getString(4);
                String roleUser = rs.getString(5);
                String passordUser = rs.getString(6);

                Object[] obj = {idUser, nameUser, emailUser, jobtitle, roleUser, passordUser};

                DefaultTableModel model = (DefaultTableModel) tbl_user.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Add details User to database 
    public int addUser() {
        int generatedUserId = -1;

        name_user = txt_nameUser.getText();
        email_user = txt_emailUser.getText();
        job_title = txt_jobtitle.getText();
        role = cmbrole1.getSelectedItem().toString();
        password_user = txt_passwordUser.getText();

        try {
            Connection con = DBconnection.getConnection();
            DBconnection mq = new DBconnection();

            String sql = "INSERT INTO userp (UserName, UserEmail, UserJob, RoleID, UserPassword) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement prs = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            HashMap<String, Integer> roleMap = mq.PopulateComboRole();
            //  Integer roleId = roleMap.get(role);

            prs.setString(1, name_user);
            prs.setString(2, email_user);
            prs.setString(3, job_title);
            prs.setInt(4, roleMap.get(role));
            prs.setString(5, password_user);

            int updatedRowCount = prs.executeUpdate();

            if (updatedRowCount > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = prs.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedUserId = generatedKeys.getInt(1);
                }
            }

            // After adding the user, get user and assign it to the permission
            for (Map.Entry<String, Integer> p : permissionMap.entrySet()) {
                Integer idPerm = p.getValue();
                addPermission(generatedUserId, idPerm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    //    System.out.println(generatedUserId);
        return generatedUserId;
    }

    //update user to table 
    
     public boolean updateUser(int id) {
      boolean isUpdate = false;
      //  id_user = Integer.parseInt(txt_idUser.getText());
        name_user = txt_nameUser.getText();
        email_user = txt_emailUser.getText();
        job_title=txt_jobtitle.getText();
        cmbrole1.getSelectedItem().toString();
        password_user=txt_passwordUser.getText();
    
        try {
            Connection con = DBconnection.getConnection();
            String sql = "UPDATE userp SET UserName=?, UserEmail=?, UserJob=?, RoleID=?,  UserPassword=?  WHERE UserID="+id+";";
            PreparedStatement prs = con.prepareStatement(sql);

            
            prs.setString(1, name_user);
            prs.setString(2, email_user);
            prs.setString(3, job_title);
            prs.setString(4, String.valueOf(roleId));      
            prs.setString(5, password_user);
           

            int updatedRowCount = prs.executeUpdate();
            if (updatedRowCount > 0) {
                isUpdate = true;
            } else {
                isUpdate = false;
            }
           
            deleteAllPermission(id);
            for (Map.Entry<String, Integer> p : permissionMap.entrySet()) {
                Integer idPerm = p.getValue();
                addPermission(id, idPerm);            
                System.out.println("id "+id );
                System.out.println("Id Per"+idPerm);
                
            }

           
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdate;
    }
   
    //delete user to table      
 public boolean deleteUser(int id) {
    try (Connection con = DBconnection.getConnection()) {
        con.setAutoCommit(false);
        
        String[] tables = {"subtask", "userpermission", "task"};
        for (String table : tables) {
            String deleteSQL = "DELETE FROM " + table + " WHERE UserID = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            deleteStmt.close();
        }

        String deleteUserSQL = "DELETE FROM userp WHERE UserID = ?";
        PreparedStatement deleteUserStmt = con.prepareStatement(deleteUserSQL);
        deleteUserStmt.setInt(1, id);
        int updatedRowCount = deleteUserStmt.executeUpdate();

        con.commit();
        return updatedRowCount > 0;
    } catch (SQLException e) {
                  if (e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(null, "Cannot delete User because it has associated Project or tasks.", "Foreign Key Constraint", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace(); // For other SQL exceptions, print the stack trace
            }
    }
        return false;
    
}
  
    //clear table 
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_user.getModel();
        model.setRowCount(0);
    }
    //clear form
    public void clearform(){
        txt_nameUser.setText("");
        txt_emailUser.setText("");
         txt_jobtitle.setText("");
        txt_passwordUser.setText("");
        cmbrole1.setSelectedItem("");  
        cmbPrj.setSelectedItem("");
        cmbPrj.setSelectedItem("");
        cmbTask.setSelectedItem("");
        cmbSubtsk.setSelectedItem("");
        cmbUser.setSelectedItem("");
        cmbrol.setSelectedItem("");
        
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_user = new rojeru_san.complementos.RSTableMetro();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btn_delete1 = new rojeru_san.complementos.RSButtonHover();
        btn_add1 = new rojeru_san.complementos.RSButtonHover();
        btn_update1 = new rojeru_san.complementos.RSButtonHover();
        jLabel18 = new javax.swing.JLabel();
        cmbrole1 = new javax.swing.JComboBox<>();
        cmbSubtsk = new javax.swing.JComboBox<>();
        cmbPrj = new javax.swing.JComboBox<>();
        cmbUser = new javax.swing.JComboBox<>();
        cmbTask = new javax.swing.JComboBox<>();
        cmbrol = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_passwordUser = new rojerusan.RSPasswordTextPlaceHolder();
        txt_nameUser = new app.bolivia.swing.JCTextField();
        txt_jobtitle = new app.bolivia.swing.JCTextField();
        txt_emailUser = new app.bolivia.swing.JCTextField();
        btn_imprimer = new rojeru_san.complementos.RSButtonHover();
        btn_search = new rojerusan.RSButtonMetro();
        btn_all = new rojerusan.RSButtonMetro();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 206, 209));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_menu_48px_1.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 5, 50));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/male_user_50px.png"))); // NOI18N
        jLabel2.setText("Welcom ,Admin");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 10, 170, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 81, 98));
        jLabel4.setText("X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 20, 20, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Global Technology2.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 210, 50));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("-");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 20, 20, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 70));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 81, 98));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icon dashbord blue.png"))); // NOI18N
        jLabel8.setText("   Tableau de bord");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 260, 60));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 81, 98));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/ProejctIcon.png"))); // NOI18N
        jLabel6.setText("   Projet");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 260, 60));

        jPanel6.setBackground(new java.awt.Color(0, 206, 209));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconUsr.png"))); // NOI18N
        jLabel15.setText("   Utilisateur");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, -1));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 260, 60));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 81, 98));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/RoleP.png"))); // NOI18N
        jLabel9.setText("   Role");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, -1));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 260, 60));

        jPanel9.setBackground(new java.awt.Color(0, 81, 98));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Exit_26px.png"))); // NOI18N
        jLabel5.setText("   Se déconnecter");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, -1));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 260, 50));

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, -1, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 81, 98));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconTask.png"))); // NOI18N
        jLabel7.setText("   Tâche");
        jPanel14.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, -1));

        jPanel3.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 260, 60));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 81, 98));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconTask.png"))); // NOI18N
        jLabel12.setText("   Sous-Tâche");
        jPanel16.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, -1));

        jPanel3.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 700));

        jPanel10.setBackground(new java.awt.Color(248, 248, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom", "E-mail", "Profil", "Role", "Mot de passe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_user.setColorBackgoundHead(new java.awt.Color(0, 206, 209));
        tbl_user.setColorFilasForeground1(new java.awt.Color(128, 128, 128));
        tbl_user.setColorFilasForeground2(new java.awt.Color(128, 128, 128));
        tbl_user.setColorSelBackgound(new java.awt.Color(0, 81, 98));
        tbl_user.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        tbl_user.setRowHeight(40);
        tbl_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_userMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_user);
        if (tbl_user.getColumnModel().getColumnCount() > 0) {
            tbl_user.getColumnModel().getColumn(0).setResizable(false);
            tbl_user.getColumnModel().getColumn(1).setResizable(false);
            tbl_user.getColumnModel().getColumn(2).setResizable(false);
            tbl_user.getColumnModel().getColumn(3).setResizable(false);
            tbl_user.getColumnModel().getColumn(4).setResizable(false);
            tbl_user.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel11.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 970, 250));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 81, 98));
        jLabel14.setText("Projet");
        jPanel17.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 90, 20));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 81, 98));
        jLabel17.setText("Profil");
        jPanel17.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, 20));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 81, 98));
        jLabel29.setText("Role");
        jPanel17.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 80, 20));

        btn_delete1.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete1.setText("supprimer");
        btn_delete1.setColorHover(new java.awt.Color(255, 51, 51));
        btn_delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete1ActionPerformed(evt);
            }
        });
        jPanel17.add(btn_delete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 150, 130, 40));

        btn_add1.setText("Ajouter");
        btn_add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add1ActionPerformed(evt);
            }
        });
        jPanel17.add(btn_add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 130, 40));

        btn_update1.setBackground(new java.awt.Color(255, 153, 0));
        btn_update1.setText("Modifier");
        btn_update1.setColorHover(new java.awt.Color(255, 204, 51));
        btn_update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update1ActionPerformed(evt);
            }
        });
        jPanel17.add(btn_update1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, 130, 40));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 81, 98));
        jLabel18.setText("E-mail");
        jPanel17.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 90, 20));

        cmbrole1.setBackground(new java.awt.Color(255, 255, 255));
        cmbrole1.setForeground(new java.awt.Color(0, 81, 98));
        cmbrole1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbrole1ActionPerformed(evt);
            }
        });
        jPanel17.add(cmbrole1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 230, 30));

        cmbSubtsk.setBackground(new java.awt.Color(255, 255, 255));
        cmbSubtsk.setForeground(new java.awt.Color(0, 81, 98));
        cmbSubtsk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SubTask view", "SubTask edit", "SubTask hide" }));
        cmbSubtsk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSubtskActionPerformed(evt);
            }
        });
        jPanel17.add(cmbSubtsk, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 120, 20));

        cmbPrj.setBackground(new java.awt.Color(255, 255, 255));
        cmbPrj.setForeground(new java.awt.Color(0, 81, 98));
        cmbPrj.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Project view", "Project edit", "Project hide" }));
        cmbPrj.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbPrjItemStateChanged(evt);
            }
        });
        cmbPrj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPrjActionPerformed(evt);
            }
        });
        jPanel17.add(cmbPrj, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 120, 20));

        cmbUser.setBackground(new java.awt.Color(255, 255, 255));
        cmbUser.setForeground(new java.awt.Color(0, 81, 98));
        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User view", "User edit", "User hide" }));
        cmbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserActionPerformed(evt);
            }
        });
        jPanel17.add(cmbUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 110, 20));

        cmbTask.setBackground(new java.awt.Color(255, 255, 255));
        cmbTask.setForeground(new java.awt.Color(0, 81, 98));
        cmbTask.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Task view", "Task edit", "Task hide" }));
        cmbTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTaskActionPerformed(evt);
            }
        });
        jPanel17.add(cmbTask, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 120, 20));

        cmbrol.setBackground(new java.awt.Color(255, 255, 255));
        cmbrol.setForeground(new java.awt.Color(0, 81, 98));
        cmbrol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Role view", "Role edit", "Role hide" }));
        cmbrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbrolActionPerformed(evt);
            }
        });
        jPanel17.add(cmbrol, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 110, 20));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 81, 98));
        jLabel16.setText("Nom");
        jPanel17.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, 20));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 81, 98));
        jLabel19.setText("Mot de passe ");
        jPanel17.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 110, 20));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 81, 98));
        jLabel20.setText("Sous-Tâche");
        jPanel17.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 110, 20));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 81, 98));
        jLabel21.setText("Utilisateur");
        jPanel17.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 80, 20));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 81, 98));
        jLabel22.setText("Role");
        jPanel17.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 60, 20));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 81, 98));
        jLabel25.setText("Tâche");
        jPanel17.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 90, 20));

        txt_passwordUser.setBackground(new java.awt.Color(255, 255, 255));
        txt_passwordUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_passwordUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_passwordUser.setPhColor(new java.awt.Color(0, 0, 0));
        txt_passwordUser.setPlaceholder("Entrer le mot de passe");
        jPanel17.add(txt_passwordUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 130, 30));

        txt_nameUser.setBackground(new java.awt.Color(255, 255, 255));
        txt_nameUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nameUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameUser.setPlaceholder("entre le nom complet ");
        jPanel17.add(txt_nameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 230, -1));

        txt_jobtitle.setBackground(new java.awt.Color(255, 255, 255));
        txt_jobtitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_jobtitle.setForeground(new java.awt.Color(0, 81, 98));
        txt_jobtitle.setPlaceholder("entrez le profil");
        jPanel17.add(txt_jobtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 230, -1));

        txt_emailUser.setBackground(new java.awt.Color(255, 255, 255));
        txt_emailUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_emailUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_emailUser.setPlaceholder("entre l'email");
        txt_emailUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailUserActionPerformed(evt);
            }
        });
        jPanel17.add(txt_emailUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 230, -1));

        btn_imprimer.setText("Imprimer tout");
        btn_imprimer.setColorHover(new java.awt.Color(255, 51, 51));
        btn_imprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimerActionPerformed(evt);
            }
        });
        jPanel17.add(btn_imprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 220, 190, 30));

        btn_search.setBackground(new java.awt.Color(0, 153, 255));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        btn_search.setText("Recherche");
        btn_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_searchMouseClicked(evt);
            }
        });
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel17.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 160, 30));

        btn_all.setText("Fichier de projets");
        btn_all.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_allMouseClicked(evt);
            }
        });
        btn_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_allActionPerformed(evt);
            }
        });
        jPanel17.add(btn_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 220, 190, 30));

        jPanel11.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 970, 270));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Gestion des utilisateurs");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel11.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 970, 570));

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1020, 700));

        setSize(new java.awt.Dimension(1279, 711));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
        
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
 new Task().setVisible(true);
 dispose();
       
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
 new Project().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
 new Role().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void tbl_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_userMouseClicked
        int rowN = tbl_user.getSelectedRow();
        TableModel model = tbl_user.getModel();
        
        id_user=Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameUser.setText(model.getValueAt(rowN, 1).toString());
        txt_emailUser.setText(model.getValueAt(rowN, 2).toString());
        txt_jobtitle.setText(model.getValueAt(rowN, 3).toString());
        cmbrole1.setSelectedItem(model.getValueAt(rowN, 4).toString()); 
        txt_passwordUser.setText(model.getValueAt(rowN, 5).toString());
        
    }//GEN-LAST:event_tbl_userMouseClicked
    public void bindcomboRole() { 
        DBconnection mq = new DBconnection();
        HashMap<String, Integer> map = mq.PopulateComboRole();
        for (String s : map.keySet()) {
            cmbrole1.addItem(s);
        }
    }
    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
new SubTask().setVisible(true);
dispose();
    }//GEN-LAST:event_jPanel16MouseClicked

    private void btn_delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete1ActionPerformed
       if (validateuser()==true) {
        if (deleteUser(id_user) == true) {
            JOptionPane.showMessageDialog(this, "User Delet succesful");
            clearTable();
            clearform();
            setUserToTable();
        } 
       }
    }//GEN-LAST:event_btn_delete1ActionPerformed

    private void btn_add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add1ActionPerformed
        setPermision();
        if (validateuser()==true) {
        if(checkDuplicate()==false){
        if (addUser() > 0) {
        JOptionPane.showMessageDialog(this, "User created successfully");
        clearTable();
        setUserToTable();
        clearform();
    } else {
        JOptionPane.showMessageDialog(this, "User creation failed!");
    }
        }else{
                JOptionPane.showMessageDialog(this, "This Name alreaddy Exist");
            }
        }
    }//GEN-LAST:event_btn_add1ActionPerformed

    public void setPermision(){
        int idPrj = getPermission(String.valueOf(cmbPrj.getSelectedItem()));
        permissionMap.put("PROJECT_PERMISSION", idPrj);
        int idTsk = getPermission(String.valueOf(cmbTask.getSelectedItem()));
        permissionMap.put("TASK_PERMISSION", idTsk);
        int idSubTask = getPermission(String.valueOf(cmbSubtsk.getSelectedItem()));
        permissionMap.put("SUBTASK_PERMISSION", idSubTask);
        int idUsr = getPermission(String.valueOf(cmbUser.getSelectedItem()));
        permissionMap.put("USER_PERMISSION", idUsr);
        int idRl = getPermission(String.valueOf(cmbrol.getSelectedItem()));
        permissionMap.put("ROLE_PERMISSION", idRl);
        
        
    }
    private void btn_update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update1ActionPerformed
           setPermision();      
       if (validateuser()==true) {
        if (updateUser(id_user) == true) {
            JOptionPane.showMessageDialog(this, "User Update succesful");
            clearTable();
            clearform();
            setUserToTable();
        } else {
            JOptionPane.showMessageDialog(this, "User update failed !");
        }
        
        }
    }//GEN-LAST:event_btn_update1ActionPerformed

    private void cmbrole1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbrole1ActionPerformed
        DBconnection mq=new DBconnection();
        HashMap<String,Integer> map=mq.PopulateComboRole();
        roleId=map.get(cmbrole1.getSelectedItem().toString());
    }//GEN-LAST:event_cmbrole1ActionPerformed

    private void cmbSubtskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSubtskActionPerformed
 
    }//GEN-LAST:event_cmbSubtskActionPerformed
    
    private void cmbPrjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPrjActionPerformed

    }//GEN-LAST:event_cmbPrjActionPerformed

    private void cmbUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserActionPerformed

    }//GEN-LAST:event_cmbUserActionPerformed

    private void cmbTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTaskActionPerformed

    }//GEN-LAST:event_cmbTaskActionPerformed

    private void cmbrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbrolActionPerformed

    }//GEN-LAST:event_cmbrolActionPerformed

    private void cmbPrjItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbPrjItemStateChanged
       
    }//GEN-LAST:event_cmbPrjItemStateChanged

    private void btn_imprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimerActionPerformed
        try {
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\Users.jrxml");

            //            String query = "select ID_Adhr,Nom_Adhr+' '+Prenom_Adhr as 'Nom Prenom',Adresse_Adhr,Tel_Adhr,CIN_Adhr from Adherent where CIN_Adhr like '" + cin + "%';";
            //
            //            JRDesignQuery updateQuery = new JRDesignQuery();
            //            updateQuery.setText(query);
            //
            //            jdesign.setQuery(updateQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, DBconnection.con);
            JasperViewer.viewReport(jprint, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btn_imprimerActionPerformed

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
       Login login=new Login();
       login.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void btn_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_searchMouseClicked
        fichierUsers fichierUsers = new fichierUsers();
        fichierUsers.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_searchMouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed

    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_allMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_allMouseClicked
        fichierUsers fichierUsers = new fichierUsers();
        fichierUsers.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_allMouseClicked

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_allActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        clearform();

    }//GEN-LAST:event_jLabel10MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
//       new Dashbord().managePermission();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void txt_emailUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailUserActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
   this.setExtendedState(User.ICONIFIED);    }//GEN-LAST:event_jLabel13MouseClicked
   
 
   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new User().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.RSButtonHover btn_add1;
    private rojerusan.RSButtonMetro btn_all;
    private rojeru_san.complementos.RSButtonHover btn_delete1;
    private rojeru_san.complementos.RSButtonHover btn_imprimer;
    private rojerusan.RSButtonMetro btn_search;
    private rojeru_san.complementos.RSButtonHover btn_update1;
    private javax.swing.JComboBox<String> cmbPrj;
    private javax.swing.JComboBox<String> cmbSubtsk;
    private javax.swing.JComboBox<String> cmbTask;
    private javax.swing.JComboBox<String> cmbUser;
    private javax.swing.JComboBox<String> cmbrol;
    private javax.swing.JComboBox<String> cmbrole1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_user;
    private app.bolivia.swing.JCTextField txt_emailUser;
    private app.bolivia.swing.JCTextField txt_jobtitle;
    private app.bolivia.swing.JCTextField txt_nameUser;
    private rojerusan.RSPasswordTextPlaceHolder txt_passwordUser;
    // End of variables declaration//GEN-END:variables
}
