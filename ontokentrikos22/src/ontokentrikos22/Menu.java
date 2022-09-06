package ontokentrikos22;

import java.util.Scanner;

public class Menu {
    private static Organization organization;
    private static int loggedUserIndex;
    private static UserType loggedUserType;
    

//kanei arxikopoihsh toy admin 
    public static void initAdmin(){
       organization = new Organization("name", new Admin("Admin", "00"));
    }
    
    public static Organization getOrganization(){
        return organization;
    }

//login to opoio elegxei an to thlefwno antistoixei se kapoion user
//
    public static void loginUser(){
        boolean logged = false;
        while(logged == false){
            UserType type = null;
            System.out.print("Phone: ");
            Scanner scanner = new Scanner(System.in);
            String typedPhone;
            typedPhone = scanner.next();
            int index = organization.getBeneficiaryIndexWithGivenPhone(typedPhone);
            type = UserType.BENEFICIARY;
            if(index == -1){
                index = organization.getDonatorIndexWithGivenPhone(typedPhone);
                type = UserType.DONATOR;
                if(index == -1 ){
                    if(organization.getAdmin().equalsWithPhone(typedPhone)){
                        logged = true;
                        loggedUserIndex = -1;
                        adminMainMenu();
                    }
                    else{
                        logged = register(typedPhone);
                        if(logged){
                            if(loggedUserType == UserType.DONATOR){
                                donatorMainMenu();
                            }
                            else{
                                beneficiaryMainMenu();
                            }
                        }
                    }
                }
                else{
                    loggedUserIndex = index;
                    donatorMainMenu();
                    logged = true;
                }
            }
            else{
                loggedUserIndex = index;
                beneficiaryMainMenu();
                logged = true;
            }
        }
    }
    
//an den exei ikanopoihthei kamia apo tis parapanw epiloges to programma paei sto register
    private static boolean register(String phone){
        try{
            System.out.print("Do you want to register? (1 yes, 2 no): ");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            if(input != 1 && input != 2){
                throw new InvalidMenuSelectionException();
            }
            if(input == 1){
                boolean userInList = false;
                while(userInList == false){  
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.print("Name: ");
                    String name = scanner2.nextLine();
                    int choice;
                    int ch2 = 0;
                    do{
                        System.out.print("Type (1  Donator, 2  Beneficiary): ");
                        choice = scanner2.nextInt();
                        if(choice != 1 && choice != 2){
                            System.err.println("The choice has to be 1 or 2");
                        }
                        else if(choice == 2){
                            System.out.println("\n You are now a Beneficiary. \n ");
                            System.out.print("Please type no of people:");
                            ch2 = scanner2.nextInt();
                        }
                    }while(choice != 1 && choice != 2);
                    if(choice == 1){
                        System.out.println("\n You are now a Donator. \n ");
                        userInList = organization.insertDonator(new Donator(name, phone));
                        if(userInList){
                            loggedUserIndex = organization.getDonatorIndexWithGivenPhone(phone);
                            loggedUserType = UserType.DONATOR;
                        }
                    }
                    else{
                        userInList = organization.insertBeneficiary(new Beneficiary(name, phone, ch2));
                        if(userInList){
                            loggedUserIndex = organization.getBeneficiaryIndexWithGivenPhone(phone);
                            loggedUserType = UserType.BENEFICIARY;
                        }

                    }
                }
                return true;
            }
            else{
                return false;
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            return register(phone);
        }
    }
    
    //to main menu toy donator
    private static void donatorMainMenu(){
        int input = -1;
        while(input != 4){
            try{
                while(input <= 0 || input > 4){
                    System.out.println("\nWelcome Donator "+organization.getDonatorWithGivenIndex(loggedUserIndex)+"\n");
                    System.out.println("  Donator Menu\n");
                    System.out.println(" 1. Add offer   ");
                    System.out.println(" 2. Show offers ");
                    System.out.println(" 3. Logout      ");
                    System.out.println(" 4. Exit  \n    ");
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Choose from 1-4 according to the options above: ");
                    input = scanner.nextInt();
                    System.out.print("\n");
                    if(input <= 0 || input > 4){
                        throw new InvalidMenuSelectionException();
                    } 
                }
            }
            catch(InvalidMenuSelectionException ex){
                System.out.println(ex.getMessage());
                donatorMainMenu();
            }
            switch (input) {
                case 1:
                    selectionDonator1();
                    break;
                case 2:
                    selectionDonator2();
                    break;
                case 3:
                    loginUser();
                    break;
                case 4:
                    System.out.println("\n\nExit\n\n");
                    System.exit(0);
                                   
                default:
                    break;
            }
            input = -1;
        }
    }
    
    private static void selectionDonator1(){
        int input = -1;
        organization.showCategories();
        EntityCategory cat = null;
        try{
            while(input < 1 || input > 2){
                Scanner scanner = new Scanner(System.in);
                System.out.print("Choice: ");
                input = scanner.nextInt();
                if(input < 1 || input > 2){
                    throw new InvalidMenuSelectionException();
                } 
            }
            cat = (input == 1) ? EntityCategory.MATERIAL : EntityCategory.SERVICE;
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            selectionDonator1();
        }
        entitiesInCategory(cat);
    }
    
    private static void entityDetails(EntityCategory category, int product){
        try{

            System.out.println("Do you want to offer the entity? (1 - yes, 0 no)");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            if(input != 0 && input != 1){
                throw new InvalidMenuSelectionException();
            }

            addOfferedEntity(category, product);
            
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            entityDetails(category, product);
        }
    }
    
    private static void entitiesInCategory(EntityCategory category){
        System.out.println("Entities:\n");
        
        organization.showEntitiesInCategory(category);
        int input = -1;
        try{
            while(input <= 0 || input > organization.productCountPerCategory(category)){
                Scanner scanner = new Scanner(System.in);
                System.out.print("Choice: ");
                input = scanner.nextInt();
                if(input <= 0 || input > organization.productCountPerCategory(category)){
                    throw new InvalidMenuSelectionException();
                }
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            entitiesInCategory(category);
        }
        entityDetails(category, input);
    }
    
    private static void addOfferedEntity(EntityCategory category, int product_index){
        boolean ins = false;
        while(ins == false){
            System.out.println("Please type the desired quantity");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            Entity entity = organization.getEntityByIndex(category, product_index);
            if(entity == null){
                ins = false;
            } 
            ins = organization.getDonatorWithGivenIndex(loggedUserIndex).add(new RequestDonation(entity, input), organization.getEntities());
        }
    }
    
    private static void selectionDonator2(){
        Donator b = organization.getDonatorWithGivenIndex(loggedUserIndex);
        b.monitor();
        int input = 0;
        if(!b.getOffers().isEmpty()){
            try{
                System.out.println("1. Select offer");
                System.out.println("2. Clear offers");
                System.out.println("3. commit");
                System.out.print("Choose from 1-3 according to the options above: ");
                Scanner sc = new Scanner(System.in);
                input = sc.nextInt();
                if(input <= 0 || input > 3){
                    throw new InvalidMenuSelectionException();
                }
            }
            catch(InvalidMenuSelectionException ex){
                System.out.println(ex.getMessage());
                selectionDonator2();
            }
            switch (input) {
                case 1:
                    offerSelection(input, b);
                    break;
                case 2:
                    b.reset();
                    break;
                default:
                    b.commit(organization.getCurrentDonations(), organization.getEntities());
                    break;
            }
        }

    }
    
    private static void offerSelection(int order_index, Donator b){
        int input = 0;
        try{
            System.out.print("Please select an offer: ");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();
            if(input <= 0 || input > b.getOffers().size()){
                throw new InvalidMenuSelectionException();
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            offerSelection(order_index, b);
        }
        offerSelectionChoices(input,b);
    }
    
    private static void offerSelectionChoices(int order_no, Donator b){
        int input = 0;
        try{
            System.out.println("1. Delete order");
            System.out.println("2. Change quantity");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();
            if(input < 1 || input > 2){
                throw new InvalidMenuSelectionException();
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            offerSelectionChoices(order_no, b);
        }
        if(input == 1){
            b.remove(order_no-1);
        }
        else{
            System.out.println("Please type the desired quantity");
            Scanner scanner = new Scanner(System.in);
            int quantity = scanner.nextInt();
            b.modify(order_no-1, quantity);
        }
    }
    
    private static void beneficiaryMainMenu(){
                int input = -1;
        while(input != 4){
            try{
                while(input <= 0 || input > 4){
                    System.out.println("\nWelcome Beneficiary "+organization.getBeneficiaryWithGivenIndex(loggedUserIndex)+"\n");
                    System.out.println("Beneficiary Menu\n");
                    System.out.println("1. Add request    ");
                    System.out.println("2. Show requests  ");
                    System.out.println("3. Logout         ");
                    System.out.println("4. Exit       \n  ");
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Choose from 1-4 according to the options above: ");
                    input = scanner.nextInt();
                    if(input <= 0 || input > 4){
                        throw new InvalidMenuSelectionException();
                    } 
                }
            }
            catch(InvalidMenuSelectionException ex){
                System.out.println(ex.getMessage());
                beneficiaryMainMenu();
            }
                    switch (input) {
                        case 1:
                            selectionBeneficiary1();
                            break;
                        case 2:
                            selectionBeneficiary2();
                            break;
                        case 3:
                            loginUser();
                            break;
                        case 4:
                            System.out.println("\n\nExit\n\n");
                            System.exit(0);
                        default:
                            break;
                    }
            input = -1;
        }
    }
    
    private static void selectionBeneficiary1(){
        int input = -1;
        organization.showCategoriesOfOffers();
        EntityCategory cat = null;
        try{
            while(input < 1 || input > 2){
                Scanner scanner = new Scanner(System.in);
                System.out.print("Choice: ");
                input = scanner.nextInt();
                if(input < 1 || input > 2){
                    throw new InvalidMenuSelectionException();
                } 
            }
            cat = (input == 1) ? EntityCategory.MATERIAL : EntityCategory.SERVICE;
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            selectionBeneficiary1();
        }
        offersInCategory(cat);
    }
    
    private static void offersInCategory(EntityCategory category){
        System.out.println("Offers:\n");
        
        organization.showOffersEntitiesInCategory(category);
        int input = -1;
        try{
            while(input <= 0 || input > organization.offersCountPerCategory(category)){
                Scanner scanner = new Scanner(System.in);
                System.out.print("Choice: ");
                input = scanner.nextInt();
                if(input <= 0 || input > organization.offersCountPerCategory(category)){
                    throw new InvalidMenuSelectionException();
                }
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            offersInCategory(category);
        }
        offerDetails(category, input);
    }
    
    private static void offerDetails(EntityCategory category, int product){
        try{

            System.out.println("Do you want to request the entity? (1 - yes, 0 no)");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            if(input != 0 && input != 1){
                throw new InvalidMenuSelectionException();
            }

            addRequestedEntity(category, product);
            
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            offerDetails(category, product);
        }
    }
    
    private static void addRequestedEntity(EntityCategory category, int product_index){
        boolean ins = false;
        while(ins == false){
            try{
                System.out.println("Please type the desired quantity");
                Scanner scanner = new Scanner(System.in);
                int input = scanner.nextInt();
                Entity entity = organization.getEntityByIndexInOffers(category, product_index);
                if(entity == null){
                    
                    ins = false;
                } 
                ins = organization.getBeneficiaryWithGivenIndex(loggedUserIndex).addRequest(new RequestDonation(entity, input), organization.getEntities(), organization.getCurrentDonations());
                if(!ins){
                    throw new InvalidRequest();
                }
            }
            catch(InvalidRequest ex){
                System.err.println(ex.getMessage());
            }

        }
    }
    
    private static void selectionBeneficiary2(){
        Beneficiary b = organization.getBeneficiaryWithGivenIndex(loggedUserIndex);
        b.monitor();
        int input = 0;
        if(!b.getRequests().isEmpty()){
            try{
                System.out.println("1. Select request");
                System.out.println("2. Clear requests");
                System.out.println("3. commit");
                Scanner sc = new Scanner(System.in);
                input = sc.nextInt();
                if(input <= 0 || input > 3){
                    throw new InvalidMenuSelectionException();
                }
            }
            catch(InvalidMenuSelectionException ex){
                System.out.println(ex.getMessage());
                selectionDonator2();
            }
            switch (input) {
                case 1:
                    requestSelection(input, b);
                    break;
                case 2:
                    b.reset();
                    break;
                default:
                    b.commit(organization.getCurrentDonations(), organization.getEntities());
                    break;
            }
        }

    }
    
    private static void requestSelection(int order_index, Beneficiary b){
        int input = 0;
        try{
            System.out.print("Please select an request: ");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();
            if(input <= 0 || input > b.getRequests().size()){
                throw new InvalidMenuSelectionException();
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            requestSelection(order_index, b);
        }
        requestSelectionChoices(input,b);
    }
    
    private static void requestSelectionChoices(int order_no, Beneficiary b){
        int input = 0;
        try{
            System.out.println("1. Delete request");
            System.out.println("2. Change quantity");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();
            if(input < 1 || input > 2){
                throw new InvalidMenuSelectionException();
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            requestSelectionChoices(order_no, b);
        }
        if(input == 1){
            b.removeRequest(order_no-1);
        }
        else{
            System.out.println("Please type the desired quantity");
            Scanner scanner = new Scanner(System.in);
            int quantity = scanner.nextInt();
            RequestDonation req = b.getRequests().get(order_no-1);
            b.modifyRequest(order_no-1, quantity, req, organization.getEntities(),organization.getCurrentDonations());
        }
    }
    
    private static void adminMainMenu(){
        int input = -1;
        while(input != 5){
            try{
                while(input <= 0 || input > 5){
                    System.out.println("\nWelcome Admin "+organization.getAdmin()+"\n");
                    System.out.println("   Admin Menu \n       ");
                    System.out.println("1. View                ");
                    System.out.println("2. Monitor Organization");
                    System.out.println("3. Logout              ");
                    System.out.println("4. Exit                ");
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Choice: ");
                    input = scanner.nextInt();
                    if(input <= 0 || input > 4){
                        throw new InvalidMenuSelectionException();
                    } 
                }
            }
            catch(InvalidMenuSelectionException ex){
                System.out.println(ex.getMessage());
                donatorMainMenu();
            }
            switch (input) {
                case 1:
                    selectionAdmin1();
                    break;
                case 2:
                    selectionAdmin2();
                    break;
                case 3:
                    loginUser();
                    break;
                case 4:
                    System.out.println("\n\nExit\n\n");
                    System.exit(0);
                default:
                    break;
            }
            input = -1;
        }
    }
    
    private static void selectionAdmin1(){
        int input = -1;
        organization.showCategories();
        EntityCategory cat = null;
        try{
            while(input < 1 || input > 2){
                Scanner scanner = new Scanner(System.in);
                System.out.print("Choice: ");
                input = scanner.nextInt();
                if(input < 1 || input > 2){
                    throw new InvalidMenuSelectionException();
                } 
            }
            cat = (input == 1) ? EntityCategory.MATERIAL : EntityCategory.SERVICE;
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            selectionAdmin1();
        }
        entitiesInCategoryAdmin(cat);
    }
    
    private static void entitiesInCategoryAdmin(EntityCategory category){
        System.out.println("Entities:\n");
        
        organization.showEntitiesInCategory(category);
        int input = -1;
        try{
            while(input <= 0 || input > organization.productCountPerCategory(category)){
                Scanner scanner = new Scanner(System.in);
                System.out.print("Choice: ");
                input = scanner.nextInt();
                if(input <= 0 || input > organization.productCountPerCategory(category)){
                    throw new InvalidMenuSelectionException();
                }
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            entitiesInCategoryAdmin(category);
        }
    }
    
    private static void selectionAdmin2(){
        int input = 0;
        try{
            System.out.println("1. List Beneficiaries");
            System.out.println("2. List Donators");
            System.out.println("3. Reset Beneficiaries list");
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();
            if(input < 1 || input > 3){
                throw new InvalidMenuSelectionException();
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            selectionAdmin2();
        }
        switch (input) {
            case 1:
                beneficiaryChoice();
                break;
            case 2:
                donatorChoice();
                break;
            default:
                break;
        }
    }
    
    private static void beneficiaryChoice(){
        int input = 0;
        try{
            organization.listBeneficiaries();
            Scanner sc = new Scanner(System.in);
            System.out.print("Choice: ");
            input = sc.nextInt();
            if(input < 1 || input > organization.getBeneficiaries().size()){
                throw new InvalidMenuSelectionException();
            }

            organization.getBeneficiaryWithGivenIndex(input - 1).receivedList();
            System.out.println("1. Καθαρισμός received");
            System.out.println("2. Delete");
            int input2 = sc.nextInt();
            if(input2 == 1){
                clearBeneficiary(input - 1);
            }
            else{
                removeBeneficiary(input - 1);
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            beneficiaryChoice();
        }
    }
    
    private static void clearBeneficiary(int index){
        organization.getBeneficiaryWithGivenIndex(index).reset();
    }
    
    private static void removeBeneficiary(int index){
        organization.removeBeneficiary(index);
    }
    
    private static void donatorChoice(){
        int input = 0;
        try{
            organization.listDonators();
            Scanner sc = new Scanner(System.in);
            System.out.print("Choice: ");
            input = sc.nextInt();
            if(input < 1 || input > organization.getDonators().size()){
                throw new InvalidMenuSelectionException();
            }

            organization.getDonatorWithGivenIndex(input - 1).monitor();
            System.out.println("1. Delete");
            int input2 = sc.nextInt();
            if(input2 == 1){
                removeDonator(input - 1);
            }
        }
        catch(InvalidMenuSelectionException ex){
            System.out.println(ex.getMessage());
            donatorChoice();
        }
    }

    
    private static void removeDonator(int index){
        organization.removeDonator(index);
    }
    
}
