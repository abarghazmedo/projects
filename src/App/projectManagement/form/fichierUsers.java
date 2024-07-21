
package App.projectManagement.form;


import static App.projectManagement.form.User.id_user;
import static App.projectManagement.form.User.roleId;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mohamed
 */
public class fichierUsers extends javax.swing.JFrame {
 
    static int idTask,userId ,projetId;
    DefaultTableModel model;

    public fichierUsers() {
        initComponents();
        bindcomboRole();
         setUserToTable();
     
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
    
     

       public void searchName(String str) {
    model = (DefaultTableModel) tbl_user.getModel();
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
    tbl_user.setRowSorter(trs);
    
    // Check if the input string is null or empty
    if (str == null || str.trim().isEmpty()) {
        trs.setRowFilter(null); // No filtering
    } else {
        // Use Pattern.quote to escape any special characters in the input string
        String sanitizedStr = Pattern.quote(str);
        trs.setRowFilter(RowFilter.regexFilter(sanitizedStr));
    }
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
        txt_passwordUser.setText("");
        txt_jobtitle.setText("");
        cmbrole1.setSelectedIndex(0);      
    }
    
        //show first row
    public void showToFirstRow() {
        if (tbl_user.getRowCount() > 0) {
            tbl_user.setRowSelectionInterval(0, 0);
            SetIntextfeild(0);
        }
    }

    //show Next row
    public void showToNextRow() {
        int selectedRow = tbl_user.getSelectedRow();
        int rowCount = tbl_user.getRowCount();
        if (selectedRow < rowCount - 1) {
            tbl_user.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
            SetIntextfeild(selectedRow + 1);
        }
    }
    
    //show Previous row
    public void showToPreviousRow() {
        int selectedRow = tbl_user.getSelectedRow();
        if (selectedRow > 0) {
            tbl_user.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
            SetIntextfeild(selectedRow - 1);
        }
    }
    
    //show Last row
    public void showToLastRow() {
        int rowCount = tbl_user.getRowCount();
        if (rowCount > 0) {
            tbl_user.setRowSelectionInterval(rowCount - 1, rowCount - 1);
            SetIntextfeild(rowCount - 1);
        }
    }
      
    //  set index Data from table 
    public void SetIntextfeild(int row) {

        int rowN = tbl_user.getSelectedRow();
        TableModel model = tbl_user.getModel();
        
        id_user=Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameUser.setText(model.getValueAt(rowN, 1).toString());
        txt_nameUser.setEditable(false);
        txt_emailUser.setText(model.getValueAt(rowN, 2).toString());
        txt_emailUser.setEditable(false);
        txt_jobtitle.setText(model.getValueAt(rowN, 3).toString());
        txt_jobtitle.setEditable(false);
        cmbrole1.setSelectedItem(model.getValueAt(rowN, 4).toString()); 
        cmbrole1.setEnabled(false);
        txt_passwordUser.setText(model.getValueAt(rowN, 5).toString());
        txt_passwordUser.setEditable(false);

    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_passwordUser = new rojerusan.RSPasswordTextPlaceHolder();
        cmbrole1 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_jobtitle = new app.bolivia.swing.JCTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_emailUser = new app.bolivia.swing.JCTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_nameUser = new app.bolivia.swing.JCTextField();
        jLabel10 = new javax.swing.JLabel();
        btn_firs = new rojerusan.RSButtonMetro();
        btn_next = new rojerusan.RSButtonMetro();
        btn_back = new rojerusan.RSButtonMetro();
        btn_last = new rojerusan.RSButtonMetro();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_searchName = new app.bolivia.swing.JCTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_user = new rojeru_san.complementos.RSTableMetro();
        btn_imprimer = new rojeru_san.complementos.RSButtonHover();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("fichiers de gestion des utilisateurs");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel16.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 81, 98));
        jLabel19.setText("Mot de passe ");
        jPanel16.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 110, 20));

        txt_passwordUser.setBackground(new java.awt.Color(255, 255, 255));
        txt_passwordUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_passwordUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_passwordUser.setPhColor(new java.awt.Color(0, 0, 0));
        txt_passwordUser.setPlaceholder("Entrer le mot de passe");
        txt_passwordUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordUserActionPerformed(evt);
            }
        });
        jPanel16.add(txt_passwordUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 130, 190, 30));

        cmbrole1.setBackground(new java.awt.Color(255, 255, 255));
        cmbrole1.setForeground(new java.awt.Color(0, 81, 98));
        cmbrole1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbrole1ActionPerformed(evt);
            }
        });
        jPanel16.add(cmbrole1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 230, 30));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 81, 98));
        jLabel29.setText("Role");
        jPanel16.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 80, 20));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 81, 98));
        jLabel17.setText("profil");
        jPanel16.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 60, 20));

        txt_jobtitle.setBackground(new java.awt.Color(255, 255, 255));
        txt_jobtitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_jobtitle.setForeground(new java.awt.Color(0, 81, 98));
        txt_jobtitle.setPlaceholder("entrez le profil");
        jPanel16.add(txt_jobtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 220, -1));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 81, 98));
        jLabel18.setText("E-mail");
        jPanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 90, 20));

        txt_emailUser.setBackground(new java.awt.Color(255, 255, 255));
        txt_emailUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_emailUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_emailUser.setPlaceholder("entrez l'email ");
        txt_emailUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailUserActionPerformed(evt);
            }
        });
        jPanel16.add(txt_emailUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 230, -1));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 81, 98));
        jLabel16.setText("Nom");
        jPanel16.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 60, 20));

        txt_nameUser.setBackground(new java.awt.Color(255, 255, 255));
        txt_nameUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nameUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameUser.setPlaceholder("entre le nom complet ");
        jPanel16.add(txt_nameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 230, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel16.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        btn_firs.setText("Premier");
        btn_firs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_firsMouseClicked(evt);
            }
        });
        btn_firs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_firsActionPerformed(evt);
            }
        });
        jPanel16.add(btn_firs, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 120, 30));

        btn_next.setText("Suivant");
        btn_next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_nextMouseClicked(evt);
            }
        });
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });
        jPanel16.add(btn_next, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 90, 120, 30));

        btn_back.setText("Précédent");
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        jPanel16.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 120, 30));

        btn_last.setText("Dernier");
        btn_last.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_lastMouseClicked(evt);
            }
        });
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });
        jPanel16.add(btn_last, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, 120, 30));

        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1000, 260));

        jPanel1.setBackground(new java.awt.Color(0, 206, 209));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/back (2).png"))); // NOI18N
        jLabel1.setText("Revenir");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 50));

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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 5, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 81, 98));
        jLabel4.setText("X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 20, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Global Technology2.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 210, 50));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 81, 98));
        jLabel5.setText("-");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 20, 20, 30));

        jPanel15.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 70));

        txt_searchName.setBackground(new java.awt.Color(255, 255, 255));
        txt_searchName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_searchName.setForeground(new java.awt.Color(0, 81, 98));
        txt_searchName.setPlaceholder("Entre search String");
        txt_searchName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_searchNameFocusLost(evt);
            }
        });
        txt_searchName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchNameKeyReleased(evt);
            }
        });
        jPanel15.add(txt_searchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 180, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        jPanel15.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 30, 30));

        tbl_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom", "E-mail", "Profil", "Role", "Mot de passe "
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

        jPanel15.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 970, 210));

        btn_imprimer.setBackground(new java.awt.Color(255, 153, 0));
        btn_imprimer.setText("Imprimer");
        btn_imprimer.setColorHover(new java.awt.Color(255, 51, 51));
        btn_imprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimerActionPerformed(evt);
            }
        });
        jPanel15.add(btn_imprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 360, 210, 30));

        getContentPane().add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 620));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void showInTextFeild(){
        try {
            Connection con = DBconnection.getConnection();
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT p.projectID,p.projectName,p.projectDescription,p.Startdate,p.ENDdate,u.UserName FROM `project` p ,userp u WHERE u.UserID = p.UserID ");

            
      
   } catch (Exception e) {
            e.printStackTrace();
        }
   }
  
    
    
    private void txt_searchNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchNameFocusLost

    private void txt_searchNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchNameKeyReleased
        String searchname=txt_searchName.getText();
        searchName(searchname);
    }//GEN-LAST:event_txt_searchNameKeyReleased

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
       User user=new User();
       user.setVisible(true);
       dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
     
      User user=new User();
       user.setVisible(true);
       dispose();

    }//GEN-LAST:event_jLabel1MouseClicked

    
      public void bindcomboRole() { 
        DBconnection mq = new DBconnection();
        HashMap<String, Integer> map = mq.PopulateComboRole();
        for (String s : map.keySet()) {
            cmbrole1.addItem(s);
        }
    }
    private void cmbrole1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbrole1ActionPerformed
          DBconnection mq=new DBconnection();
        HashMap<String,Integer> map=mq.PopulateComboRole();
        roleId=map.get(cmbrole1.getSelectedItem().toString());
    }//GEN-LAST:event_cmbrole1ActionPerformed

    private void tbl_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_userMouseClicked
       int rowN = tbl_user.getSelectedRow();
        TableModel model = tbl_user.getModel();
        
        id_user=Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameUser.setText(model.getValueAt(rowN, 1).toString());
        txt_nameUser.setEditable(false);
        txt_emailUser.setText(model.getValueAt(rowN, 2).toString());
        txt_emailUser.setEditable(false);
        txt_jobtitle.setText(model.getValueAt(rowN, 3).toString());
        txt_jobtitle.setEditable(false);
        cmbrole1.setSelectedItem(model.getValueAt(rowN, 4).toString()); 
        cmbrole1.setEnabled(false);
        txt_passwordUser.setText(model.getValueAt(rowN, 5).toString());
        txt_passwordUser.setEditable(false);

    }//GEN-LAST:event_tbl_userMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
         clearform();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void btn_firsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_firsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_firsMouseClicked

    private void btn_firsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firsActionPerformed
        showToFirstRow();
    }//GEN-LAST:event_btn_firsActionPerformed

    private void btn_nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_nextMouseClicked

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        showToNextRow();
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_backMouseClicked

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        showToPreviousRow();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_lastMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lastMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_lastMouseClicked

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        showToLastRow();
    }//GEN-LAST:event_btn_lastActionPerformed

    private void btn_imprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimerActionPerformed
        try {
            // Load the JasperReport template
            JasperDesign jasdi = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\FichierUser.jrxml");

            // Get the selected row from the jTable
            int selectedRow = tbl_user.getSelectedRow();

            // Check if the user selected any row
            if (selectedRow == -1) {
                // If nothing is selected, show an error message
                JOptionPane.showMessageDialog(null, "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                // Construct the SQL query dynamically based on the user's selection
                String id = tbl_user.getValueAt(selectedRow, 0).toString(); // Assuming the first column contains the PersID
                String sql = "SELECT u.UserID,u.UserName,u.UserEmail,u.UserJob,r.roleName,"
                        + "u.UserPassword FROM userp u ,role r WHERE r.RoleID=u.RoleID AND UserID = '" + id + "' ORDER BY UserID DESC ";

                // Set the constructed SQL query
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jasdi.setQuery(newQuery);
            }

            // Compile the JasperReport template
            JasperReport js = JasperCompileManager.compileReport(jasdi);

            // Provide a database connection (replace 'con' with your actual Connection object)
            Connection con = DBconnection.getConnection();

            // Fill the JasperReport with data
            JasperPrint jd = JasperFillManager.fillReport(js, null, con);

            // Use an array to hold the JasperViewer instance
            final JasperViewer[] jasperViewerHolder = new JasperViewer[1];
            jasperViewerHolder[0] = new JasperViewer(jd, false);

            // Add window listener to the JasperViewer frame
            jasperViewerHolder[0].addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // This method is called when the JasperViewer frame is closing
                    // Handle the closing event here (for example, hide the JasperViewer instead of closing the entire application)
                    jasperViewerHolder[0].setVisible(false);
                    jasperViewerHolder[0] = null; // Set the JasperViewer instance to null after hiding
                }
            });

            jasperViewerHolder[0].setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_imprimerActionPerformed

    private void txt_passwordUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordUserActionPerformed

    private void txt_emailUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailUserActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
          this.setExtendedState(fichierUsers.ICONIFIED);
    }//GEN-LAST:event_jLabel5MouseClicked

  
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
            java.util.logging.Logger.getLogger(fichierUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fichierUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fichierUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fichierUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fichierUsers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSButtonMetro btn_back;
    private rojerusan.RSButtonMetro btn_firs;
    private rojeru_san.complementos.RSButtonHover btn_imprimer;
    private rojerusan.RSButtonMetro btn_last;
    private rojerusan.RSButtonMetro btn_next;
    private javax.swing.JComboBox<String> cmbrole1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_user;
    private app.bolivia.swing.JCTextField txt_emailUser;
    private app.bolivia.swing.JCTextField txt_jobtitle;
    private app.bolivia.swing.JCTextField txt_nameUser;
    private rojerusan.RSPasswordTextPlaceHolder txt_passwordUser;
    private app.bolivia.swing.JCTextField txt_searchName;
    // End of variables declaration//GEN-END:variables
}
