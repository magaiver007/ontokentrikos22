/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ontokentrikos22;

/**
 *
 * @author Magaiver
 */
public class Ontokentrikos22 {


    public static void main(String[] args) {
        Menu.initAdmin();
        Menu.getOrganization().addEntity(new Material(1,"Milk,", "Milk", 7, 4, 5));
        Menu.getOrganization().addEntity(new Material(2,"Sugar", "Sugar", 1, 8, 4));
        Menu.getOrganization().addEntity(new Material(3,"Rice", "Rice", 2, 4, 6));
        Menu.getOrganization().addEntity(new Service(4,"MedicalSupport ", "MedicalSupport"));
        Menu.getOrganization().addEntity(new Service(5,"NurserySupport", "NurserySupport"));
        Menu.getOrganization().addEntity(new Service(6,"BabySitting", "BabySitting"));
        
        Menu.getOrganization().insertDonator(new Donator("Donator 1", "01"));
        Menu.getOrganization().insertBeneficiary(new Beneficiary("Beneficiary 1", "02", 5));  
        Menu.getOrganization().insertBeneficiary(new Beneficiary("Beneficiary 2", "03", 7)); 
        
        Menu.loginUser();
    }
    
}