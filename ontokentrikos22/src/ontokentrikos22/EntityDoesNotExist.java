
package ontokentrikos22;

public class EntityDoesNotExist extends Exception{
    public EntityDoesNotExist(){
        super("Entity with id does not exist");
    }
}
