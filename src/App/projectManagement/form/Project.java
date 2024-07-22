package App.projectManagement.form;



import static App.projectManagement.form.Dashbord.userNameU;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

        
public class Project extends javax.swing.JFrame {
    
       public void icon(){
              try {
             Image img = ImageIO.read(getClass().getResource("/adminIcons/code.png"));
             this.setIconImage(img);
         } catch (Exception e) {
             
         }
       }  

    String name_project, description_project,manager ;
    Date startDate,endDate;
    static int idProject,userId;
    DefaultTableModel model;
    static ArrayList<String> projetHave=null;
    static String emailProject;
       
    
    
       
     public Project() {    
        initComponents();   
        setProjectdetailToTable();
        bindcomboUser();
        icon();
        setUserInLabel();

        ArrayList<String> permissionData = new Dashbord().permissionData;
        managePermissionLabel(permissionData);
        
      } 
     
    public Project(String email) {
        emailProject=email;
        initComponents();
        bindcomboUser();
        icon();
        setProjectdetailToTable();
        setUserInLabel();
    }
      public void setUserInLabel(){
        usershow.setText(userNameU);
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
            jPanel12.setVisible(true);

        }
        if (per.contains("SubTask hide")) {
            jPanel12.setVisible(false);


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
  

    
     
//    private String email;
//     
//      
//     
//     public Project(String email) {
//    initComponents();
//     setProjectdetailToTable();
//     bindcomboUser();
//     
//     this.email=email;
//         System.out.println(email);
//    
//
//    try (Connection con = DBconnection.getConnection()) {
//        String query = "SELECT p.TaskAppName FROM userp u " +
//                       "INNER JOIN userpermission up ON u.UserID = up.UserID " +
//                       "INNER JOIN permission p ON up.PermissionID = p.PermissionID " +
//                       "WHERE u.UserEmail = ?";
//        try (PreparedStatement prs = con.prepareStatement(query)) {
//            prs.setString(1, email);
//
//            try (ResultSet rs = prs.executeQuery()) {
//                boolean hasDashboardPermission = false;
//                while (rs.next()) {
//                    String permission = rs.getString("TaskAppName");
//                    
//                    if (permission.equals("Project edit") || permission.equals("Project view")) {
//                        hasDashboardPermission = true;
//                        this.setVisible(true);
//                        break;
//                        
//                    } else if (permission.equals("Project hide")) {
//                       // new Project().setVisible(true);
//                       // this.dispose();
//                       hasDashboardPermission = true;
//                        this.setVisible(true);
//                       jPanel5.setVisible(false);
//                       
//                        return; // Exit the constructor early
//                    }
//                }
//
//                if (!hasDashboardPermission) {
//                    JOptionPane.showMessageDialog(this, "You do not have permission to view the dashboard.");
//                    this.dispose();
//                }
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}


    //validaion project if textfiled empty 
    public boolean validateProject() {
    String name_project = txt_nameproject.getText().trim();
    String description_project = txt_description.getText().trim();
        Date startDate = Sdate.getDatoFecha();
        Date endDate = Edate.getDatoFecha();
        Object selectedManager = cmbuser.getSelectedItem();
        String manager = selectedManager != null ? selectedManager.toString().trim() : "";

        if (name_project.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Project Name");
            return false;
        }
        if (description_project.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Project Description");
            return false;
        }
        if (startDate == null) {
            JOptionPane.showMessageDialog(this, "Please Enter Start Date");
            return false;
        }
        if (endDate == null) {
            JOptionPane.showMessageDialog(this, "Please Enter End Date");
            return false;
        }
        if (manager.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a Project Manager");
            return false;
        }

        return true;
    }

    //check duplicate NameProject
    public boolean checkDuplicate() {
        String name = txt_nameproject.getText().trim();
        boolean isExist = false;

        try {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM project WHERE projectName = ?";
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
    
      public String getRoleName(){
         String roleName=null;
         try (Connection con = DBconnection.getConnection()) {
             String query="SELECT r.roleName  FROM userp u , role r WHERE u.RoleID = r.RoleID AND u.UserEmail=?;";
              try (PreparedStatement pst = con.prepareStatement(query)) {       
                pst.setString(1, String.valueOf(emailProject));
                 try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        roleName= rs.getString("roleName");
                    }
                 }catch(Exception e){
                       e.printStackTrace();
                 }
            }             
         }catch (Exception e) {
        e.printStackTrace();
    }
         return roleName;
   }
     

