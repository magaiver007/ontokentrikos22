package ontokentrikos22;

public class UserInListException extends Exception{
    public UserInListException(){
        super("User already exists");
    }
}
