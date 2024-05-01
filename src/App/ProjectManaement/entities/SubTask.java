package App.ProjectManaement.entities;


public class SubTask {
    
    
    private Integer SubtasId; 
    private String SubtaskName; 
    private String SubtaskDescription; 
    private Integer SubtaskEstimer; 
    private String SubtaskPriority; 
    private String SubtaskStatut; 
    private String SubtaskType; 

    public SubTask(Integer SubtasId, String SubtaskName, String SubtaskDescription, Integer SubtaskEstimer, String SubtaskPriority, String SubtaskStatut, String SubtaskType) {
        this.SubtasId = SubtasId;
        this.SubtaskName = SubtaskName;
        this.SubtaskDescription = SubtaskDescription;
        this.SubtaskEstimer = SubtaskEstimer;
        this.SubtaskPriority = SubtaskPriority;
        this.SubtaskStatut = SubtaskStatut;
        this.SubtaskType = SubtaskType;
    }

    /**
     * @return the SubtasId
     */
    public int getSubtasId() {
        return SubtasId;
    }

    /**
     * @param SubtasId the SubtasId to set
     */
    public void setSubtasId(Integer SubtasId) {
        this.SubtasId = SubtasId;
    }

    /**
     * @return the SubtaskName
     */
    public String getSubtaskName() {
        return SubtaskName;
    }

    /**
     * @param SubtaskName the SubtaskName to set
     */
    public void setSubtaskName(String SubtaskName) {
        this.SubtaskName = SubtaskName;
    }

    /**
     * @return the SubtaskDescription
     */
    public String getSubtaskDescription() {
        return SubtaskDescription;
    }

    /**
     * @param SubtaskDescription the SubtaskDescription to set
     */
    public void setSubtaskDescription(String SubtaskDescription) {
        this.SubtaskDescription = SubtaskDescription;
    }

    /**
     * @return the SubtaskEstimer
     */
    public int getSubtaskEstimer() {
        return SubtaskEstimer;
    }

    /**
     * @param SubtaskEstimer the SubtaskEstimer to set
     */
    public void setSubtaskEstimer(Integer SubtaskEstimer) {
        this.SubtaskEstimer = SubtaskEstimer;
    }

    /**
     * @return the SubtaskPriority
     */
    public String getSubtaskPriority() {
        return SubtaskPriority;
    }

    /**
     * @param SubtaskPriority the SubtaskPriority to set
     */
    public void setSubtaskPriority(String SubtaskPriority) {
        this.SubtaskPriority = SubtaskPriority;
    }

    /**
     * @return the SubtaskStatut
     */
    public String getSubtaskStatut() {
        return SubtaskStatut;
    }

    /**
     * @param SubtaskStatut the SubtaskStatut to set
     */
    public void setSubtaskStatut(String SubtaskStatut) {
        this.SubtaskStatut = SubtaskStatut;
    }

    /**
     * @return the SubtaskType
     */
    public String getSubtaskType() {
        return SubtaskType;
    }

    /**
     * @param SubtaskType the SubtaskType to set
     */
    public void setSubtaskType(String SubtaskType) {
        this.SubtaskType = SubtaskType;
    }

   

    
    
    
}
