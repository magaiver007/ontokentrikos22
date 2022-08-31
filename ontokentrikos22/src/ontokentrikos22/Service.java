
package ontokentrikos22;

public class Service extends Entity{
    public Service(int id,String name, String description){
        super(id, name, description);
    }

    @Override
    public String getDetails() {
        return "\tService";
    }
}
