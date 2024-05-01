/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package App.ProjectManaement.form;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Ibtissam
 */
public class Permission extends javax.swing.JFrame {

    String name_Permission;
   static int id_Permission ;
    DefaultTableModel model;

    public Permission() {
        initComponents();
        setPermissionToTable();

    }

    //to set project table 
    
   public void setPermissionToTable() {
        try {
            Connection con = DBconnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM  permission");

            while (rs.next()) {
                int idPermission = rs.getInt(1);  // Assuming the ID is in the first column
                String namePermission = rs.getString(2);  // Assuming the name is in the second column
                Object[] obj = {idPermission, namePermission};

                DefaultTableModel model = (DefaultTableModel) tbl_permission.getModel();
                model.addRow(obj);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    //Add permission to tabledata 

    public boolean addPermission() {
        boolean isAdded = false;
     
        name_Permission = txt_namePermission.getText();
       

        try {
            Connection con = DBconnection.getConnection();
            String sql = "insert into taskapp (TaskAppName)value(?)";
            PreparedStatement prs = con.prepareStatement(sql);

         //   prs.setInt(1, id_Permission);
            prs.setString(1, name_Permission);
      
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

    //update permission to table 
    
    public boolean updatePermission(int id) {
        boolean isUpdate = false;
     //   id_Permission= Integer.parseInt(txt_idPermission.getText());
        name_Permission= txt_namePermission.getText();
     

        try {
            Connection con = DBconnection.getConnection();
            String sql = "UPDATE taskapp SET TaskAppName=? WHERE AppID="+id+";";
            PreparedStatement prs = con.prepareStatement(sql);

            prs.setString(1, name_Permission);           
         

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

    //delete permission to table
    
    public boolean deletePermission(int id) {
        boolean isDelete = false;
       // id_Permission= Integer.parseInt(txt_idPermission.getText());

        try {
            Connection con = DBconnection.getConnection();
            String sql = "DELETE FROM taskapp WHERE AppID="+id+";";
            PreparedStatement prs = con.prepareStatement(sql);

           // prs.setInt(1, id_Permission);
            int updatedrowcont = prs.executeUpdate();
            if (updatedrowcont > 0) {
                isDelete = true;
            } else {
                isDelete = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDelete;

    }

    //clear table 
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_permission.getModel();
        model.setRowCount(0);
    }
    
    public void clearform(){
        txt_namePermission.setText("");
    }

    //is not correct
    /*  public void setProjectToTable(){
        
        try {
            Connection con = DBconnection.getConnection();
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery("SELECT * FROM `project`");
            
           while (rs.next()){
                 
               int idProject=rs.getInt(id_project);
               String nameProject=rs.getString(name_project);
               String descriptionProject=rs.getString(description_project);
               int assighnTo=rs.getInt(assigne_to);
               
               Object[] obj={idProject,nameProject,descriptionProject,assighnTo};
               
               model=(DefaultTableModel) tbl_project.getModel();
               model.addRow(obj);
            
           }
         
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_permission = new rojeru_san.complementos.RSTableMetro();
        jLabel11 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        btn_add = new rojeru_san.complementos.RSButtonHover();
        btn_update = new rojeru_san.complementos.RSButtonHover();
        btn_delete = new rojeru_san.complementos.RSButtonHover();
        txt_namePermission = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();

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
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 10, 20, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Global Technology2.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 210, 50));

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
        jLabel8.setText("   Dashboard");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 170, -1));

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
        jLabel6.setText("   Project");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 140, -1));

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
        jLabel15.setText("   Users");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 150, -1));

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
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 150, -1));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 260, 60));

        jPanel8.setBackground(new java.awt.Color(0, 206, 209));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icon dashbord blue_3.png"))); // NOI18N
        jLabel10.setText("   Permission");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 180, -1));

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 260, 60));

        jPanel9.setBackground(new java.awt.Color(0, 81, 98));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Exit_26px.png"))); // NOI18N
        jLabel5.setText("   Logout");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 180, -1));

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
        jLabel7.setText("   Task");
        jPanel14.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 150, -1));

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
        jLabel12.setText(" Sub Task");
        jPanel16.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 150, -1));

        jPanel3.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 700));

        jPanel10.setBackground(new java.awt.Color(248, 248, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_permission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " Id", "Name"
            }
        ));
        tbl_permission.setColorBackgoundHead(new java.awt.Color(0, 206, 209));
        tbl_permission.setColorFilasForeground1(new java.awt.Color(128, 128, 128));
        tbl_permission.setColorFilasForeground2(new java.awt.Color(128, 128, 128));
        tbl_permission.setColorSelBackgound(new java.awt.Color(0, 81, 98));
        tbl_permission.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        tbl_permission.setRowHeight(40);
        tbl_permission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_permissionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_permission);

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 830, 210));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Permission Management");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel15.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Account_50px.png"))); // NOI18N
        jPanel12.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 50, 50));

        btn_add.setText("CREATE");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel12.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 130, -1));

        btn_update.setBackground(new java.awt.Color(255, 153, 0));
        btn_update.setText("UPDATE");
        btn_update.setColorHover(new java.awt.Color(255, 204, 51));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel12.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 100, 130, -1));

        btn_delete.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete.setText("DELETE");
        btn_delete.setColorHover(new java.awt.Color(255, 51, 51));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel12.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 170, 130, -1));

        txt_namePermission.setBackground(new java.awt.Color(255, 255, 255));
        txt_namePermission.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_namePermission.setForeground(new java.awt.Color(0, 81, 98));
        txt_namePermission.setPlaceholder("entre permission name");
        jPanel12.add(txt_namePermission, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 230, -1));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("Permission Name");
        jPanel12.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 130, 20));

        jPanel15.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 830, 250));

        jPanel11.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 570));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 830, 570));

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1020, 700));

        setSize(new java.awt.Dimension(1279, 769));
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

    private void tbl_permissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_permissionMouseClicked
        int rowN = tbl_permission.getSelectedRow();
        TableModel model = tbl_permission.getModel();

        id_Permission=Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_namePermission.setText(model.getValueAt(rowN, 1).toString());
     //   txt_description.setText(model.getValueAt(rowN, 2).toString());
    //    txt_assigneto.setText(model.getValueAt(rowN, 3).toString());
    }//GEN-LAST:event_tbl_permissionMouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
       Task task=new Task();
       task.setVisible(true);
       dispose();
       
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
       Project project=new Project();
       project.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        User user=new User();
       user.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
       Role role=new Role();
       role.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
         SubTask task=new SubTask();
       task.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel16MouseClicked

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (addPermission() == true) {
            JOptionPane.showMessageDialog(this, "Project created succesful");
            clearTable();
            setPermissionToTable();
           
        } else {
            JOptionPane.showMessageDialog(this, "Project created failed !");
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        if (updatePermission(id_Permission) == true) {
            JOptionPane.showMessageDialog(this, "Project Update succesful");
            clearTable();
            setPermissionToTable();
            
        } else {
            JOptionPane.showMessageDialog(this, "Project update failed !");
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (deletePermission(id_Permission)== true) {
            JOptionPane.showMessageDialog(this, "Project Delet succesful");
            clearTable();
            setPermissionToTable();
           
        } else {
            JOptionPane.showMessageDialog(this, "Project Delete  failed !");
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Permission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Permission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Permission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Permission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Permission().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.complementos.RSButtonHover btn_add;
    private rojeru_san.complementos.RSButtonHover btn_delete;
    private rojeru_san.complementos.RSButtonHover btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private rojeru_san.complementos.RSTableMetro tbl_permission;
    private app.bolivia.swing.JCTextField txt_namePermission;
    // End of variables declaration//GEN-END:variables
}
