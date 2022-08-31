
package ontokentrikos22;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Beneficiary extends User{
    private RequestDonationList received;
    private Requests requests;
    private int noPersons ;
    
    public Beneficiary(String name, String phone, int noPersons){
        super(name, phone);
        this.noPersons = 1;
        requests = new Requests();
        received= new RequestDonationList();
        setNoPersons(noPersons);
    }
    
    public List<RequestDonation> getRequests(){
        return requests.getList();
    }
    
    private void setNoPersons(int noPersons){
        this.noPersons = noPersons;
    }
    
    public int getNoPersons(){
        return noPersons;
    }
    
    public void monitor(){
        requests.monitor();
    }
    
    public void reset(){
        requests.reset();
    }
    
    public List<RequestDonation> getReceived(){
        return received.getList();
    }
    
    
    public void monitorReceived(){
        received.monitor();
    }
    
    public void monitorRequests(){
        requests.monitor();
    }
    
    public boolean addReceived(RequestDonation req, List<Entity> entitiesList, RequestDonationList currentDonations){
        return received.add(req, entitiesList);
    }
    
    public boolean addRequest(RequestDonation request, List<Entity> entitiesList, RequestDonationList currentDonations){
        return requests.add(this, request, entitiesList, currentDonations);
    }
    
    public boolean modifyRequest(int index, int quantity,RequestDonation request, List<Entity> entitiesList, RequestDonationList currentDonations){
        return requests.modify(index, quantity, request, entitiesList, currentDonations, this);
    }
            
    public void resetReceived(){
        received.reset();
    }     
    
    public void resetRequests(){
        requests.reset();
    }
    
    public void removeRequest(int index){
        requests.remove(index);
    }
    
    public void commit(RequestDonationList current, List<Entity> entities){
        requests.commit(current, entities, this);
    }
    
    public void receivedList(){
        if(received.getList().isEmpty()){
            System.out.println("*****No entities have been recorded*****");
        }
        else{
            System.out.println("\n Received \n");
            Iterator<RequestDonation> iter = received.getList().iterator();
            int i = 1;
            while(iter.hasNext()){
                RequestDonation req = iter.next();
                System.out.println("Entity "+i+": "+req.getEntity()+" x"+req.getQuantity());
                i++;
            }
        }
    }
    
    public List<RequestDonation> getRequestMaterials(){
        List<RequestDonation> mat = new ArrayList<>();
        for (RequestDonation req : requests.getList()) {
            if(req.getEntity().getClass().getName().contains("Material")){
                mat.add(req);
            }
        }
        return mat;
    }
    
    public List<RequestDonation> getRequestService(){
        List<RequestDonation> mat = new ArrayList<>();
        for (RequestDonation req : requests.getList()) {
            if(req.getEntity().getClass().getName().contains("Service")){
                mat.add(req);
            }
        }
        return mat;
    }
}

