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
            System.out.println("3. Search Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Total Contacts");
            System.out.println("6. Exit");
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
                    System.out.println("Contact not found");
                }
                break;

            case 4:
                sc.nextLine();
                System.out.println("Enter Name");
                String deleteContact = sc.nextLine();

                contactManager.deleteContact(deleteContact);
                break;

            case 5:
                System.out.println("Total contacts: " + contactManager.getTotalContacts());
                break;

            case 6:
                System.exit(0);
        }
            
        }
    }
}
