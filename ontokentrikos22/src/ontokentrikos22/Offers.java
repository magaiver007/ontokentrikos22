
package ontokentrikos22;

import java.util.Iterator;
import java.util.List;

public class Offers extends RequestDonationList{
    public Offers(){
        super();
    }
    
    public void commit(RequestDonationList currentDonations, List<Entity> entitiesList){
        Iterator<RequestDonation> iterator = super.getList().iterator();
        while(iterator.hasNext()){
            currentDonations.add(iterator.next(), entitiesList);
        }
        super.reset();
    }
}
