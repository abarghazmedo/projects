
package App.projectManagement.form;


import static App.projectManagement.form.Task.emailtasks;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
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
public class fichierTask extends javax.swing.JFrame {
 
    static int idTask,userId ,projetId;
    DefaultTableModel model;

    public fichierTask() {
        initComponents();
         setTaskToTable();
         bindcombousers();
          bindcomboproject();  
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
     
    //show details task in table  
    public void setTaskToTable() {
        String roleName = getRoleName();
        System.out.println(roleName);
        model = (DefaultTableModel) tbl_task.getModel(); // Assuming tbl_task is your JTable for tasks

        try (Connection con = DBconnection.getConnection()) {
            String query = "SELECT t.taskID, t.taskName, t.taskDescription, t.userID AS UserIDtask , u.UserName,u.UserEmail,\n"
                    + "p.UserID AS UserIDproject ,p.projectName, t.Estimer, t.taskPriority, t.taskStatut, t.taskType \n"
                    + "FROM task t \n"
                    + "INNER JOIN project p ON p.projectID = t.projectID \n"
                    + "INNER JOIN userp u ON t.userID = u.UserID ";

            if ("Manager".equals(roleName)) {
                query = query + "WHERE p.UserID = (SELECT UserID FROM userp u WHERE u.UserEmail=?)";
            }

            if ("Developer".equals(roleName)) {

                query = query + "WHERE u.UserEmail=?";
            }
            System.out.println("Developer".equals(roleName));
            System.out.println(query);

            try (PreparedStatement pst = con.prepareStatement(query)) {
                if ("Manager".equals(roleName) || "Developer".equals(roleName)) {
                    pst.setString(1, String.valueOf(emailtasks));
                }
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int taskId = rs.getInt("taskID");
                        String taskNameResult = rs.getString("taskName");
                        String taskDescription = rs.getString("taskDescription");
                        String userName = rs.getString("UserName");
                        String projectName = rs.getString("projectName");
                        String estimer = rs.getString("Estimer");
                        String taskPriority = rs.getString("taskPriority");
                        String taskStatut = rs.getString("taskStatut");
                        String taskType = rs.getString("taskType");

                        Object[] obj = {taskId, taskNameResult, taskDescription, userName, projectName, estimer, taskPriority, taskStatut, taskType};
                        model = (DefaultTableModel) tbl_task.getModel();
                        model.addRow(obj);
                    }
                }
            
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
     

      public void searchName(String str) {
    model = (DefaultTableModel) tbl_task.getModel();
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
    tbl_task.setRowSorter(trs);
    
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
        DefaultTableModel model = (DefaultTableModel) tbl_task.getModel();
        model.setRowCount(0);
    }
    //clear Form

       public void clearForm() {
        txt_nametask.setText("");
        txt_descriptiontask.setText("");
        cmbuser.setSelectedIndex(0);
        cmbproject.setSelectedIndex(0);
        cmbEstimer.setValue(0);
        cmbpriority.setSelectedIndex(0);
        cmbstatut.setSelectedIndex(0);
        cmbtype.setSelectedIndex(0);
       
    }
       
    //show first row
    public void showToFirstRow() {
        if (tbl_task.getRowCount() > 0) {
            tbl_task.setRowSelectionInterval(0, 0);
            SetIntextfeild(0);
        }
    }

    //show Next row
    public void showToNextRow() {
        int selectedRow = tbl_task.getSelectedRow();
        int rowCount = tbl_task.getRowCount();
        if (selectedRow < rowCount - 1) {
            tbl_task.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
            SetIntextfeild(selectedRow + 1);
        }
    }
    
    //show Previous row
    public void showToPreviousRow() {
        int selectedRow = tbl_task.getSelectedRow();
        if (selectedRow > 0) {
            tbl_task.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
            SetIntextfeild(selectedRow - 1);
        }
    }
    
    //show Last row
    public void showToLastRow() {
        int rowCount = tbl_task.getRowCount();
        if (rowCount > 0) {
            tbl_task.setRowSelectionInterval(rowCount - 1, rowCount - 1);
            SetIntextfeild(rowCount - 1);
        }
    }
      
    //  set index Data from table 
    public void SetIntextfeild(int row) {

      int rowN = tbl_task.getSelectedRow();
        TableModel model = tbl_task.getModel();
       idTask = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nametask.setText(model.getValueAt(rowN, 1).toString());
         txt_nametask.setEditable(false);
        txt_descriptiontask.setText(model.getValueAt(rowN, 2).toString());
        txt_descriptiontask.setEditable(false);
        cmbuser.setSelectedItem(model.getValueAt(rowN, 3).toString());
         cmbuser.setEnabled(false);
        cmbproject.setSelectedItem(model.getValueAt(rowN, 4).toString());
         cmbproject.setEnabled(false);
        int estimerValue = Integer.parseInt(model.getValueAt(rowN, 5).toString());
        ((SpinnerNumberModel) cmbEstimer.getModel()).setValue(estimerValue);
        cmbpriority.setSelectedItem(model.getValueAt(rowN, 6).toString());
         cmbpriority.setEnabled(false);
        cmbstatut.setSelectedItem(model.getValueAt(rowN, 7).toString());
         cmbstatut.setEnabled(false);
        cmbtype.setSelectedItem(model.getValueAt(rowN, 8).toString());
         cmbtype.setEnabled(false);

    }
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btn_firs = new rojerusan.RSButtonMetro();
        btn_next = new rojerusan.RSButtonMetro();
        btn_back = new rojerusan.RSButtonMetro();
        btn_last = new rojerusan.RSButtonMetro();
        cmbtype = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbuser = new javax.swing.JComboBox<>();
        cmbstatut = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_descriptiontask = new javax.swing.JTextArea();
        cmbpriority = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_nametask = new app.bolivia.swing.JCTextField();
        jLabel22 = new javax.swing.JLabel();
        cmbproject = new javax.swing.JComboBox<>();
        cmbEstimer = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_searchName = new app.bolivia.swing.JCTextField();
        btn_imprimer = new rojeru_san.complementos.RSButtonHover();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_task = new rojeru_san.complementos.RSTableMetro();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Fichiers de tâches");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel16.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

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
        jPanel16.add(btn_firs, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 50, 120, 30));

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
        jPanel16.add(btn_next, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 120, 30));

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
        jPanel16.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, 120, 30));

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
        jPanel16.add(btn_last, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 210, 120, 30));

        cmbtype.setBackground(new java.awt.Color(255, 255, 255));
        cmbtype.setForeground(new java.awt.Color(0, 81, 98));
        cmbtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Evolution", "Bug" }));
        jPanel16.add(cmbtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 220, 230, 30));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 81, 98));
        jLabel19.setText("Type");
        jPanel16.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 70, 20));

        cmbuser.setBackground(new java.awt.Color(255, 255, 255));
        cmbuser.setForeground(new java.awt.Color(0, 81, 98));
        cmbuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbuserActionPerformed(evt);
            }
        });
        jPanel16.add(cmbuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 230, 30));

        cmbstatut.setBackground(new java.awt.Color(255, 255, 255));
        cmbstatut.setForeground(new java.awt.Color(0, 81, 98));
        cmbstatut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "To do", "In progress", "In test", "Done" }));
        jPanel16.add(cmbstatut, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 230, 30));

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 81, 98));
        jLabel23.setText("Statut");
        jPanel16.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 70, 20));

        txt_descriptiontask.setBackground(new java.awt.Color(255, 255, 255));
        txt_descriptiontask.setColumns(20);
        txt_descriptiontask.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_descriptiontask.setForeground(new java.awt.Color(0, 81, 98));
        txt_descriptiontask.setRows(5);
        txt_descriptiontask.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        jScrollPane2.setViewportView(txt_descriptiontask);

        jPanel16.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 270, 100));

        cmbpriority.setBackground(new java.awt.Color(255, 255, 255));
        cmbpriority.setForeground(new java.awt.Color(0, 81, 98));
        cmbpriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P0", "P1", "P2", "P3", " " }));
        jPanel16.add(cmbpriority, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 140, 230, 30));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 81, 98));
        jLabel24.setText("Priorité");
        jPanel16.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 70, 20));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 81, 98));
        jLabel17.setText("Description");
        jPanel16.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 90, 20));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("Nom");
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 20));

        txt_nametask.setBackground(new java.awt.Color(255, 255, 255));
        txt_nametask.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nametask.setForeground(new java.awt.Color(0, 81, 98));
        txt_nametask.setPlaceholder("entre le nom de la tâche");
        jPanel16.add(txt_nametask, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 270, -1));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 81, 98));
        jLabel22.setText("Projet");
        jPanel16.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 70, 20));

        cmbproject.setBackground(new java.awt.Color(255, 255, 255));
        cmbproject.setForeground(new java.awt.Color(0, 81, 98));
        cmbproject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbprojectActionPerformed(evt);
            }
        });
        jPanel16.add(cmbproject, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 230, 30));
        jPanel16.add(cmbEstimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, 70, -1));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 81, 98));
        jLabel27.setText("Estimations");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 90, 20));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 81, 98));
        jLabel25.setText("affecter à");
        jPanel16.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 80, 20));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
        });
        jPanel16.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

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
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 12, 30, 40));

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
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 20, 20, 20));

        jPanel15.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 70));

        txt_searchName.setBackground(new java.awt.Color(255, 255, 255));
        txt_searchName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_searchName.setForeground(new java.awt.Color(0, 81, 98));
        txt_searchName.setPlaceholder("Entre recherche Chaîne");
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
        jPanel15.add(txt_searchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 180, -1));

        btn_imprimer.setBackground(new java.awt.Color(255, 153, 0));
        btn_imprimer.setText("Imprimer");
        btn_imprimer.setColorHover(new java.awt.Color(255, 51, 51));
        btn_imprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimerActionPerformed(evt);
            }
        });
        jPanel15.add(btn_imprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 360, 200, 30));

        tbl_task.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom", "Description", "affecter à", "Projet", "Estimer", "Priorité", "Statut", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_task.setColorBackgoundHead(new java.awt.Color(0, 206, 209));
        tbl_task.setColorFilasForeground1(new java.awt.Color(128, 128, 128));
        tbl_task.setColorFilasForeground2(new java.awt.Color(128, 128, 128));
        tbl_task.setColorSelBackgound(new java.awt.Color(0, 81, 98));
        tbl_task.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        tbl_task.setRowHeight(40);
        tbl_task.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_taskMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_task);
        if (tbl_task.getColumnModel().getColumnCount() > 0) {
            tbl_task.getColumnModel().getColumn(0).setResizable(false);
            tbl_task.getColumnModel().getColumn(1).setResizable(false);
            tbl_task.getColumnModel().getColumn(2).setResizable(false);
            tbl_task.getColumnModel().getColumn(3).setResizable(false);
            tbl_task.getColumnModel().getColumn(4).setResizable(false);
            tbl_task.getColumnModel().getColumn(5).setResizable(false);
            tbl_task.getColumnModel().getColumn(6).setResizable(false);
            tbl_task.getColumnModel().getColumn(7).setResizable(false);
            tbl_task.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 980, 210));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        jPanel15.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 20, 30));

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
        //combo user add users be in combo
    public void bindcombousers() {
       DBconnection mq=new DBconnection();
       HashMap<String,Integer> map=mq.PopulateCombousers();
       for(String s :map.keySet()){
           cmbuser.addItem(s);
       }       
    }
    
    
    private void txt_searchNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchNameFocusLost

    private void txt_searchNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchNameKeyReleased
        String searchname=txt_searchName.getText();
        searchName(searchname);
    }//GEN-LAST:event_txt_searchNameKeyReleased

    private void btn_firsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_firsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_firsMouseClicked

    private void btn_firsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firsActionPerformed
    showToFirstRow();
    }//GEN-LAST:event_btn_firsActionPerformed

    private void btn_lastMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lastMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_lastMouseClicked

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
     showToLastRow();    
    }//GEN-LAST:event_btn_lastActionPerformed

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

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
       Task task = new Task();
        task.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void btn_imprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimerActionPerformed
  try {
        // Load the JasperReport template
        JasperDesign jasdi = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\FichierTask.jrxml");

        // Get the selected row from the jTable
        int selectedRow = tbl_task.getSelectedRow();

        // Check if the user selected any row
        if (selectedRow == -1) {
            // If nothing is selected, show an error message
            JOptionPane.showMessageDialog(null, "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            // Construct the SQL query dynamically based on the user's selection
            String id = tbl_task.getValueAt(selectedRow, 0).toString(); // Assuming the first column contains the PersID
            String sql = "SELECT t.taskID, t.taskName, t.taskDescription, u.UserName, p.projectName, t.Estimer, " +
                         "t.taskPriority, t.taskStatut, t.taskType FROM task t, userp u, project p " +
                         "WHERE u.userID = t.userID AND p.projectID = t.projectID AND t.taskID = '" + id + "'";

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

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
     
        Task task = new Task();
        task.setVisible(true);
        dispose();

    }//GEN-LAST:event_jLabel1MouseClicked

    private void tbl_taskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_taskMouseClicked
      int rowN = tbl_task.getSelectedRow();
        TableModel model = tbl_task.getModel();
       idTask = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nametask.setText(model.getValueAt(rowN, 1).toString());
         txt_nametask.setEditable(false);
        txt_descriptiontask.setText(model.getValueAt(rowN, 2).toString());
        txt_descriptiontask.setEditable(false);
        cmbuser.setSelectedItem(model.getValueAt(rowN, 3).toString());
         cmbuser.setEnabled(false);
        cmbproject.setSelectedItem(model.getValueAt(rowN, 4).toString());
         cmbproject.setEnabled(false);
        int estimerValue = Integer.parseInt(model.getValueAt(rowN, 5).toString());
        ((SpinnerNumberModel) cmbEstimer.getModel()).setValue(estimerValue);
        cmbpriority.setSelectedItem(model.getValueAt(rowN, 6).toString());
         cmbpriority.setEnabled(false);
        cmbstatut.setSelectedItem(model.getValueAt(rowN, 7).toString());
         cmbstatut.setEnabled(false);
        cmbtype.setSelectedItem(model.getValueAt(rowN, 8).toString());
         cmbtype.setEnabled(false);
    }//GEN-LAST:event_tbl_taskMouseClicked

    private void cmbuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbuserActionPerformed
          DBconnection mq = new DBconnection();
        HashMap<String, Integer> map = mq.PopulateCombousers();
        userId = map.get(cmbuser.getSelectedItem().toString());
        Object user = cmbuser.getSelectedItem();
        if (user != null) {
            userId = map.get(user.toString());
        }
    }//GEN-LAST:event_cmbuserActionPerformed

      //combo user add project be in combo
    public void bindcomboproject() {
        DBconnection Prj = new DBconnection();
        HashMap<String, Integer> map = Prj.PopulateComboprojet();
        for (String r : map.keySet()) {
            cmbproject.addItem(r);
        }
    }
    private void cmbprojectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbprojectActionPerformed
         DBconnection mq=new DBconnection();
        HashMap<String,Integer> mapPrj=mq.PopulateComboprojet();
         projetId=mapPrj.get(cmbproject.getSelectedItem().toString()); 
    }//GEN-LAST:event_cmbprojectActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
       clearForm();
       
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
      this.setExtendedState(fichierTask.ICONIFIED);
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
            java.util.logging.Logger.getLogger(fichierTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fichierTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fichierTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fichierTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fichierTask().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSButtonMetro btn_back;
    private rojerusan.RSButtonMetro btn_firs;
    private rojeru_san.complementos.RSButtonHover btn_imprimer;
    private rojerusan.RSButtonMetro btn_last;
    private rojerusan.RSButtonMetro btn_next;
    private javax.swing.JSpinner cmbEstimer;
    private javax.swing.JComboBox<String> cmbpriority;
    private javax.swing.JComboBox<String> cmbproject;
    private javax.swing.JComboBox<String> cmbstatut;
    private javax.swing.JComboBox<String> cmbtype;
    private javax.swing.JComboBox<String> cmbuser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_task;
    private javax.swing.JTextArea txt_descriptiontask;
    private app.bolivia.swing.JCTextField txt_nametask;
    private app.bolivia.swing.JCTextField txt_searchName;
    // End of variables declaration//GEN-END:variables
}
