package App.projectManagement.form;

import static App.projectManagement.form.Dashbord.userNameU;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import java.sql.SQLException;

public class Role extends javax.swing.JFrame {

    public void icon() {
        try {
            Image img = ImageIO.read(getClass().getResource("/adminIcons/code.png"));
            this.setIconImage(img);
        } catch (Exception e) {
        }
    }

    String name_Role;
    static int id_Role;
    DefaultTableModel model;

    public Role() {
        initComponents();
        setRoleToTable();
        icon();
        ArrayList<String> permissionData = new Dashbord().permissionData;
        managePermissionLabel(permissionData);
        setUserInLabel();

    }

    public void setUserInLabel() {
        usershow.setText(userNameU);
    }

    public void managePermissionLabel(ArrayList<String> per) {

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

        if (per.contains("Role hide")) {
            jPanel7.setVisible(false);
        }
    }

//to set Role table 
    public void setRoleToTable() {
        try {
            Connection con = DBconnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `role`");

            while (rs.next()) {
                int idRole = rs.getInt(1);  // Assuming the ID is in the first column
                String nameRole = rs.getString(2);  // Assuming the name is in the second column

                Object[] obj = {idRole, nameRole};

                DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
                model.addRow(obj);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add Role to tabledata 
    public boolean addRole() {
        boolean isAdded = false;
        // id_Role = Integer.parseInt(txt_idRole.getText());
        name_Role = txt_nameRole.getText();

        try {
            Connection con = DBconnection.getConnection();
            String sql = "insert into role (roleName)value(?)";
            PreparedStatement prs = con.prepareStatement(sql);

            //  prs.setInt(1, id_Role);
            prs.setString(1, name_Role);

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

    //update Role to table 
    public boolean updateRole(int id) {
        boolean isUpdate = false;
        //  id_Role= Integer.parseInt(txt_idRole.getText());
        name_Role = txt_nameRole.getText();

        try {
            Connection con = DBconnection.getConnection();
            String sql = "UPDATE role SET roleName=? WHERE RoleID=" + id + ";";
            PreparedStatement prs = con.prepareStatement(sql);

            prs.setString(1, name_Role);
            // prs.setInt(2, id_Role);

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

    //delete role to table
    public boolean deleteRole(int id) {
        boolean isDelete = false;
        //  id_Role = Integer.parseInt(txt_idRole.getText());

        try {
            Connection con = DBconnection.getConnection();
            String sql = "DELETE FROM role WHERE RoleID=" + id + ";";
            PreparedStatement prs = con.prepareStatement(sql);

            // prs.setInt(1, id_Role);
            int updatedrowcont = prs.executeUpdate();
            if (updatedrowcont > 0) {
                isDelete = true;
            } else {
                isDelete = false;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(null, "Cannot delete Role because it has associated Users.", "Foreign Key Constraint", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace(); // For other SQL exceptions, print the stack trace
            }
        }
        return isDelete;

    }

    //clear table 
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
        model.setRowCount(0);
    }

    public void clearform() {
        txt_nameRole.setText("");
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
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_nameRole = new app.bolivia.swing.JCTextField();
        btn_add = new rojeru_san.complementos.RSButtonHover();
        btn_update = new rojeru_san.complementos.RSButtonHover();
        btn_delete = new rojeru_san.complementos.RSButtonHover();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_project = new rojeru_san.complementos.RSTableMetro();
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
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 20, 20, 30));

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

        jPanel7.setBackground(new java.awt.Color(0, 206, 209));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconRole.png"))); // NOI18N
        jLabel9.setText("   Role");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, -1));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 260, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 700));

        jPanel10.setBackground(new java.awt.Color(248, 248, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("Nom");
        jPanel17.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 90, 20));

        txt_nameRole.setBackground(new java.awt.Color(255, 255, 255));
        txt_nameRole.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nameRole.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameRole.setPlaceholder("entre role name");
        jPanel17.add(txt_nameRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 230, -1));

        btn_add.setText("Ajouter");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel17.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, 130, -1));

        btn_update.setBackground(new java.awt.Color(255, 153, 0));
        btn_update.setText("Modifier");
        btn_update.setColorHover(new java.awt.Color(255, 204, 51));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel17.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 100, 130, 40));

        btn_delete.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete.setText("supprimer");
        btn_delete.setColorHover(new java.awt.Color(255, 51, 51));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel17.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 170, 130, -1));

        jPanel11.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 970, 230));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("gestion des rôles");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel11.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        tbl_project.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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
        tbl_project.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_projectMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_project);
        if (tbl_project.getColumnModel().getColumnCount() > 0) {
            tbl_project.getColumnModel().getColumn(0).setResizable(false);
            tbl_project.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel11.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 970, 240));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 970, 570));

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

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        new SubTask().setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel16MouseClicked

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (addRole() == true) {
            JOptionPane.showMessageDialog(this, "Role created succesful");
            clearTable();
            setRoleToTable();

        } else {
            JOptionPane.showMessageDialog(this, "Role created failed !");
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        if (updateRole(id_Role) == true) {
            JOptionPane.showMessageDialog(this, "Role Update succesful");
            clearTable();
            setRoleToTable();

        } else {
            JOptionPane.showMessageDialog(this, "Role update failed !");
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (deleteRole(id_Role) == true) {
            JOptionPane.showMessageDialog(this, "Role Delet succesful");
            clearTable();
            setRoleToTable();

        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void tbl_projectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_projectMouseClicked
        int rowN = tbl_project.getSelectedRow();
        TableModel model = tbl_project.getModel();

        id_Role = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameRole.setText(model.getValueAt(rowN, 1).toString());

    }//GEN-LAST:event_tbl_projectMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        new User().setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        clearform();

    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.setExtendedState(Role.ICONIFIED);
    }//GEN-LAST:event_jLabel14MouseClicked

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
            java.util.logging.Logger.getLogger(Role.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Role.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Role.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Role.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Role().setVisible(true);
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private rojeru_san.complementos.RSTableMetro tbl_project;
    private app.bolivia.swing.JCTextField txt_nameRole;
    private javax.swing.JLabel usershow;
    // End of variables declaration//GEN-END:variables
}
