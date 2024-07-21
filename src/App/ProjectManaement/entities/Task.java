
package App.ProjectManaement.entities;


public class Task {
    
    
    private Integer tasId; 
    private String taskName; 
    private String taskDescription; 
    private Integer taskEstimer; 
    private String taskPriority; 
    private String taskStatut; 
    private String taskType; 

    public Task(Integer tasId, String taskName, String taskDescription, Integer taskEstimer, String taskPriority, String taskStatut, String taskType) {
        this.tasId = tasId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskEstimer = taskEstimer;
        this.taskPriority = taskPriority;
        this.taskStatut = taskStatut;
        this.taskType = taskType;
    }

  
    public int getTasId() {
        return tasId;
    }

    
    public void setTasId(Integer tasId) {
        this.tasId = tasId;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the taskDescription
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * @param taskDescription the taskDescription to set
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * @return the taskEstimer
     */
    public int getTaskEstimer() {
        return taskEstimer;
    }

    /**
     * @param taskEstimer the taskEstimer to set
     */
    public void setTaskEstimer(Integer taskEstimer) {
        this.taskEstimer = taskEstimer;
    }

    /**
     * @return the taskPriority
     */
    public String getTaskPriority() {
        return taskPriority;
    }

    /**
     * @param taskPriority the taskPriority to set
     */
    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    /**
     * @return the taskStatut
     */
    public String getTaskStatut() {
        return taskStatut;
    }

    /**
     * @param taskStatut the taskStatut to set
     */
    public void setTaskStatut(String taskStatut) {
        this.taskStatut = taskStatut;
    }

    /**
     * @return the taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * @param taskType the taskType to set
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    

    
   
}
