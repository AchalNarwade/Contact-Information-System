package app;


import ContactInfoSystem.Contact;
import ContactInfoSystem.ContactManager;
import ContactInfoSystem.DBConnection;

import java.sql.Connection;
import java.util.ArrayList;
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

        switch (choice) {

            case 1: {
                sc.nextLine();
                System.out.println("Enter Name: ");
                String name = sc.nextLine();

                //validating name
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                    break;
                }

                System.out.println("Enter Phone Number : ");
                String phoneNo = sc.nextLine();

                //validating phone number(Stronger validation)
                if (!phoneNo.matches("\\d{10}")) {
                    System.out.println("Invalid phone number.Enter 10 digits only.");
                    break;
                }

                System.out.println("Enter Email: ");
                String email = sc.nextLine();

                //validating email
                if (!email.contains("@")) {
                    System.out.println("Invalid email format");
                    break;
                }

                System.out.println("Enter Address: ");
                String address = sc.nextLine();
                //new contact object lives inside arraylist
                Contact newContact = new Contact(name, phoneNo, email, address);
                contactManager.addContact(newContact);
                break;
            }
            case 2: {
                contactManager.showAllContacts();
                break;
            }

            case 3:{
                sc.nextLine();
                System.out.println("Enter name to search: ");
                String searchName = sc.nextLine();

                ArrayList<Contact> results = contactManager.searchContactsByName(searchName);

                if (results.isEmpty()) {
                    System.out.println("No contact found");
                } else {
                    System.out.println("\nSearch Result\n");

                    int i = 1;
                    for (Contact c : results) {
                        System.out.println("Contact " + i + ":");
                        c.displayShortContacts();
                        i++;
                    }
                }
                break;
            }

            case 4: {
                sc.nextLine();
                System.out.println("Enter phone number: ");
                String searchPhone = sc.nextLine();

                Contact contactByPhone = contactManager.findContactByPhone(searchPhone);

                if (contactByPhone != null) {
                    contactByPhone.displayContact();
                } else {
                    System.out.println("No contact found with given phone number.");
                }
                break;
            }

            case 5: {
                sc.nextLine();
                System.out.println("Enter name to search for update:");
                String updateName = sc.nextLine();

                ArrayList<Contact> results = contactManager.searchContactsByName(updateName);

                if (results.isEmpty()) {
                    System.out.println("No contact found!");
                } else {

                    System.out.println("\nSelect contact to update:");

                    for (int i = 0; i < results.size(); i++) {
                        Contact c = results.get(i);
                        System.out.println((i + 1) + ". " + c.getName() + " - " + c.getPhoneNumber());
                    }

                    System.out.println("Enter choice:");
                    int updateChoiceIndex = sc.nextInt();
                    sc.nextLine();

                    if (choice > 0 && choice <= results.size()) {

                        Contact selected = results.get(choice - 1);
                        String phone = selected.getPhoneNumber();

                        System.out.println("\nWhat do you want to update?");
                        System.out.println("1. Name");
                        System.out.println("2. Email");
                        System.out.println("3. Address");

                        int updateChoice = sc.nextInt();
                        sc.nextLine();

                        switch (updateChoice) {

                            case 1:
                                System.out.println("Enter new Name:");
                                String newName = sc.nextLine();
                                contactManager.updateContact(phone, newName, null, null);
                                break;

                            case 2:
                                System.out.println("Enter new Email:");
                                String newEmail = sc.nextLine();
                                contactManager.updateContact(phone, null, newEmail, null);
                                break;

                            case 3:
                                System.out.println("Enter new Address:");
                                String newAddress = sc.nextLine();
                                contactManager.updateContact(phone, null, null, newAddress);
                                break;

                            default:
                                System.out.println("Invalid choice");
                        }

                    } else {
                        System.out.println("Invalid choice");
                    }
                }
                break;
            }


            case 6: {
                sc.nextLine();
                System.out.println("Enter name to search for deletion:");
                String deleteName = sc.nextLine().trim();

                ArrayList<Contact> results = contactManager.searchContactsByName(deleteName);

                if (results.isEmpty()) {
                    System.out.println("No contact found!");
                } else {

                    System.out.println("\nSelect contact to delete:");

                    for (int i = 0; i < results.size(); i++) {
                        Contact c = results.get(i);
                        System.out.println((i + 1) + ". " + c.getName() + " - " + c.getPhoneNumber());
                    }

                    System.out.println("Enter choice:");
                    int deleteChoice = sc.nextInt();
                    sc.nextLine();

                    if(deleteChoice >= 1 && deleteChoice <= results.size()){
                        Contact selected = results.get(deleteChoice - 1);

                        System.out.println("Are you sure to delete? (yes/no)");
                        String confirm = sc.nextLine();

                        if (confirm.equalsIgnoreCase("yes")) {
                            contactManager.deleteContact(selected.getPhoneNumber());
                            break;
                        } else {
                            System.out.println("Deletion cancelled");
                        }

                    } else {
                        System.out.println("Invalid choice");
                    }
                }
                break;
            }

            case 7: {
                System.out.println("Total contacts: " + contactManager.getTotalContacts());
                break;
            }

            case 8:
                System.exit(0);
        }
            
        }
    }
}
