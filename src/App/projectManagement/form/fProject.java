
package App.projectManagement.form;


import static App.projectManagement.form.Project.emailProject;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class fProject extends javax.swing.JFrame {

    String name_project, description_project, manager;
    Date startDate, endDate;
    static int idProject, userId;
    DefaultTableModel model;
    
    
    public fProject() {
        initComponents();
        setProjectdetailToTable();
        bindcomboUser ();
    }
    
    //validaion project if date empty 
     public boolean validateProject() {
   
        Date startDate = dateFromdate.getDatoFecha();
        Date endDate = dateTodate.getDatoFecha();
           if (startDate == null) {
            JOptionPane.showMessageDialog(this, "Please Enter  From Date");
            return false;
        }
        if (endDate == null) {
            JOptionPane.showMessageDialog(this, "Please Enter To Date");
            return false;
        }
        return true;
        
     }
 
          public void searchName(String str) {
    model = (DefaultTableModel) tbl_project.getModel();
    TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
    tbl_project.setRowSorter(trs);
    
    // Check if the input string is null or empty
    if (str == null || str.trim().isEmpty()) {
        trs.setRowFilter(null); // No filtering
    } else {
        // Use Pattern.quote to escape any special characters in the input string
        String sanitizedStr = Pattern.quote(str);
        trs.setRowFilter(RowFilter.regexFilter(sanitizedStr));
    }
}
       
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
        model.setRowCount(0);
    }
           
             
      public void clearForm(){   
        txt_nameproject.setText("");
        txt_description.setText("");
        Sdate.setDatoFecha(null);
        Edate.setDatoFecha(null);
        cmbuser.setSelectedIndex(0);
        dateFromdate.setDatoFecha(null);
        dateTodate.setDatoFecha(null);
        
    }
    

      //show details project in table 
   public void setProjectdetailToTable() {
    try (Connection con = DBconnection.getConnection()) {
        String query = "SELECT p.projectID, p.projectName, p.projectDescription, p.Startdate, p.ENDdate, u.UserName " +
                       "FROM `project` p " +
                       "INNER JOIN `userp` u ON u.UserID = p.UserID " +
                       "WHERE u.UserEmail = ?";

            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, String.valueOf(emailProject));
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
    
     public void search(){
        Date startDate = dateFromdate.getDatoFecha();
        Date endDate = dateTodate.getDatoFecha();
        Long startMillis = startDate.getTime();
        Long endMillis = endDate.getTime();

        java.sql.Date sqlStartDate = new java.sql.Date(startMillis);
        java.sql.Date sqlEndDate = new java.sql.Date(endMillis);
        try {
             Connection con = DBconnection.getConnection();
            DBconnection mq = new DBconnection();
           // SELECT p.projectID,p.projectName,p.projectDescription,p.Startdate,p.ENDdate,u.UserName FROM `project` p ,userp u WHERE u.UserID = p.UserID 
           // String sql = "SELECT * FROM project WHERE Startdate BETWEEN ? AND ?";
            String sql = "SELECT p.projectID,p.projectName,p.projectDescription,p.Startdate,p.ENDdate,u.UserName FROM `project` p ,"
                    + "userp u WHERE Startdate BETWEEN ? AND ? And u.UserID = p.UserID ";
            PreparedStatement prs = con.prepareStatement(sql);
            
             prs.setDate(1, sqlStartDate);
            prs.setDate(2, sqlEndDate);
            
           ResultSet rs=prs.executeQuery();
           
           if (rs.next()==false){
               JOptionPane.showMessageDialog(this , "No Record Found");
           }else{
              while (rs.next()) {
                int idproject = rs.getInt(1);
                String nameproject = rs.getString(2);
                String descriptionproject = rs.getString(3);
                String Startdate = rs.getString(4);
                String Enddate = rs.getString(5);
                String manager = rs.getString(6);

                Object[] obj = {idproject, nameproject, descriptionproject, Startdate, Enddate, manager};
                DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
                model.addRow(obj);

            } 
           }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
     
       //show first row
    public void showToFirstRow() {
        if (tbl_project.getRowCount() > 0) {
            tbl_project.setRowSelectionInterval(0, 0);
            SetIntextfeild(0);
        }
    }

    //show Next row
    public void showToNextRow() {
        int selectedRow = tbl_project.getSelectedRow();
        int rowCount = tbl_project.getRowCount();
        if (selectedRow < rowCount - 1) {
            tbl_project.setRowSelectionInterval(selectedRow + 1, selectedRow + 1);
            SetIntextfeild(selectedRow + 1);
        }
    }
    
    //show Previous row
    public void showToPreviousRow() {
        int selectedRow = tbl_project.getSelectedRow();
        if (selectedRow > 0) {
            tbl_project.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
            SetIntextfeild(selectedRow - 1);
        }
    }
    
    //show Last row
    public void showToLastRow() {
        int rowCount = tbl_project.getRowCount();
        if (rowCount > 0) {
            tbl_project.setRowSelectionInterval(rowCount - 1, rowCount - 1);
            SetIntextfeild(rowCount - 1);
        }
    }
      
    //  set index Data from table 
    public void SetIntextfeild(int row) {

               int rowN = tbl_project.getSelectedRow();
        TableModel model = tbl_project.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        idProject = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameproject.setText( model.getValueAt(rowN, 1).toString());
        txt_nameproject.setEditable(false);
        txt_description.setText( model.getValueAt(rowN, 2).toString());
        txt_description.setEditable(false);
        String startdate = model.getValueAt(rowN, 3).toString();
        String enddate = model.getValueAt(rowN, 4).toString();      
        cmbuser.setSelectedItem(model.getValueAt(rowN, 5).toString());
        cmbuser.setEnabled(false);
        try {
            startDate = dateFormat.parse(startdate);          
            endDate = dateFormat.parse(enddate);
            Sdate.setDatoFecha(startDate);         
            Edate.setDatoFecha(endDate);
            Sdate.setEnabled(false);
            Edate.setEnabled(false);
         

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        txt_nameproject = new app.bolivia.swing.JCTextField();
        jLabel28 = new javax.swing.JLabel();
        btn_firs = new rojerusan.RSButtonMetro();
        btn_next = new rojerusan.RSButtonMetro();
        btn_back = new rojerusan.RSButtonMetro();
        btn_last = new rojerusan.RSButtonMetro();
        jLabel10 = new javax.swing.JLabel();
        btn_search = new rojerusan.RSButtonMetro();
        dateTodate = new rojeru_san.componentes.RSDateChooser();
        dateFromdate = new rojeru_san.componentes.RSDateChooser();
        txt_searchName = new app.bolivia.swing.JCTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_imprimer = new rojeru_san.complementos.RSButtonHover();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_project.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name ", "Description", "Démarrer", "Fin", "Manager"
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

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 1000, 240));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sdate.setColorBackground(new java.awt.Color(0, 81, 98));
        Sdate.setColorDiaActual(new java.awt.Color(0, 206, 209));
        Sdate.setColorForeground(new java.awt.Color(255, 255, 255));
        Sdate.setPlaceholder("Démarrer le projet");
        jPanel16.add(Sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 260, 30));

        Edate.setColorBackground(new java.awt.Color(0, 81, 98));
        Edate.setColorDiaActual(new java.awt.Color(0, 206, 209));
        Edate.setColorForeground(new java.awt.Color(255, 255, 255));
        Edate.setPlaceholder("Fin du projet");
        jPanel16.add(Edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 260, 30));

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

        jPanel16.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 280, 110));

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
        jPanel16.add(cmbuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 260, 30));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 81, 98));
        jLabel25.setText("Démarrer le projet");
        jPanel16.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 130, 30));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 81, 98));
        jLabel26.setText("Manager");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 90, 30));

        txt_nameproject.setBackground(new java.awt.Color(255, 255, 255));
        txt_nameproject.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_nameproject.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameproject.setPlaceholder("entre project name");
        txt_nameproject.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nameprojectFocusLost(evt);
            }
        });
        jPanel16.add(txt_nameproject, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 240, -1));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 81, 98));
        jLabel28.setText("Fin du projet");
        jPanel16.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 110, 30));

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
        jPanel16.add(btn_firs, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 120, 30));

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
        jPanel16.add(btn_next, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 60, 120, 30));

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
        jPanel16.add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 100, 120, 30));

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
        jPanel16.add(btn_last, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 150, 120, 30));

        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1010, 200));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear (1).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
        });
        jPanel15.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        btn_search.setBackground(new java.awt.Color(0, 81, 98));
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        btn_search.setText("Recherche");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel15.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 100, 30));

        dateTodate.setColorBackground(new java.awt.Color(0, 81, 98));
        dateTodate.setColorDiaActual(new java.awt.Color(0, 206, 209));
        dateTodate.setColorForeground(new java.awt.Color(255, 255, 255));
        dateTodate.setPlaceholder("Fin du projet");
        jPanel15.add(dateTodate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 170, 30));

        dateFromdate.setColorBackground(new java.awt.Color(0, 81, 98));
        dateFromdate.setColorDiaActual(new java.awt.Color(0, 206, 209));
        dateFromdate.setColorForeground(new java.awt.Color(255, 255, 255));
        dateFromdate.setPlaceholder("Date de début");
        jPanel15.add(dateFromdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 170, 30));

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
        jPanel15.add(txt_searchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 150, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Les Fichier de Projects");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel15.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Ampeross-Qetto-2-Search.16.png"))); // NOI18N
        jPanel15.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 20, 20));

        btn_imprimer.setBackground(new java.awt.Color(255, 153, 0));
        btn_imprimer.setText("Imprimer");
        btn_imprimer.setColorHover(new java.awt.Color(255, 51, 51));
        btn_imprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimerActionPerformed(evt);
            }
        });
        jPanel15.add(btn_imprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 260, 190, 30));

        getContentPane().add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1020, 550));

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
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, 20, -1));

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
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 20, 20, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 70));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_projectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_projectMouseClicked
             int rowN = tbl_project.getSelectedRow();
        TableModel model = tbl_project.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        idProject = Integer.parseInt(model.getValueAt(rowN, 0).toString());
        txt_nameproject.setText( model.getValueAt(rowN, 1).toString());
        txt_nameproject.setEditable(false);
        txt_description.setText( model.getValueAt(rowN, 2).toString());
        txt_description.setEditable(false);
        String startdate = model.getValueAt(rowN, 3).toString();
        String enddate = model.getValueAt(rowN, 4).toString();      
        cmbuser.setSelectedItem(model.getValueAt(rowN, 5).toString());
        cmbuser.setEnabled(false);
        try {
            startDate = dateFormat.parse(startdate);          
            endDate = dateFormat.parse(enddate);
            Sdate.setDatoFecha(startDate);         
            Edate.setDatoFecha(endDate);
            Sdate.setEnabled(false);
            Edate.setEnabled(false);
         

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbl_projectMouseClicked

    
       //combo user add user be in combo
    public void bindcomboUser () {
       DBconnection mq=new DBconnection();
       HashMap<String,Integer> map=mq.PopulateCombouser();
       for(String s :map.keySet()){
           cmbuser.addItem(s);
       }
        
    }
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

    private void txt_nameprojectFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nameprojectFocusLost

    }//GEN-LAST:event_txt_nameprojectFocusLost

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        clearTable();
        setProjectdetailToTable();
        clearForm();
        
        
 
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        Project project = new Project();
        project.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        Project project = new Project();
        project.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
         if (validateProject() == true) {
            clearTable();
            search();
        }
    }//GEN-LAST:event_btn_searchActionPerformed

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

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseEntered

    private void btn_imprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimerActionPerformed
        try {
            // Load the JasperReport template
            JasperDesign jasdi = JRXmlLoader.load("C:\\Users\\Mohamed\\JaspersoftWorkspace\\MyReports\\FichierProject.jrxml");

            // Get the selected row from the jTable
            int selectedRow = tbl_project.getSelectedRow();

            // Check if the user selected any row
            if (selectedRow == -1) {
                // If nothing is selected, show an error message
                JOptionPane.showMessageDialog(null, "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                // Construct the SQL query dynamically based on the user's selection
                String id = tbl_project.getValueAt(selectedRow, 0).toString(); // Assuming the first column contains the PersID
                String sql = "SELECT p.projectID,p.projectName,p.projectDescription,p.Startdate,p.ENDdate,u.UserName FROM `project` p ,userp u WHERE u.UserID = p.UserID AND projectID= '" + id + "'";

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

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
   this.setExtendedState(fProject.ICONIFIED);
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
            java.util.logging.Logger.getLogger(fProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fProject().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.componentes.RSDateChooser Edate;
    private rojeru_san.componentes.RSDateChooser Sdate;
    private rojerusan.RSButtonMetro btn_back;
    private rojerusan.RSButtonMetro btn_firs;
    private rojeru_san.complementos.RSButtonHover btn_imprimer;
    private rojerusan.RSButtonMetro btn_last;
    private rojerusan.RSButtonMetro btn_next;
    private rojerusan.RSButtonMetro btn_search;
    private javax.swing.JComboBox<String> cmbuser;
    private rojeru_san.componentes.RSDateChooser dateFromdate;
    private rojeru_san.componentes.RSDateChooser dateTodate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private rojeru_san.complementos.RSTableMetro tbl_project;
    private javax.swing.JTextArea txt_description;
    private app.bolivia.swing.JCTextField txt_nameproject;
    private app.bolivia.swing.JCTextField txt_searchName;
    // End of variables declaration//GEN-END:variables
}
