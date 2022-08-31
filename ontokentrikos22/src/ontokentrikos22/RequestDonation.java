
package ontokentrikos22;

public class RequestDonation {
    private Entity entity;
    private double quantity;
    
    public RequestDonation(Entity entity, double quantity){
        this.setEntity(entity);
        this.setQuantity(quantity);
    }
    
  
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
    
    public void setEntity(Entity entity){
        this.entity = entity;
    }
    
    public Entity getEntity(){
        return entity;
    }
    
    public double getQuantity(){
        return this.quantity;
    }
    
    @Override
    public String toString(){
        String det = this.entity+"\t";
        return entity+", Quantity: "+quantity+"\n";
    }
}
