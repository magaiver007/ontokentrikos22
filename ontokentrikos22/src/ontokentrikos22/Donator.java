
package ontokentrikos22;


import java.util.List;

public class Donator extends User{
    private final Offers offers;
    
    public Donator(String name, String phone){
        super(name, phone);
        this.offers = new Offers();
    }

    public void monitor(){
        offers.monitor();
    }
    
    public List<RequestDonation> getOffers(){
        return offers.getList();
    }
    
    public void modify(int index, double quantity){
        offers.modify(index, quantity);
    }
    
    public void reset(){
        offers.reset();
    }
    public void remove(int index){
        offers.remove(index);
    }
    
    public boolean add(RequestDonation donation, List<Entity> entitiesList){
        return offers.add(donation, entitiesList);
    }
    
  public void commit(RequestDonationList current, List<Entity> entities){
      offers.commit(current, entities);
  }
}