    //show details project in table 
   public void setProjectdetailToTable() {
        String roleName= getRoleName();
    try (Connection con = DBconnection.getConnection()) {
        String query = "SELECT p.projectID, p.projectName, p.projectDescription, p.Startdate, p.ENDdate, u.UserName "
                + "FROM `project` p "
                + "INNER JOIN `userp` u ON u.UserID = p.UserID ";

        if ("Manager".equals(roleName)) {
            query = query + "WHERE u.UserEmail = ?";
        }

        try (PreparedStatement pst = con.prepareStatement(query)) {    
                   if ("Manager".equals(roleName)) {
                    pst.setString(1, String.valueOf(emailProject));
                }
            try (ResultSet rs = pst.executeQuery()) {
                
                while (rs.next()) {
                    int idproject = rs.getInt("projectID");
                    String nameproject = rs.getString("projectName");
                    String descriptionproject = rs.getString("projectDescription");
                    String Startdate = rs.getString("Startdate");
                    String Enddate = rs.getString("ENDdate");
                    String manager = rs.getString("UserName");

                    Object[] obj = {idproject, nameproject, descriptionproject, Startdate, Enddate, manager};
                    model = (DefaultTableModel) tbl_project.getModel();
                    model.addRow(obj);
                }
                }
            }
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    //Add details Project to database 
    public boolean addProject() {
        boolean isAdded = false;

        name_project = txt_nameproject.getText();
        description_project = txt_description.getText();
        startDate = Sdate.getDatoFecha();
        endDate = Edate.getDatoFecha();
        manager = cmbuser.getSelectedItem().toString();

        Long startMillis = startDate.getTime();
        Long endMillis = endDate.getTime();

        java.sql.Date sqlStartDate = new java.sql.Date(startMillis);
        java.sql.Date sqlEndDate = new java.sql.Date(endMillis);

        try {
            Connection con = DBconnection.getConnection();
            DBconnection mq = new DBconnection();
            
            String sql = "insert into project (projectName, projectDescription, Startdate, ENDdate, UserID) values (?, ?, ?, ?, ?)";
            PreparedStatement prs = con.prepareStatement(sql);

            HashMap<String, Integer> map = mq.PopulateCombouser();

            prs.setString(1, name_project);
            prs.setString(2, description_project);
            prs.setDate(3, sqlStartDate);
            prs.setDate(4, sqlEndDate);
            prs.setInt(5, map.get(manager)); // Assuming manager is a key in the map

            int updatedRowCount = prs.executeUpdate();

            if (updatedRowCount > 0) {
                isAdded = true;
            } else {
                isAdded = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }
    
//  public boolean printD(int id) {
//    boolean isPrint = false;
//
//    try {
//        // Load JasperDesign
//        JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\Fichier.jrxml");
//        Connection con = DBconnection.getConnection();
//
//        // Create parameterized query in JRXML
//        String query = "SELECT p.projectName, p.projectDescription, p.Startdate, p.ENDdate, u.UserName " +
//                       "FROM `project` p, userp u " +
//                       "WHERE u.UserID = p.UserID AND p.projectID = " + id + ";";
//
//        // Set the query to JasperDesign
//        JRDesignQuery updateQuery = new JRDesignQuery();
//        updateQuery.setText(query);
//        jdesign.setQuery(updateQuery);
//
//        // Compile the report
//        JasperReport jreport = JasperCompileManager.compileReport(jdesign);
//
//        // Set parameter
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("projectID", id);
//
//        // Fill the report with the parameter and database connection
//        JasperPrint jprint = JasperFillManager.fillReport(jreport, parameters, con);
//
//        // View the report
//        JasperViewer.viewReport(jprint, false);
//        isPrint = true;
//
//        // Close the connection
//        con.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//    return isPrint;
//}



    //Update details to database
    public boolean updateproject(int id) {
        boolean isUpdate = false;

        name_project = txt_nameproject.getText();
        description_project = txt_description.getText();
        startDate = Sdate.getDatoFecha();
        endDate = Edate.getDatoFecha();
        manager = cmbuser.getSelectedItem().toString();

        Long startMillis = startDate.getTime();
        Long endMillis = endDate.getTime();

        java.sql.Date sqlStartDate = new java.sql.Date(startMillis);
        java.sql.Date sqlEndDate = new java.sql.Date(endMillis);

        try {
            Connection con = DBconnection.getConnection();
            
            String sql = "UPDATE `project` SET  `projectName` = ?, `projectDescription` = ?,Startdate=?,ENDdate=?, `UserID` = ? WHERE `project`.`projectID` = " + id + ";";
            PreparedStatement prs = con.prepareStatement(sql);

            prs.setString(1, name_project);
            prs.setString(2, description_project);
            prs.setDate(3, sqlStartDate);
            prs.setDate(4, sqlEndDate);
            prs.setString(5, String.valueOf(userId));

            int updatedRowCount = prs.executeUpdate();
            if (updatedRowCount > 0) {
                isUpdate = true;
            } else {
                isUpdate = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    //delete project to table 
    public boolean deleteProject(int projectId) {
    boolean isDeleted = false;

    try {
        Connection con = DBconnection.getConnection();

        // Delete related rows in the task table first
        String deleteTasksSql = "DELETE FROM task WHERE projectID = ?";
        PreparedStatement deleteTasksStmt = con.prepareStatement(deleteTasksSql);
        deleteTasksStmt.setInt(1, projectId);
        deleteTasksStmt.executeUpdate();

        // Then delete the row in the project table
        String deleteProjectSql = "DELETE FROM project WHERE projectID = ?";
        PreparedStatement deleteProjectStmt = con.prepareStatement(deleteProjectSql);
        deleteProjectStmt.setInt(1, projectId);
        int affectedRows = deleteProjectStmt.executeUpdate();

        if (affectedRows > 0) {
            isDeleted = true;
        }

    } catch (SQLException e) {
                  if (e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(null, "Cannot delete the project because it has associated tasks.", "Foreign Key Constraint", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace(); // For other SQL exceptions, print the stack trace
            }
    }

    return isDeleted;
}

    //clear Table   
     public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
        model.setRowCount(0);
    }
     
     //clear Form
    public void clearForm(){ 
       
        txt_nameproject.setText("");
        txt_description.setText("");
        Sdate.setDatoFecha(null);
        Edate.setDatoFecha(null);
        cmbuser.setSelectedIndex(0);       
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        usershow = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
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
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_project = new rojeru_san.complementos.RSTableMetro();
        jPanel16 = new javax.swing.JPanel();
        Sdate = new rojeru_san.componentes.RSDateChooser();
        Edate = new rojeru_san.componentes.RSDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_description = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        cmbuser = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        btn_add = new rojeru_san.complementos.RSButtonHover();
        btn_delete = new rojeru_san.complementos.RSButtonHover();
        btn_update = new rojeru_san.complementos.RSButtonHover();
        txt_nameproject = new app.bolivia.swing.JCTextField();
        btn_imprimer = new rojeru_san.complementos.RSButtonHover();
        btn_search = new rojerusan.RSButtonMetro();
        btn_all = new rojerusan.RSButtonMetro();
        jLabel28 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Global Technology2.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 210, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 81, 98));
        jLabel4.setText("X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 10, 20, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 81, 98));
        jLabel14.setText("-");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 30, 20));

        usershow.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        usershow.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(usershow, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 20, 70, 30));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/male_user_50px.png"))); // NOI18N
        jLabel2.setText("Welcom , ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, -1, 50));

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

        jPanel5.setBackground(new java.awt.Color(0, 206, 209));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconDash.png"))); // NOI18N
        jLabel6.setText("   Projet");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 260, 60));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 81, 98));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconUsers.png"))); // NOI18N
        jLabel15.setText("   Utilisateur");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 180, -1));

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
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, -1));

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

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 81, 98));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconTask.png"))); // NOI18N
        jLabel12.setText("   Sous-Tâche");
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 180, -1));

        jPanel3.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 640));

        jPanel10.setBackground(new java.awt.Color(248, 248, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_project.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom", "Description", "Démarrer ", "Fin", "Manager"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_project.setColorBackgoundHead(new java.awt.Color(0, 206, 209));
        tbl_project.setColorFilasForeground1(new java.awt.Color(128, 128, 128));
        tbl_project.setColorFilasForeground2(new java.awt.Color(128, 128, 128));
        tbl_project.setColorSelBackgound(new java.awt.Color(0, 81, 98));
        tbl_project.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        tbl_project.setRowHeight(40);
        tbl_project.setShowGrid(false);
        tbl_project.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_projectMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_project);
        if (tbl_project.getColumnModel().getColumnCount() > 0) {
            tbl_project.getColumnModel().getColumn(0).setResizable(false);
            tbl_project.getColumnModel().getColumn(1).setResizable(false);
            tbl_project.getColumnModel().getColumn(2).setResizable(false);
            tbl_project.getColumnModel().getColumn(3).setResizable(false);
            tbl_project.getColumnModel().getColumn(4).setResizable(false);
            tbl_project.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 970, 280));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sdate.setColorBackground(new java.awt.Color(0, 81, 98));
        Sdate.setColorDiaActual(new java.awt.Color(0, 206, 209));
        Sdate.setColorForeground(new java.awt.Color(0, 81, 98));
        Sdate.setPlaceholder("Start Date");
        jPanel16.add(Sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 260, 30));

        Edate.setColorBackground(new java.awt.Color(0, 81, 98));
        Edate.setColorDiaActual(new java.awt.Color(0, 206, 209));
        Edate.setColorForeground(new java.awt.Color(0, 81, 98));
        Edate.setPlaceholder("End Date");
        jPanel16.add(Edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 260, 30));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("Nom");
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, 20));

        txt_description.setBackground(new java.awt.Color(255, 255, 255));
        txt_description.setColumns(20);
        txt_description.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_description.setForeground(new java.awt.Color(0, 81, 98));
        txt_description.setRows(5);
        txt_description.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        jScrollPane3.setViewportView(txt_description);

        jPanel16.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 230, 110));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 81, 98));
        jLabel18.setText("Description");
        jPanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, 20));

        cmbuser.setBackground(new java.awt.Color(255, 255, 255));
        cmbuser.setForeground(new java.awt.Color(0, 81, 98));
        cmbuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbuserActionPerformed(evt);
            }
        });
        jPanel16.add(cmbuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 260, 30));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 81, 98));
        jLabel25.setText("Démarrer le projet");
        jPanel16.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 130, 20));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 81, 98));
        jLabel26.setText("Manager");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 160, 80, 20));

        btn_add.setText("Ajouter");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel16.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 130, -1));

        btn_delete.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete.setText("supprimer");
        btn_delete.setColorHover(new java.awt.Color(255, 51, 51));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel16.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 140, 130, -1));

        btn_update.setBackground(new java.awt.Color(255, 153, 0));
        btn_update.setText("Modifier");
        btn_update.setColorHover(new java.awt.Color(255, 204, 51));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel16.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, 130, -1));

        txt_nameproject.setBackground(new java.awt.Color(255, 255, 255));
        txt_nameproject.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nameproject.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameproject.setPlaceholder("entre project name");
        txt_nameproject.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nameprojectFocusLost(evt);
            }
        });
        jPanel16.add(txt_nameproject, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 230, -1));

        btn_imprimer.setText("Imprimer tout");
        btn_imprimer.setColorHover(new java.awt.Color(255, 51, 51));
        btn_imprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimerActionPerformed(evt);
            }
        });
        jPanel16.add(btn_imprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 200, 200, 30));

        btn_search.setBackground(new java.awt.Color(0, 153, 255));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        btn_search.setText("      Recherche");
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
        jPanel16.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 150, 30));

        btn_all.setText("fichier de projets");
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
        jPanel16.add(btn_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 200, 30));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 81, 98));
        jLabel28.setText("Fin du projet");
        jPanel16.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 110, 20));

        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 970, 240));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Gestion de projet");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel15.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel15.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        jPanel11.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 570));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 970, 570));

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1020, 640));

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
    //get value from table and set textfiled 
    private void tbl_projectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_projectMouseClicked
        int rowN = tbl_project.getSelectedRow();
        TableModel model = tbl_project.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       
        idProject = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameproject.setText( model.getValueAt(rowN, 1).toString()); 
        txt_description.setText( model.getValueAt(rowN, 2).toString()); 
        String startdate = model.getValueAt(rowN, 3).toString();
        String enddate = model.getValueAt(rowN, 4).toString();
       cmbuser.setSelectedItem(model.getValueAt(rowN, 5).toString());
        try {
             startDate = dateFormat.parse(startdate);
             endDate = dateFormat.parse(enddate);        
             Sdate.setDatoFecha(startDate);
             Edate.setDatoFecha(endDate);

        } catch (Exception e) {
          
            e.printStackTrace();          
        }   

    }//GEN-LAST:event_tbl_projectMouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
 new Task().setVisible(true);
 dispose();
       
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
 new User().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
 new Role().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel7MouseClicked
    //combo user add user be in combo
    public void bindcomboUser () {
       DBconnection mq=new DBconnection();
       HashMap<String,Integer> map=mq.PopulateCombouser();
       for(String s :map.keySet()){
           cmbuser.addItem(s);
       }
        
    }
    //combo user 
    private void cmbuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbuserActionPerformed
     DBconnection mq = new DBconnection();
    HashMap<String, Integer> map = mq.PopulateCombouser();
    
    Object selectedManager = cmbuser.getSelectedItem();
    if (selectedManager != null) {
        userId = map.get(selectedManager.toString());
    } 
