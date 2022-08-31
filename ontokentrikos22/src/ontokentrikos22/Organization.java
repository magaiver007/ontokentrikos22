
package ontokentrikos22;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Organization {
    private String name;
    public Admin admin;
    private List<Entity> entities;
    private List<Donator> donators;
    private List<Beneficiary> beneficiaries;
    private RequestDonationList current;
    
    
    public Organization(String name, Admin admin){
        setName(name);
        setAdmin(admin);
        entities = new ArrayList<>();
        donators = new ArrayList<>();
        beneficiaries = new ArrayList<>();
        current = new RequestDonationList();
    }
    
    private void setName(String name){
        this.name = name;
    }
    
    private void setAdmin(Admin admin){
        this.admin = admin;
    }
    
    public Admin getAdmin(){
        return admin;
    }
    
    private List<Material> getMaterials(){
        List<Material> materials = new ArrayList<>();
        for (Entity entity : entities) {
            if(entity.getClass().getName().contains("Material")){
                materials.add((Material)entity);
            }
        }
        return materials;
    }
    
    private List<Service> getServices(){
        List<Service> services = new ArrayList<>();
        for (Entity entity : entities) {
            if(entity.getClass().getName().contains("Service")){
                services.add((Service)entity);
            }
        }
        return services;
    }
    
    public void addEntity(Entity entity){
        try{
            for (Entity cur : entities) {
                if(cur.equals(entity)){
                    throw new EntityExists();
                }
            }
            entities.add(entity);
        }
        catch(EntityExists ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public Entity getEntityById(EntityCategory category ,int id){
        try{
            switch(category){
                case MATERIAL:
                    List<Material> materials = getMaterials();
                    Iterator<Material> iter = materials.iterator();
                    while(iter.hasNext()){
                        if(iter.next().getId() == id){
                            return iter.next();
                        }
                    }
                    throw new EntityDoesNotExist(); 
                case SERVICE:
                    List<Service> services = getServices();
                    Iterator<Service> iter2 = services.iterator();
                    while(iter2.hasNext()){
                        if(iter2.next().getId() == id){
                            return iter2.next();
                        }
                    }
                    throw new EntityDoesNotExist(); 
            }
        }
        catch(EntityDoesNotExist ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public Entity getEntityByIndex(EntityCategory category, int index){
        int i = 0;
        switch(category){
            case MATERIAL:
                
                List<Material> materials = getMaterials();
                Iterator<Material> iter = materials.iterator();
                while(iter.hasNext()){
                    if(i == index){
                        return iter.next();
                    }
                    i++;
                }
                break;
            case SERVICE:
                List<Service> services = getServices();
                Iterator<Service> iter2 = services.iterator();
                while(iter2.hasNext()){
                    if(i == index){
                        return iter2.next();
                    }
                    i++;
                }
                break;
        }
        return null;
    }
    
    public Entity getEntityByIndexInOffers(EntityCategory category, int index){
        int i = 0;
        switch(category){
            case MATERIAL:
                
                List<RequestDonation> materials = getMaterialsOffers();
                Iterator<RequestDonation> iter = materials.iterator();
                while(iter.hasNext()){
                    if(i == index){
                        return iter.next().getEntity();
                    }
                    i++;
                }
                break;
            case SERVICE:
                List<RequestDonation> service = getServicesOffers();
                Iterator<RequestDonation> iter2 = service.iterator();
                while(iter2.hasNext()){
                    if(i == index){
                        return iter2.next().getEntity();
                    }
                    i++;
                }
                break;
        }
        return null;
    }
    
    public boolean insertDonator(Donator donator){
        Iterator<Donator> iter = donators.iterator();
        try{  
            while(iter.hasNext()){
                if(iter.next().equals(donator)){
                    throw new UserInListException();
                }
            }
            donators.add(donator);
            return true;
        }
        catch(UserInListException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean insertBeneficiary(Beneficiary beneficiary){
        Iterator<Beneficiary> iter = beneficiaries.iterator();
        try{  
            while(iter.hasNext()){
                if(iter.next().equals(beneficiary)){
                    throw new UserInListException();
                }
            }
            beneficiaries.add(beneficiary);
            return true;
        }
        catch(UserInListException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public void removeBeneficiary(int index){
        beneficiaries.remove(index);
    }
    
    public void removeDonator(int index){
        donators.remove(index);
    }
    
    public List<Entity> getEntities(){
        return entities;
    }
    
    public RequestDonationList getCurrentDonations(){
        return current;
    }
    
    public void showCategories(){
        System.out.println("\nCategories\n");
        System.out.println("1. Materials("+getMaterials().size()+")\n");
        System.out.println("2. Services("+getServices().size()+")  \n");
    }
    
    public void showForRequestsCategories(){
        System.out.println("\nCategories\n");
        System.out.println("1. Materials("+getMaterials().size()+")\n");
        System.out.println("2. Services("+getServices().size()+")  \n");
    }
    
    public void showEntitiesInCategory(EntityCategory category){
        int i = 1;
        switch(category){
            case MATERIAL:
                Iterator<Material> iter = getMaterials().iterator();
                while(iter.hasNext()){
                    System.out.println(i+" "+iter.next());
                    i++;
                }
                break;
            case SERVICE:
                Iterator<Service> iter2 = getServices().iterator();
                while(iter2.hasNext()){
                    System.out.println(i+" "+iter2.next());
                    i++;
                } 
                break;
        }
    }
    
    public int getDonatorIndexWithGivenPhone(String phone){
        Iterator<Donator> iter = donators.iterator();
        int index = 0;
        while(iter.hasNext()){
            Donator b = iter.next();
            if(b.equalsWithPhone(phone)){
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public int getBeneficiaryIndexWithGivenPhone(String phone){
        Iterator<Beneficiary> iter = beneficiaries.iterator();
        int index = 0;
        while(iter.hasNext()){
            Beneficiary b = iter.next();
            if(b.equalsWithPhone(phone)){
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public  void listEntities(EntityCategory category, int index){
        int i = 1;
        switch(category){
            case MATERIAL:
                Iterator<Material> iter = getMaterials().iterator();
                while(iter.hasNext()){     
                    System.out.println(i+" "+iter.next());
                }
                break;
            case SERVICE:
                Iterator<Service> iter2 = getServices().iterator();
                while(iter2.hasNext()){    
                    System.out.println(i+" "+iter2.next());
                    i++;
                } 
        }
    }
    
    public int productCountPerCategory(EntityCategory category){
        switch (category) {
            case MATERIAL:
                return getMaterials().size();
            default:
                return getServices().size();
        }
    }
    
    public Donator getDonatorWithGivenIndex(int index){
        return donators.get(index);
    }
    
    public Beneficiary getBeneficiaryWithGivenIndex(int index){

        return beneficiaries.get(index);
    }
    
        
    
    public void showOffersEntitiesInCategory(EntityCategory category){
        int i = 1;
        switch(category){
            case MATERIAL:
                Iterator<RequestDonation> iter = getMaterialsOffers().iterator();
                while(iter.hasNext()){
                    System.out.println(i+" "+iter.next());
                    i++;
                }
                break;
            case SERVICE:
                Iterator<RequestDonation> iter2 = getServicesOffers().iterator();
                while(iter2.hasNext()){
                    System.out.println(i+" "+iter2.next());
                    i++;
                } 
                break;
        }
    }
    
    private List<RequestDonation> getMaterialsOffers(){
        List<RequestDonation> list = new ArrayList<>();
        for (RequestDonation don : current.getList()) {
            if(don.getEntity().getClass().getName().contains("Material")){
                list.add(don);
            }
        }
        return list;
    }
    
    private List<RequestDonation> getServicesOffers(){
        List<RequestDonation> list = new ArrayList<>();
        for (RequestDonation don : current.getList()) {
            if(don.getEntity().getClass().getName().contains("Service")){
                list.add(don);
            }
        }
        return list;
    }
    
    public void showCategoriesOfOffers(){
        System.out.println("                          ");
        System.out.println("        Categories        ");
        System.out.println("                          ");
        System.out.println("      1. Materials("+getMaterialsOffers().size()+")             ");
        System.out.println("      2. Services("+getServicesOffers().size()+")           ");
        System.out.println("**************************");
    }
    
    public void resetAllBeneficiaries(){
        beneficiaries.forEach(next -> {
            next.resetReceived();
        });
    }

    public void showRequestsInCategory(EntityCategory category){
        int i = 1;
        switch(category){
            case MATERIAL:
                Iterator<RequestDonation> iter = getMaterialsOffers().iterator();
                while(iter.hasNext()){
                    System.out.println(i+" "+iter.next());
                    i++;
                }
                break;
            case SERVICE:
                Iterator<RequestDonation> iter2 = getServicesOffers().iterator();
                while(iter2.hasNext()){
                    System.out.println(i+" "+iter2.next());
                    i++;
                } 
                break;
        }
    }
    
    public int offersCountPerCategory(EntityCategory category){
        switch (category) {
            case MATERIAL:
                return getMaterialsOffers().size();
            default:
                return getServicesOffers().size();
        }
    }
    
    
    public void listBeneficiaries(){
        Iterator<Beneficiary> it = beneficiaries.iterator();
        int i = 1;
        while (it.hasNext()) {
            Beneficiary next = it.next();
            System.out.println(i+": "+next);
            i++;
        }
    }
    
    public void listDonators(){
        Iterator<Donator> it = donators.iterator();
        int i = 1;
        while (it.hasNext()) {
            Donator next = it.next();
            System.out.println(i+": "+next);
        }
    }
    
    public List<Beneficiary> getBeneficiaries(){
        return beneficiaries;
    }
    
    public List<Donator> getDonators(){
        return donators;
    }
}
