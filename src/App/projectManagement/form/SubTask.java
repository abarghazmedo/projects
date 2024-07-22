package App.projectManagement.form;


import static App.projectManagement.form.Dashbord.userNameU;
import static App.projectManagement.form.Task.emailtasks;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class SubTask extends javax.swing.JFrame {
    
    public void icon(){
         try {
             Image img = ImageIO.read(getClass().getResource("/adminIcons/code.png"));
             this.setIconImage(img);
         } catch (Exception e) {
         }
    }

    String namesubTask, descriptionsubTask, assignetosubTask, Task, prioritysubTask, statutsubTask, typesubTask;
    static int idsubTask, userId, taskId, Estimer;
    DefaultTableModel model;
 
    static String emailSubTasks;

    public SubTask() {
        initComponents();
        setsubTaskToTable();
        bindcombousers();
        bindcombotask();
        icon();
         ArrayList<String> permissionData = new Dashbord().permissionData;
        managePermissionLabel(permissionData);
        setUserInLabel();
       
    }
    
    public SubTask(String email){
        emailSubTasks=email;
        initComponents();      
        bindcombousers();
        bindcombotask();
        icon();
        setsubTaskToTable();
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
    


    //validaion sub task if textfiled empty 
    public boolean validateSubtask() {
        String namSubTask = txt_nametask.getText().trim();
        String description_task = txt_descriptiontask.getText().trim();
        Object selecteduser = cmbuser.getSelectedItem();
        String users = selecteduser != null ? selecteduser.toString().trim() : "";
        Object selectedtask = cmbtask.getSelectedItem();
        String task = selectedtask != null ? selecteduser.toString().trim() : "";
        Object selectedpriority = cmbpriority.getSelectedItem();
        String priority_Task = selecteduser != null ? selecteduser.toString().trim() : "";
        Object selectedstatut = cmbstatut.getSelectedItem();
        String statut_Task = selectedstatut != null ? selecteduser.toString().trim() : "";
        Object selectedtype = cmbtype.getSelectedItem();
        String type_task = selectedtype != null ? selecteduser.toString().trim() : "";

        if (namSubTask.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Task Name !");
            return false;
        }
        if (description_task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Task Description !");
            return false;
        }
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a devloper !");
            return false;
        }
        if (task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a Task !");
            return false;
        }
        if (priority_Task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a Priority !");
            return false;
        }
        if (statut_Task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a Statut !");
            return false;
        }
        if (type_task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a Type !");
            return false;
        }

        return true;
    }
    
    
    
    
         public String getRoleName(){
         String roleName=null;
         try (Connection con = DBconnection.getConnection()) {
             String query="SELECT r.roleName  FROM userp u , role r WHERE u.RoleID = r.RoleID AND u.UserEmail=?;";
              try (PreparedStatement pst = con.prepareStatement(query)) {       
                pst.setString(1, String.valueOf(emailtasks));
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
   //show data in table
public void setsubTaskToTable() {
    String roleName = getRoleName();
    model = (DefaultTableModel) tbl_subtask.getModel(); // Assuming tbl_task is your JTable for tasks

    try (Connection con = DBconnection.getConnection()) {
        String query = "SELECT s.subtaskID, s.subtaskName, s.subtaskDescription, s.userID AS UserIDtask, u.UserName, u.UserEmail, " +
                       "t.userID AS UserIDtask, t.taskName, s.Estimer, s.subtaskPriority, s.subtaskStatut, s.subtaskType " +
                       "FROM subtask s " +
                       "INNER JOIN task t ON t.taskID = s.taskID " +
                       "INNER JOIN userp u ON s.userID = u.UserID ";

        if ("Manager".equals(roleName)) {
            query = query + "WHERE u.UserID = (SELECT UserID FROM userp u WHERE u.UserEmail=?)";
        }

        if ("Developer".equals(roleName)) {
            query = query + "WHERE u.UserEmail=?";
        }
        
        try (PreparedStatement pst = con.prepareStatement(query)) {
            if ("Manager".equals(roleName) || "Developer".equals(roleName)) {
                pst.setString(1, String.valueOf(emailtasks));
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int idsubtask = rs.getInt("subtaskID");
                    String namesubtask = rs.getString("subtaskName");
                    String descriptionsubtask = rs.getString("subtaskDescription");
                    String assinetosubtask = rs.getString("UserName");
                    String tasksubtask = rs.getString("taskName");
                    int estimersubtask = rs.getInt("Estimer");
                    String prioritysubtask = rs.getString("subtaskPriority");
                    String statutsubtask = rs.getString("subtaskStatut");
                    String typesubtask = rs.getString("subtaskType");

                    Object[] obj = {idsubtask, namesubtask, descriptionsubtask, assinetosubtask, tasksubtask, estimersubtask, prioritysubtask, statutsubtask, typesubtask};
                    model.addRow(obj);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    

    //Add Subtask to table data  
    public boolean addsubTask() {
        boolean isAdded = false;
        namesubTask = txt_nametask.getText();
        descriptionsubTask = txt_descriptiontask.getText();
        assignetosubTask = cmbuser.getSelectedItem().toString();
        Task = cmbtask.getSelectedItem().toString();
        Estimer = ((SpinnerNumberModel) cmbEstimer.getModel()).getNumber().intValue();
        prioritysubTask = cmbpriority.getSelectedItem().toString();
        statutsubTask = cmbstatut.getSelectedItem().toString();
        typesubTask = cmbtype.getSelectedItem().toString();

        try {
            Connection con = DBconnection.getConnection();
            DBconnection mq = new DBconnection();

            HashMap<String, Integer> mapUsers = mq.PopulateCombousers();
            HashMap<String, Integer> mapTask = mq.PopulateCombotask();

            Integer userId = mapUsers.get(assignetosubTask);
            Integer taskId = mapTask.get(Task);

            String sql = "INSERT INTO `subtask`( subtaskName, subtaskDescription, `userID`,`taskID`, Estimer, subtaskPriority, subtaskStatut, subtaskType) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(sql);

            prs.setString(1, namesubTask);
            prs.setString(2, descriptionsubTask);
            prs.setString(3, String.valueOf(userId));
            prs.setString(4, String.valueOf(taskId));
            prs.setInt(5, (Estimer));
            prs.setString(6, prioritysubTask);
            prs.setString(7, statutsubTask);
            prs.setString(8, typesubTask);

            int updatedrowcont = prs.executeUpdate();
            if (updatedrowcont > 0) {
                isAdded = true;
            } else {
                isAdded = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    //update SubTask to table data
    public boolean updateSubTask(int id) {
        boolean isUpdate = false;
        namesubTask = txt_nametask.getText();
        descriptionsubTask = txt_descriptiontask.getText();
        assignetosubTask = cmbuser.getSelectedItem().toString();
        Task = cmbtask.getSelectedItem().toString();
        Estimer = ((SpinnerNumberModel) cmbEstimer.getModel()).getNumber().intValue();
        prioritysubTask = cmbpriority.getSelectedItem().toString();
        statutsubTask = cmbstatut.getSelectedItem().toString();
        typesubTask = cmbtype.getSelectedItem().toString();

        try {
            Connection con = DBconnection.getConnection();
            String sql = "UPDATE subtask SET subtaskName=?, subtaskDescription=?, userID=?, taskID=?, Estimer=?, subtaskPriority=?, subtaskStatut=?,  subtaskType=? WHERE subtaskID =" + id + ";";
            PreparedStatement prs = con.prepareStatement(sql);

            prs.setString(1, namesubTask);
            prs.setString(2, descriptionsubTask);
            prs.setString(3, String.valueOf(userId));
            prs.setString(4, String.valueOf(taskId));
            prs.setInt(5, Estimer);
            prs.setString(6, prioritysubTask);
            prs.setString(7, statutsubTask);
            prs.setString(8, typesubTask);

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

    //delete task to table 
    public boolean deletesubTask(int id) {
        boolean isDelete = false;

        try {
            Connection con = DBconnection.getConnection();
            String sql = "DELETE FROM subtask WHERE subtaskID=" + id + ";";
            PreparedStatement prs = con.prepareStatement(sql);

            int updatedrowcont = prs.executeUpdate();
            if (updatedrowcont > 0) {
                isDelete = true;
            } else {
                isDelete = false;
            }

        } catch (SQLException e) {
                  if (e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(null, "Cannot delete SubTask because it has associated Task.", "Foreign Key Constraint", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace(); // For other SQL exceptions, print the stack trace
            }
    }
        return isDelete;

    }

    //clear table 
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_subtask.getModel();
        model.setRowCount(0);
    }

    //clear formulair
    public void clearform() {
        txt_nametask.setText("");
        txt_descriptiontask.setText("");
        cmbuser.setSelectedIndex(0);
        cmbtask.setSelectedIndex(0);
        cmbEstimer.setValue(0);
        cmbpriority.setSelectedIndex(0);
        cmbstatut.setSelectedIndex(0);
        cmbtype.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_subtask = new rojeru_san.complementos.RSTableMetro();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_descriptiontask = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cmbuser = new javax.swing.JComboBox<>();
        cmbtask = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        cmbpriority = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cmbstatut = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cmbtype = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        btn_delete = new rojeru_san.complementos.RSButtonHover();
        btn_add = new rojeru_san.complementos.RSButtonHover();
        btn_update = new rojeru_san.complementos.RSButtonHover();
        cmbEstimer = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_nametask = new app.bolivia.swing.JCTextField();
        btn_imprimer = new rojeru_san.complementos.RSButtonHover();
        btn_all = new rojerusan.RSButtonMetro();
        btn_search = new rojerusan.RSButtonMetro();
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

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 81, 98));
        jLabel14.setText("-");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 20, 40));

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
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, -1));

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

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 81, 98));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconTask.png"))); // NOI18N
        jLabel12.setText("   Tâche");
        jPanel14.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, -1));

        jPanel3.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 260, 60));

        jPanel12.setBackground(new java.awt.Color(0, 206, 209));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconTas.png"))); // NOI18N
        jLabel7.setText("   Sous-Tâche");
        jPanel12.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, -1));

        jPanel3.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 700));

        jPanel10.setBackground(new java.awt.Color(248, 248, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_subtask.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom", "Description", "affecter à", "Tache", "Estimer", "Priority", "Statut", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_subtask.setColorBackgoundHead(new java.awt.Color(0, 206, 209));
        tbl_subtask.setColorFilasForeground1(new java.awt.Color(128, 128, 128));
        tbl_subtask.setColorFilasForeground2(new java.awt.Color(128, 128, 128));
        tbl_subtask.setColorSelBackgound(new java.awt.Color(0, 81, 98));
        tbl_subtask.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        tbl_subtask.setRowHeight(40);
        tbl_subtask.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_subtaskMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_subtask);
        if (tbl_subtask.getColumnModel().getColumnCount() > 0) {
            tbl_subtask.getColumnModel().getColumn(0).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(1).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(2).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(3).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(4).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(5).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(6).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(7).setResizable(false);
            tbl_subtask.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 230));

        jPanel11.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 970, 250));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("Nom ");
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 20));

        txt_descriptiontask.setBackground(new java.awt.Color(255, 255, 255));
        txt_descriptiontask.setColumns(20);
        txt_descriptiontask.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_descriptiontask.setForeground(new java.awt.Color(0, 81, 98));
        txt_descriptiontask.setRows(5);
        txt_descriptiontask.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        jScrollPane2.setViewportView(txt_descriptiontask);

        jPanel16.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 230, 100));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 81, 98));
        jLabel17.setText("Description");
        jPanel16.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 100, 20));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 81, 98));
        jLabel25.setText("affecter à");
        jPanel16.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 90, 30));

        cmbuser.setBackground(new java.awt.Color(255, 255, 255));
        cmbuser.setForeground(new java.awt.Color(0, 81, 98));
        cmbuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbuserActionPerformed(evt);
            }
        });
        jPanel16.add(cmbuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 230, 30));

        cmbtask.setBackground(new java.awt.Color(255, 255, 255));
        cmbtask.setForeground(new java.awt.Color(0, 81, 98));
        cmbtask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbtaskActionPerformed(evt);
            }
        });
        jPanel16.add(cmbtask, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 230, 30));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 81, 98));
        jLabel22.setText("Heure");
        jPanel16.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, 50, 20));

        cmbpriority.setBackground(new java.awt.Color(255, 255, 255));
        cmbpriority.setForeground(new java.awt.Color(0, 81, 98));
        cmbpriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P0", "P1", "P2", " " }));
        jPanel16.add(cmbpriority, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 230, 30));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 81, 98));
        jLabel24.setText("Priorité");
        jPanel16.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 90, 20));

        cmbstatut.setBackground(new java.awt.Color(255, 255, 255));
        cmbstatut.setForeground(new java.awt.Color(0, 81, 98));
        cmbstatut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "To do", "In progress", "In test", "Done" }));
        jPanel16.add(cmbstatut, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 130, 230, 30));

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 81, 98));
        jLabel23.setText("Statut");
        jPanel16.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 90, 20));

        cmbtype.setBackground(new java.awt.Color(255, 255, 255));
        cmbtype.setForeground(new java.awt.Color(0, 81, 98));
        cmbtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Evolution", "Bug" }));
        jPanel16.add(cmbtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 230, 30));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 81, 98));
        jLabel19.setText("Type");
        jPanel16.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 90, 20));

        btn_delete.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete.setText("supprimer");
        btn_delete.setColorHover(new java.awt.Color(255, 51, 51));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel16.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 130, 40));

        btn_add.setText("Ajouter");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel16.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 130, 40));

        btn_update.setBackground(new java.awt.Color(255, 153, 0));
        btn_update.setText("Modifier");
        btn_update.setColorHover(new java.awt.Color(255, 204, 51));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel16.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 90, 130, 40));
        jPanel16.add(cmbEstimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 70, -1));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 81, 98));
        jLabel26.setText("Tâche");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 90, 20));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 81, 98));
        jLabel27.setText("Estimations");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 90, 20));

        txt_nametask.setBackground(new java.awt.Color(255, 255, 255));
        txt_nametask.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nametask.setForeground(new java.awt.Color(0, 81, 98));
        txt_nametask.setPlaceholder("entre sutask name");
        jPanel16.add(txt_nametask, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 230, -1));

        btn_imprimer.setText("Imprimer tout");
        btn_imprimer.setColorHover(new java.awt.Color(255, 51, 51));
        btn_imprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimerActionPerformed(evt);
            }
        });
        jPanel16.add(btn_imprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, 180, 30));

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
        jPanel16.add(btn_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 180, 30));

        btn_search.setBackground(new java.awt.Color(0, 153, 255));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        btn_search.setText("     Recherche");
        btn_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_searchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_searchMouseEntered(evt);
            }
        });
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel16.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 140, 30));

        jPanel11.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 970, 270));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Gestion des sous-tâches ");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel11.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 970, 570));

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

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
 new User().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
 new Role().setVisible(true);
 dispose();
    }//GEN-LAST:event_jPanel7MouseClicked
    //Button Create SubTask
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (validateSubtask() == true) {
            if (addsubTask() == true) {
                JOptionPane.showMessageDialog(this, "Task created succesful");
                clearTable();
                clearform();
                setsubTaskToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Task created failed !");
            }
        }
    }//GEN-LAST:event_btn_addActionPerformed
    //Button Update SubTask
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
     if (validateSubtask() == true) {
        if (updateSubTask(idsubTask) == true) {
            JOptionPane.showMessageDialog(this, "Task Update succesful");
            clearTable();
            clearform();
            setsubTaskToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Task update failed !");
        }
     }
    }//GEN-LAST:event_btn_updateActionPerformed
    //Button Delete SubTask
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (validateSubtask() == true) {
        if (deletesubTask(idsubTask) == true) {
            JOptionPane.showMessageDialog(this, "Task Delet succesful");
            clearTable();
            clearform();
            setsubTaskToTable();
        }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed
    //get value from table and set textfiled 
    private void tbl_subtaskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_subtaskMouseClicked
        int rowN = tbl_subtask.getSelectedRow();
        TableModel model = tbl_subtask.getModel();

        idsubTask = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nametask.setText(model.getValueAt(rowN, 1).toString());
        txt_descriptiontask.setText(model.getValueAt(rowN, 2).toString());
        cmbuser.setSelectedItem(model.getValueAt(rowN, 3).toString());
        cmbtask.setSelectedItem(model.getValueAt(rowN, 4).toString());
        int estimerValue = Integer.parseInt(model.getValueAt(rowN, 5).toString());
        ((SpinnerNumberModel) cmbEstimer.getModel()).setValue(estimerValue);
        cmbpriority.setSelectedItem(model.getValueAt(rowN, 6).toString());
        cmbstatut.setSelectedItem(model.getValueAt(rowN, 7).toString());
        cmbtype.setSelectedItem(model.getValueAt(rowN, 8).toString());
    }//GEN-LAST:event_tbl_subtaskMouseClicked
    //combo user add users be in combo
    public void bindcombousers() {
        DBconnection mq = new DBconnection();
        HashMap<String, Integer> map = mq.PopulateCombousers();
        for (String s : map.keySet()) {
            cmbuser.addItem(s);
        }
    }

    //combo users
    private void cmbuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbuserActionPerformed
        DBconnection mq = new DBconnection();
        HashMap<String, Integer> map = mq.PopulateCombousers();
        userId = map.get(cmbuser.getSelectedItem().toString());
    }//GEN-LAST:event_cmbuserActionPerformed
    //combo task add tasks be in combo
    public void bindcombotask() {

        DBconnection Prj = new DBconnection();
        HashMap<String, Integer> map = Prj.PopulateCombotask();
        for (String r : map.keySet()) {
            cmbtask.addItem(r);
        }
    }
    //combo task
    private void cmbtaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbtaskActionPerformed
        DBconnection mq = new DBconnection();
        HashMap<String, Integer> mapPrj = mq.PopulateCombotask();
        taskId = mapPrj.get(cmbtask.getSelectedItem().toString());
    }//GEN-LAST:event_cmbtaskActionPerformed

    private void btn_imprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimerActionPerformed
        try {
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\SubTask.jrxml");

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

    private void btn_allMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_allMouseClicked
        fichierSubTask fichiersubtask =new fichierSubTask();
        fichiersubtask .setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_allMouseClicked

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_allActionPerformed

    private void btn_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_searchMouseClicked
        fichierSubTask fichiersubtask =new fichierSubTask();
        fichiersubtask .setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_searchMouseClicked

    private void btn_searchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_searchMouseEntered

    }//GEN-LAST:event_btn_searchMouseEntered

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed

    }//GEN-LAST:event_btn_searchActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        clearform();

    }//GEN-LAST:event_jLabel10MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked

    }//GEN-LAST:event_jPanel12MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
          this.setExtendedState(SubTask.ICONIFIED);
    }//GEN-LAST:event_jLabel14MouseClicked

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SubTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubTask().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.RSButtonHover btn_add;
    private rojerusan.RSButtonMetro btn_all;
    private rojeru_san.complementos.RSButtonHover btn_delete;
    private rojeru_san.complementos.RSButtonHover btn_imprimer;
    private rojerusan.RSButtonMetro btn_search;
    private rojeru_san.complementos.RSButtonHover btn_update;
    private javax.swing.JSpinner cmbEstimer;
    private javax.swing.JComboBox<String> cmbpriority;
    private javax.swing.JComboBox<String> cmbstatut;
    private javax.swing.JComboBox<String> cmbtask;
    private javax.swing.JComboBox<String> cmbtype;
    private javax.swing.JComboBox<String> cmbuser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
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
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_subtask;
    private javax.swing.JTextArea txt_descriptiontask;
    private app.bolivia.swing.JCTextField txt_nametask;
    private javax.swing.JLabel usershow;
    // End of variables declaration//GEN-END:variables
}
