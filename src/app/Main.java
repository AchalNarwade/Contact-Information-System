package app;


import ContactInfoSystem.Contact;
import ContactInfoSystem.ContactManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactManager contactManager = new ContactManager();

        while(true){
            System.out.println("\n1. Add Contact");
            System.out.println("2. Show All Contacts");
            System.out.println("3. Search by Name");
            System.out.println("4. Search by Phone");
            System.out.println("5. Update Contact");
            System.out.println("6. Delete Contact");
            System.out.println("7. Total Contacts");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");


        int choice = 0;
        try {
            choice = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine(); //to clear scanner buffer
        }

        switch (choice){

            case 1:
                sc.nextLine();
                System.out.println("Enter Name: ");
                String name = sc.nextLine();
                System.out.println("Enter Phone Number : ");
                String phoneNo = sc.nextLine();
                System.out.println("Enter Email: ");
                String email = sc.nextLine();
                System.out.println("Enter Address: ");
                String address = sc.nextLine();
                //new contact object lives inside arraylist
                Contact newContact = new Contact(name,phoneNo,email,address);
                contactManager.addContact(newContact);
                break;

            case 2:
                contactManager.showAllContacts();
                break;

            case 3:
                sc.nextLine();
                System.out.println("Enter name: ");
                String searchName = sc.nextLine();
                //connects contact manger and contact class
                Contact contactToSearch = contactManager.findContact(searchName);

                if(contactToSearch != null){
                    contactToSearch.displayContact();
                }else{
                    System.out.println("No contact found with the given detail.");
                }
                break;


            case 4:
                sc.nextLine();
                System.out.println("Enter phone number: ");
                String searchPhone = sc.nextLine();

                Contact contactByPhone = contactManager.findContactByPhone(searchPhone);

                if(contactByPhone != null){
                    contactByPhone.displayContact();
                } else {
                    System.out.println("No contact found with given phone number.");
                }
                break;

            case 5:
                sc.nextLine();
                System.out.println("Enter the phone number of contact to update: ");
                String updatePhone = sc.nextLine();

                Contact contactToUpdate = contactManager.findContactByPhone(updatePhone);

                if(contactToUpdate != null){
                    System.out.println("\nWhat do you want to update?");
                    System.out.println("1. Phone");
                    System.out.println("2. Email");
                    System.out.println("3. Address");

                    int updateChoice = sc.nextInt();
                    sc.nextLine(); //to clear buffer

                    switch(updateChoice){

                        case 1:
                            System.out.println("Enter new Phone number");
                            String newPhone = sc.nextLine();

                            //check duplicate phone
                            if(contactManager.findContactByPhone(newPhone) != null){
                                System.out.println("Phone number already exists!");
                            }else{
                                contactToUpdate.setPhoneNumber(newPhone);
                                System.out.println("Phone number updated successfully");
                            }
                            break;

                        case 2:
                            System.out.println("Enter new Email: ");
                            String newEmail = sc.nextLine();
                            contactToUpdate.setEmail(newEmail);
                            System.out.println("Email updated successfully");
                            break;

                        case 3:
                            System.out.println("Enter new Address: ");
                            String newAddress = sc.nextLine();
                            contactToUpdate.setAddress(newAddress);
                            System.out.println("Address updated successfully");
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }
                }else {
                    System.out.println("No contact found with given phone number");
                }
                break;


            case 6:
                sc.nextLine();
                System.out.println("Enter Name");
                String deleteContact = sc.nextLine();

                contactManager.deleteContact(deleteContact);
                break;

            case 7:
                System.out.println("Total contacts: " + contactManager.getTotalContacts());
                break;

            case 8:
                System.exit(0);
        }
            
        }
    }
}
