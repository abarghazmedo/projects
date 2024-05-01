/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Swingdeprojet;

import App.ProjectManaement.form.User;
import App.ProjectManaement.form.Dashbord;
import App.ProjectManaement.form.Role;
import App.ProjectManaement.form.Task;
import App.ProjectManaement.form.DBconnection;
import App.ProjectManaement.form.Permission;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Ibtissam
 */
public class Projectol extends javax.swing.JFrame {
    //  Map<String, String> map=new HashMap<>();
    String name_project, description_project,manager ;
     Date startDate,endDate;
    static int idProject,userId;
   //  Vector<Vector<String>> v;
      DefaultTableModel model;

     
    //-------------------------------------------------------------------------------------------------------------------    
     //   setComboBox();        
         //tbl_project.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());        
         //v.get(cmbuser.getSelectedIndex()).get(0);
   // --------------------------------------------------------------------------------------------------------------------
  
      
    //set combbox
    
        
//    public void setComboBox() {
//        
//        
//          DefaultComboBoxModel modelcombo=new DefaultComboBoxModel();
//            Connection con = DBconnection.getConnection();
//        try {
//            Statement st=null;
//           // Statement st = con.createStatement();//"SELECT * FROM `user_project` where userid in (select userid from role where [name] like 'manager')"
//           // ResultSet rs = st.executeQuery("SELECT UserID, UserName FROM userproject u, role r WHERE u.RoleId = r.RoleId AND r.NameRole = 'manager'");
//           //  Statement st=null;             
//               st=con.createStatement();
//              ResultSet rs=st.executeQuery(" SELECT UserID, UserName FROM userproject u, role r WHERE u.RoleId = r.RoleId AND r.NameRole = 'manager' ");
//            while (rs.next()) {
//           
//              modelcombo.addElement(new ComboItems(rs.getString(2),rs.getString(1)));    
//                
//            }
//           cmbuser.setModel(modelcombo);
//
//         // Move the closing of the connection outside the loop
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        try {
          
//            Connection con = DBconnection.getConnection();
//            Statement st = con.createStatement();//"SELECT * FROM `user_project` where userid in (select userid from role where [name] like 'manager')"
//            ResultSet rs = st.executeQuery("SELECT UserID, UserName FROM userproject u, role r WHERE u.RoleId = r.RoleId AND r.NameRole = 'manager'");
//         //    v=new Vector<Vector<String> >();
//            while (rs.next()) {
//                // Assuming the name_user column is of type VARCHAR
//           //     Vector<String> x=new Vector<>();
//           //     x.add( rs.getString("userid"));
//           //      x.add( rs.getString("Name_user"));
//            //    v.add(x);
//                String  UserId=rs.getString("UserId");
//                String  manager =rs.getString("UserName");               
//                cmbuser.addItem(new Item(UserId, manager).toString());
//                map.put(manager,UserId);
//            }
//
//            con.close(); // Move the closing of the connection outside the loop
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//----------------------------------------------------------------------------------------------------------------------------------
    public Projectol() {
      
       initComponents();
        setProjectToTable();
        bindcomboUser();
      } 
     
      
    //to set project table 
    public void setProjectToTable() {
        try {
            Connection con = DBconnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT p.projectID,p.ProjectName,p.ProjectDescription,p.StartDate,p.EndDate,u.UserName FROM `project` p ,userproject u WHERE u.UserID = p.UserID ");

            while (rs.next()) {
                int   idproject= rs.getInt(1);
                String nameproject = rs.getString(2);  // Assuming the name is in the second column
                String descriptionproject = rs.getString(3);
                String Startdate=rs.getString(4);
                String Enddate=rs.getString(5);
                String manager =rs.getString(6) ; 
                
               Object[] obj = { idproject ,nameproject, descriptionproject,Startdate,Enddate, manager};
                DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
                model.addRow(obj);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Add project to tabledata 
   public boolean addProject() {
    boolean isAdded = false;

    //idProject = Integer.parseInt(txt_idproject.getText());
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
        String sql = "insert into project (ProjectName, ProjectDescription, StartDate, EndDate, UserID) values (?, ?, ?, ?, ?)";
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


    //update project to table 
  public boolean updateProject(int id) {
    boolean isUpdate = false;
  //  idProject = Integer.parseInt(txt_idproject.getText());
    name_project = txt_nameproject.getText();
    description_project = txt_description.getText();
     Date startDate = Sdate.getDatoFecha();
    Date endDate = Edate.getDatoFecha();
    cmbuser.getSelectedItem().toString();
    
      Long startMillis = startDate.getTime();
    Long endMillis = endDate.getTime();

    java.sql.Date sqlStartDate = new java.sql.Date(startMillis);
    java.sql.Date sqlEndDate = new java.sql.Date(endMillis);

    try {
        Connection con = DBconnection.getConnection();
     //    DBconnection mq=new DBconnection();
        // Modified SQL query to exclude Id_Project"UPDATE project SET ProjectID=?, ProjectDescription=?, UserID=? WHERE ProjectID="+id+";"
        String sql ="UPDATE `project` SET  `ProjectName` = ?, `ProjectDescription` = ?,StartDate=?,EndDate=?, `UserID` = ? WHERE `project`.`projectID` = "+id+";" ;
        PreparedStatement prs = con.prepareStatement(sql);
      //   HashMap<String,Integer> map=mq.PopulateCombo();

        prs.setString(1, name_project);
        prs.setString(2, description_project);
        prs.setDate(3, sqlStartDate);
        prs.setDate(4, sqlEndDate);
        prs.setString(5,String.valueOf(userId));
        
        int updatedRowCount = prs.executeUpdate();
        isUpdate = updatedRowCount > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return isUpdate;
}


    //delete project to table 
    public boolean deleteProject(int id) {
        boolean isDelete = false;
  //    int  idProject = Integer.parseInt(txt_idproject.getText());

        try {
            Connection con = DBconnection.getConnection();
            String sql = "DELETE FROM `project` WHERE ProjectId ="+id+";";
            PreparedStatement prs = con.prepareStatement(sql);
            
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
        DefaultTableModel model = (DefaultTableModel) tbl_project.getModel();
        model.setRowCount(0);
    }
    public void clearForm(){   
        txt_nameproject.setText("");
        txt_description.setText("");
        cmbuser.setSelectedItem(null);
        
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
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
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_project = new rojeru_san.complementos.RSTableMetro();
        jPanel12 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_description = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btn_delete = new rojerusan.RSMaterialButtonCircle();
        btn_update = new rojerusan.RSMaterialButtonCircle();
        btn_add = new rojerusan.RSMaterialButtonCircle();
        jLabel16 = new javax.swing.JLabel();
        txt_nameproject = new javax.swing.JTextField();
        cmbuser = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        Sdate = new rojeru_san.componentes.RSDateChooser();
        Edate = new rojeru_san.componentes.RSDateChooser();
        jLabel22 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_nameUser = new javax.swing.JTextField();
        btn_delete1 = new rojerusan.RSMaterialButtonCircle();
        btn_update1 = new rojerusan.RSMaterialButtonCircle();
        btn_add1 = new rojerusan.RSMaterialButtonCircle();
        txt_emailUser = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_passwordUser = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txt_jobtitle = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();

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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/Global Technology2.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 210, 50));

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

        jPanel5.setBackground(new java.awt.Color(0, 206, 209));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconDash.png"))); // NOI18N
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

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 60));

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

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 260, 60));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 81, 98));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/permissionIcon.png"))); // NOI18N
        jLabel10.setText("   Permission");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 180, -1));

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 260, 60));

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

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 700));

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
                "Projects Id", "Name project", "Description", "Start ", "End", "Manager"
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

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 590, 480));

        jPanel12.setBackground(new java.awt.Color(0, 206, 209));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel12.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, -1, -1));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel12.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 81, 98));
        jLabel17.setText("Start date");
        jPanel12.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 110, 20));

        txt_description.setBackground(new java.awt.Color(0, 206, 209));
        txt_description.setColumns(20);
        txt_description.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_description.setForeground(new java.awt.Color(0, 81, 98));
        txt_description.setRows(5);
        txt_description.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        jScrollPane2.setViewportView(txt_description);

        jPanel12.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, 140));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 81, 98));
        jLabel19.setText("Manager");
        jPanel12.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 100, 20));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 40, 40));

        btn_delete.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel12.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 510, 110, 50));

        btn_update.setBackground(new java.awt.Color(255, 153, 0));
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel12.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, 110, 50));

        btn_add.setBackground(new java.awt.Color(0, 51, 255));
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel12.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 110, 50));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 81, 98));
        jLabel16.setText("Name Project");
        jPanel12.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 100, 20));

        txt_nameproject.setBackground(new java.awt.Color(0, 206, 209));
        txt_nameproject.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_nameproject.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameproject.setActionCommand("<Not Set>");
        txt_nameproject.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_nameproject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameprojectActionPerformed(evt);
            }
        });
        jPanel12.add(txt_nameproject, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 230, 30));

        cmbuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbuserActionPerformed(evt);
            }
        });
        jPanel12.add(cmbuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 452, 250, 30));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 81, 98));
        jLabel21.setText("Description");
        jPanel12.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 80, 20));

        Sdate.setPlaceholder("");
        jPanel12.add(Sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 260, 30));

        Edate.setPlaceholder("");
        jPanel12.add(Edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, 260, 30));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 81, 98));
        jLabel22.setText("End date");
        jPanel12.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, 110, 20));

        jPanel15.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 370, 570));

        jPanel11.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 600, 570));

        jPanel16.setBackground(new java.awt.Color(0, 206, 209));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("User Name");
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 20));

        txt_nameUser.setBackground(new java.awt.Color(0, 206, 209));
        txt_nameUser.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_nameUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_nameUser.setActionCommand("<Not Set>");
        txt_nameUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_nameUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameUserActionPerformed(evt);
            }
        });
        jPanel16.add(txt_nameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 230, 30));

        btn_delete1.setBackground(new java.awt.Color(255, 0, 51));
        btn_delete1.setText("Delete");
        btn_delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete1ActionPerformed(evt);
            }
        });
        jPanel16.add(btn_delete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 510, 110, 50));

        btn_update1.setBackground(new java.awt.Color(255, 153, 0));
        btn_update1.setText("Update");
        btn_update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update1ActionPerformed(evt);
            }
        });
        jPanel16.add(btn_update1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, 110, 50));

        btn_add1.setBackground(new java.awt.Color(0, 51, 255));
        btn_add1.setText("Add");
        btn_add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add1ActionPerformed(evt);
            }
        });
        jPanel16.add(btn_add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 110, 50));

        txt_emailUser.setBackground(new java.awt.Color(0, 206, 209));
        txt_emailUser.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_emailUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_emailUser.setActionCommand("<Not Set>");
        txt_emailUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_emailUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailUserActionPerformed(evt);
            }
        });
        jPanel16.add(txt_emailUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 230, 30));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 81, 98));
        jLabel24.setText("E-mail");
        jPanel16.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 40, 20));

        txt_passwordUser.setBackground(new java.awt.Color(0, 206, 209));
        txt_passwordUser.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_passwordUser.setForeground(new java.awt.Color(0, 81, 98));
        txt_passwordUser.setActionCommand("<Not Set>");
        txt_passwordUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_passwordUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordUserActionPerformed(evt);
            }
        });
        jPanel16.add(txt_passwordUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 230, 30));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 81, 98));
        jLabel25.setText("Password");
        jPanel16.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 70, 20));

        txt_jobtitle.setBackground(new java.awt.Color(0, 206, 209));
        txt_jobtitle.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txt_jobtitle.setForeground(new java.awt.Color(0, 81, 98));
        txt_jobtitle.setActionCommand("<Not Set>");
        txt_jobtitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_jobtitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jobtitleActionPerformed(evt);
            }
        });
        jPanel16.add(txt_jobtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 230, 30));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 81, 98));
        jLabel27.setText("Job Title ");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 100, 20));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 81, 98));
        jLabel26.setText("Role");
        jPanel16.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 40, 20));

        jPanel11.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 570));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 970, 570));

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

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (deleteProject(idProject)== true) {
            JOptionPane.showMessageDialog(this, "Project Delet succesful");
            clearTable();
            setProjectToTable();
             clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Project Delete  failed !");
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        if (updateProject(idProject) == true) {
            JOptionPane.showMessageDialog(this, "Project Update succesful");
            clearTable();
            setProjectToTable();
             clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Project update failed !");
        }

    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (addProject() == true) {
            JOptionPane.showMessageDialog(this, "Project created succesful");
            clearTable();
            setProjectToTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Project created failed !");
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void tbl_projectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_projectMouseClicked
        int rowN = tbl_project.getSelectedRow();
        TableModel model = tbl_project.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Assuming the first column contains the project ID
        idProject =Integer.parseInt(model.getValueAt(rowN, 0).toString()); 
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
       Task task=new Task();
       task.setVisible(true);
       dispose();
       
    }//GEN-LAST:event_jPanel14MouseClicked

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

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        Permission permission=new Permission();
       permission.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel8MouseClicked

    private void txt_nameprojectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameprojectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameprojectActionPerformed

    public void bindcomboUser() {
       DBconnection mq=new DBconnection();
       HashMap<String,Integer> map=mq.PopulateCombouser();
       for(String s :map.keySet()){
           cmbuser.addItem(s);
       }
        
    }
    private void cmbuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbuserActionPerformed
        
        DBconnection mq=new DBconnection();
        HashMap<String,Integer> map=mq.PopulateCombouser();
         userId=map.get(cmbuser.getSelectedItem().toString());
        //   System.out.println(userId);
    }//GEN-LAST:event_cmbuserActionPerformed

    private void txt_nameUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameUserActionPerformed

    private void btn_delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete1ActionPerformed
        if (deleteUser(id_user)== true) {
            JOptionPane.showMessageDialog(this, "User Delet succesful");
            clearTable();
            setUserToTable();
            clearform();
        } else {
            JOptionPane.showMessageDialog(this, "User Delete  failed !");
        }
    }//GEN-LAST:event_btn_delete1ActionPerformed

    private void btn_update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update1ActionPerformed
        if (updateUser(id_user)== true) {
            JOptionPane.showMessageDialog(this, "User Update succesful");
            clearTable();
            setUserToTable();
            clearform();
        } else {
            JOptionPane.showMessageDialog(this, "User update failed !");
        }
    }//GEN-LAST:event_btn_update1ActionPerformed

    private void btn_add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add1ActionPerformed
        if (addUser()== true) {
            JOptionPane.showMessageDialog(this, "User created succesful");
            clearTable();
            setUserToTable();
            clearform();
        } else {
            JOptionPane.showMessageDialog(this, "User created failed !");
        }
    }//GEN-LAST:event_btn_add1ActionPerformed

    private void txt_emailUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailUserActionPerformed

    private void txt_passwordUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordUserActionPerformed

    private void txt_jobtitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jobtitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jobtitleActionPerformed

    /**
     * @param args the command line arguments
     */
  //  public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Projectol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Projectol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Projectol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Projectol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Projectol().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.componentes.RSDateChooser Edate;
    private rojeru_san.componentes.RSDateChooser Sdate;
    private rojerusan.RSMaterialButtonCircle btn_add;
    private rojerusan.RSMaterialButtonCircle btn_add1;
    private rojerusan.RSMaterialButtonCircle btn_delete;
    private rojerusan.RSMaterialButtonCircle btn_delete1;
    private rojerusan.RSMaterialButtonCircle btn_update;
    private rojerusan.RSMaterialButtonCircle btn_update1;
    private javax.swing.JComboBox<String> cmbuser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private rojeru_san.complementos.RSTableMetro tbl_project;
    private javax.swing.JTextArea txt_description;
    private javax.swing.JTextField txt_emailUser;
    private javax.swing.JTextField txt_jobtitle;
    private javax.swing.JTextField txt_nameUser;
    private javax.swing.JTextField txt_nameproject;
    private javax.swing.JTextField txt_passwordUser;
    // End of variables declaration//GEN-END:variables
}


