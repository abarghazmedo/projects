
package App.projectManagement.form;


import static App.projectManagement.form.Project.emailProject;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;


public class Dashbord extends javax.swing.JFrame {
   
    public void icon(){
              try {
             Image img = ImageIO.read(getClass().getResource("/adminIcons/code.png"));
             this.setIconImage(img);
         } catch (Exception e) {
             
         }
    }
    static String userNameU;
    
    
    
     
    
    static ArrayList<String> permissionData=null;

    public Dashbord() {
        initComponents();
        setProjectdetailToTable();
        setdatatocarduser();
        setdatatocardproject();
        icon();
        managePermissionLabel(permissionData);
        setUserInLabel();
    }

    public Dashbord(String email,String userName) {
        userNameU=userName;
        initComponents();
        setProjectdetailToTable();
        setdatatocarduser();
        setdatatocardproject();
        icon();
        permissionData =  DBconnection.permission(email);      
        managePermissionLabel(permissionData);
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
            jPanel15.setVisible(true);

        }
        if (per.contains("SubTask hide")) {
            jPanel15.setVisible(false);


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
    
    
    
    
//    public void managePermission() {
//
//        if (permissionData.contains("Project edit") || permissionData.contains("Project view")) {
//            Project project = new Project();
//            project.setVisible(true);
//        //    dispose();
//
//        }
//        if (permissionData.contains("Project hide")) {
//            new Project().setVisible(false);
//            jPanel5.setVisible(false);
////          this.dispose();
//
//        }
//        if (permissionData.contains("Task edit") || permissionData.contains("Task view")) {
//            Task task = new Task();
//            task.setVisible(true);
//        //    dispose();
//
//        }
//        if (permissionData.contains("Task hide")) {
//            new Task().setVisible(false);
//            jPanel14.setVisible(false);
////                        this.dispose();
//
//        }
//        if (permissionData.contains("SubTask edit") || permissionData.contains("SubTask view")) {
//            SubTask subtask = new SubTask();
//            subtask.setVisible(true);
//        //    dispose();
//
//        }
//        if (permissionData.contains("SubTask hide")) {
//            new SubTask().setVisible(false);
//            jPanel5.setVisible(false);
//
//
//        }
//        if (permissionData.contains("User edit") || permissionData.contains("User view")) {
//            User user = new User();
//            user.setVisible(true);
//        //    dispose();
//
//        }
//        if (permissionData.contains("User hide")) {
//            new User().setVisible(false);
//            jPanel6.setVisible(false);
//
//
//        }
//        if (permissionData.contains("Role edit") || permissionData.contains("Role view")) {
//            Role role = new Role();
//            role.setVisible(true);
//         //   dispose();
//
//        }
//        if (permissionData.contains("Role hide")) {
//            new Role().setVisible(false);
//            jPanel7.setVisible(false);
////                        this.dispose();
//            //System.out.println(per.contains(new String("Role hide")));
//        }
//    }


     


    
    //SELECT p.projectName, COUNT(*) AS ToDoTaskCount FROM project p JOIN task t ON p.projectID = t.projectID WHERE t.taskStatut = 'To do' GROUP BY p.projectName;
      public void setdatatocarduser(){
         
  
        try {
            Connection con = DBconnection.getConnection();
         Statement st = con.createStatement();
         ResultSet  rs = st.executeQuery("SELECT * FROM `userp` ");
            rs.last();
            lblnmbrUser1.setText(Integer.toString(rs.getRow()));
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
         public void setdatatocardproject(){
         
  
        try {
            Connection con = DBconnection.getConnection();
         Statement st = con.createStatement();
         ResultSet  rs = st.executeQuery("SELECT * FROM `project` ");
            rs.last();
            lblnmbrProject1.setText(Integer.toString(rs.getRow()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
    String roleName = getRoleName();
    String emailProject =Project.emailProject;

    // Clear the table first
    DefaultTableModel model = (DefaultTableModel) tbl_projects.getModel();
    model.setRowCount(0); // Clear existing rows

    if (!"Manager".equals(roleName) && !"Admin".equals(roleName)) {
        // If the user is not a Manager or Admin, do nothing
        return;
    }

    String query = "SELECT " +
                   "    p.projectName, " +
                   "    COUNT(CASE WHEN t.taskStatut = 'To do' THEN 1 END) AS ToDo, " +
                   "    COUNT(CASE WHEN t.taskStatut = 'In Progress' THEN 1 END) AS InProgress, " +
                   "    COUNT(CASE WHEN t.taskStatut = 'Done' THEN 1 END) AS Done, " +
                   "    CONCAT(" +
                   "        FLOOR(" +
                   "            (COUNT(CASE WHEN t.taskStatut = 'Done' THEN 1 END) * 100.0 / NULLIF(COUNT(*), 0))" +
                   "        ), " +
                   "        '%'" +
                   "    ) AS ProgressPercentage " +
                   "FROM " +
                   "    project p " +
                   "JOIN " +
                   "    task t ON p.projectID = t.projectID ";

    if ("Manager".equals(roleName)) {
        query += "INNER JOIN userp u ON p.UserID = u.UserID WHERE u.UserEmail = ?";
    }

    query += " GROUP BY p.projectName";

    try (Connection con = DBconnection.getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {

        if ("Manager".equals(roleName)) {
            pst.setString(1, emailProject);
        }

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String nameproject = rs.getString(1);
                String todotask = rs.getString(2);
                String inprogresstask = rs.getString(3);
                String donetask = rs.getString(4);
                String progress = rs.getString(5);

                Object[] obj = {nameproject, todotask, inprogresstask, donetask, progress};
                model.addRow(obj);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 
   public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_projects.getModel();
        model.setRowCount(0);
    }


      
 


    /*  public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "IPhone 5s" , new Double( 20 ) );  
      barDataset.setValue( "SamSung Grand" , new Double( 20 ) );   
      barDataset.setValue( "MotoG" , new Double( 40 ) );    
      barDataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
      
      //create chart
       JFreeChart piechart = ChartFactory.createPieChart("mobile sales",barDataset, false,true,false);//explain
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("IPhone 5s", new Color(255,255,102));
        piePlot.setSectionPaint("SamSung Grand", new Color(102,255,102));
        piePlot.setSectionPaint("MotoG", new Color(255,102,153));
        piePlot.setSectionPaint("Nokia Lumia", new Color(0,204,204));
      
       
        piePlot.setBackgroundPaint(new Color(204,204,204));
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelpechart.removeAll();
        panelpechart.add(barChartPanel, BorderLayout.CENTER);
        panelpechart.validate();
    }

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
        jLabel10 = new javax.swing.JLabel();
        usershow = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        lblnmbrProject1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_projects = new rojeru_san.complementos.RSTableMetro();
        jLabel11 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        lblnmbrUser1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

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

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/male_user_50px.png"))); // NOI18N
        jLabel2.setText("Welcom , ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, -1, 50));

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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 81, 98));
        jLabel10.setText("-");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, -1, 30));

        usershow.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        usershow.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(usershow, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 20, 70, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 70));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 206, 209));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icondashbord222.png"))); // NOI18N
        jLabel6.setText("   Tableau de bord");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 260, 60));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 81, 98));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/ProejctIcon.png"))); // NOI18N
        jLabel8.setText("   Projet");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, -1));

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

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 81, 98));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/iconTask.png"))); // NOI18N
        jLabel14.setText("   Sous-Tâche");
        jPanel15.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, -1));

        jPanel3.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 260, 60));

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

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 260, 700));

        jPanel10.setBackground(new java.awt.Color(248, 248, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(119, 136, 153));
        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 15, 0, 0, new java.awt.Color(255, 255, 255)));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblnmbrProject1.setBackground(new java.awt.Color(255, 255, 255));
        lblnmbrProject1.setFont(new java.awt.Font("Dialog", 1, 50)); // NOI18N
        lblnmbrProject1.setForeground(new java.awt.Color(102, 102, 102));
        lblnmbrProject1.setText("53");
        jPanel12.add(lblnmbrProject1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 130, 70));

        jLabel16.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("PROJETS");
        jPanel12.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 180, 50));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 50)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/pppppppp.png"))); // NOI18N
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 80, 80));

        jPanel10.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 330, 160));

        tbl_projects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Projets ", "à faire", "encore", "complete", "progrès"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_projects.setColorBackgoundHead(new java.awt.Color(0, 206, 209));
        tbl_projects.setColorFilasForeground1(new java.awt.Color(128, 128, 128));
        tbl_projects.setColorFilasForeground2(new java.awt.Color(128, 128, 128));
        tbl_projects.setColorSelBackgound(new java.awt.Color(0, 81, 98));
        tbl_projects.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        tbl_projects.setRowHeight(40);
        jScrollPane1.setViewportView(tbl_projects);
        if (tbl_projects.getColumnModel().getColumnCount() > 0) {
            tbl_projects.getColumnModel().getColumn(0).setResizable(false);
            tbl_projects.getColumnModel().getColumn(1).setResizable(false);
            tbl_projects.getColumnModel().getColumn(2).setResizable(false);
            tbl_projects.getColumnModel().getColumn(3).setResizable(false);
            tbl_projects.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel10.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 930, 330));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 81, 98));
        jLabel11.setText("Projets ");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 81, 98)));
        jPanel10.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 160, 50));

        jPanel16.setBackground(new java.awt.Color(240, 128, 128));
        jPanel16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 15, 0, 0, new java.awt.Color(255, 255, 255)));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblnmbrUser1.setBackground(new java.awt.Color(255, 255, 255));
        lblnmbrUser1.setFont(new java.awt.Font("Dialog", 1, 50)); // NOI18N
        lblnmbrUser1.setForeground(new java.awt.Color(102, 102, 102));
        lblnmbrUser1.setText("100");
        jPanel16.add(lblnmbrUser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 130, 70));

        jLabel21.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Utilisateur");
        jPanel16.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 180, 50));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 50)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/mmmmmmmmmm.png"))); // NOI18N
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 80, 80));

        jPanel10.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 330, 160));

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1020, 700));

        setSize(new java.awt.Dimension(1279, 711));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
            new Project().setVisible(true);
            dispose();
//     //   this.managePermission();
//             if (permissionData.contains("Project edit") || permissionData.contains("Project view")) {
//            Project project = new Project();
//            project.setVisible(true);
//       //     dispose();
//
//        }
//        if (permissionData.contains("Project hide")) {
//            new Project().setVisible(false);
//            jPanel5.setVisible(false);
//          this.dispose();
//
//        }
   
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
     new Task().setVisible(true);
     dispose();

    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
    new User().setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
      new SubTask().setVisible(true);
        dispose();
       
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
       Login login=new Login();
       login.setVisible(true);
       dispose();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
       new Role().setVisible(true);
        dispose();

    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseEntered

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
          this.setExtendedState(Dashbord.ICONIFIED);
    }//GEN-LAST:event_jLabel10MouseClicked

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
            java.util.logging.Logger.getLogger(Dashbord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashbord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashbord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashbord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashbord().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JLabel lblnmbrProject1;
    private javax.swing.JLabel lblnmbrUser1;
    private rojeru_san.complementos.RSTableMetro tbl_projects;
    private javax.swing.JLabel usershow;
    // End of variables declaration//GEN-END:variables
}
