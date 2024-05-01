
package App.ProjectManaement.entities;

import java.util.Date;


public class Project {
      private Integer projectID;
    private String projectName;
    private String projectDescription;
    private Date projectStart;
    private Date projectEnd;

    public Project(int projectID, String projectName, String projectDescription, Date projectStart, Date projectEnd) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStart = projectStart;
        this.projectEnd = projectEnd;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }
    

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    public Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(Date projectStart) {
        this.projectStart = projectStart;
    }
    

    public Date getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(Date projectEnd) {
        this.projectEnd = projectEnd;
    }
    
    

    


}
