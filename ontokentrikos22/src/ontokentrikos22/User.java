
package ontokentrikos22;

public class User {
    private String name;
    private String phone;
    public boolean equals = false;
    public boolean equalsWithPhone = false;
    
    
    public User(){
        this.equalsWithPhone = false;
        this.setName("xxxxxxxx");
        this.setPhone("00000000");
    }
    
    public User(String name, String phone){
        this.equalsWithPhone = false;
        this.setName(name);
        this.setPhone(phone);
    }
    
   
    @Override
    public String toString(){
        return "Name: "+ this.name+" Phone: "+this.phone;
    }
    
    public String getPhone(){
        return phone;
    }
    
    private void setName(String name){
        this.name = name;
    }
    
    private void setPhone(String phone){
        this.phone = phone;
    }
    
    public boolean equals(User user){
        if(this.name.equalsIgnoreCase(user.name) && this.phone.equalsIgnoreCase(user.phone)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean equalsWithPhone(String phone){
        if(this.phone.equalsIgnoreCase(phone)){
            return true;
        }
        else{
            return false;
        }
    }
}
