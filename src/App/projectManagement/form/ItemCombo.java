
package App.projectManagement.form;


public class ItemCombo {
      private int value;
       private String key;
       
    public ItemCombo(int value,String key) {
       this.value=value;
       this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