//        DBconnection mq=new DBconnection();
//        HashMap<String,Integer> map=mq.PopulateCombouser();
//        userId=map.get(cmbuser.getSelectedItem().toString());
    }//GEN-LAST:event_cmbuserActionPerformed
    //Button Create Project
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (validateProject()==true) {
            if(checkDuplicate()==false){
               if (addProject() == true) {
                JOptionPane.showMessageDialog(this, "Project created succesful");
                clearTable();
                setProjectdetailToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Project created failed !");
            }  
            }else{
                JOptionPane.showMessageDialog(this, "This Name Project alreaddy Exist");
            }
           
        }

    }//GEN-LAST:event_btn_addActionPerformed
    //Button Update Project
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
            if (validateProject()==true) {           
            if (updateproject(idProject) == true) {
                JOptionPane.showMessageDialog(this, "Project created succesful");             
               clearTable();
                setProjectdetailToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Project created failed !");
            }
            
           
        }

        
    }//GEN-LAST:event_btn_updateActionPerformed
    //Button Delete Project
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (validateProject()==true) { 
        if (deleteProject(idProject)== true) {
            JOptionPane.showMessageDialog(this, "Project Delete succesful");
            clearTable();
            setProjectdetailToTable();
             clearForm();
        } 
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
 new SubTask().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel12MouseClicked

    private void txt_nameprojectFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nameprojectFocusLost
     
    }//GEN-LAST:event_txt_nameprojectFocusLost

    private void btn_imprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimerActionPerformed
         try {
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\Project.jrxml");
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, DBconnection.con);
            JasperViewer.viewReport(jprint,false);
        } catch (JRException ex) {
       JOptionPane.showMessageDialog(null,ex.getMessage());
        }
                           

    }//GEN-LAST:event_btn_imprimerActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
       
       
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_allActionPerformed

    private void btn_allMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_allMouseClicked
        fProject fichierPr = new fProject();
        fichierPr.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_allMouseClicked

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
        fProject fichierPr = new fProject();
        fichierPr.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_searchMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
       clearForm();
      
    }//GEN-LAST:event_jLabel10MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
 
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
          this.setExtendedState(Project.ICONIFIED);
    }//GEN-LAST:event_jLabel14MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Project.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Project.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Project.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Project.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Project().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.componentes.RSDateChooser Edate;
    private rojeru_san.componentes.RSDateChooser Sdate;
    private rojeru_san.complementos.RSButtonHover btn_add;
    private rojerusan.RSButtonMetro btn_all;
    private rojeru_san.complementos.RSButtonHover btn_delete;
    private rojeru_san.complementos.RSButtonHover btn_imprimer;
    private rojerusan.RSButtonMetro btn_search;
    private rojeru_san.complementos.RSButtonHover btn_update;
    private javax.swing.JComboBox<String> cmbuser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private rojeru_san.complementos.RSTableMetro tbl_project;
    private javax.swing.JTextArea txt_description;
    private app.bolivia.swing.JCTextField txt_nameproject;
    private javax.swing.JLabel usershow;
    // End of variables declaration//GEN-END:variables
}


