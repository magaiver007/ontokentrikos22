
package ontokentrikos22;

public class Admin extends User{
    private boolean isAdmin = true;
    
    public Admin (String name, String phone){
        super (name, phone);
    }
    
    public void setIsAdmin(){
        isAdmin = true;
    }
}
