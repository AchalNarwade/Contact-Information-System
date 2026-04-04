package ContactInfoSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ContactManager {

    private ArrayList<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact){

        String query = "INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhoneNumber());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getAddress());

            ps.executeUpdate();

            System.out.println("Contact added successfully!");

        } catch (Exception e){
            System.out.println("Error adding contact");
            e.printStackTrace();
        }
    }

    public void showAllContacts(){
        if(contacts.isEmpty()){
            System.out.println("No Contacts Available!");
        } else {
            //sorting first(Alphabetically) also case-insensitive
            contacts.sort((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

            System.out.println("\n===== CONTACT LIST =====\n");
            System.out.printf("%-5s %-20s %-18s %-30s\n", "No", "Name", "Phone", "Email");
            System.out.println("--------------------------------------------------------------------------");

            int i = 1;
            for(Contact c : contacts){
                //formatted string
                System.out.printf("%-5d %-20s %-18s %-30s\n" ,
                i,
                c.getName(),
                c.getPhoneNumber(),
                c.getEmail()
                );
                i++;
            }
        }
    }
//here the contact object is returned as the return type is contact instead of void
    public Contact findContact(String name){
        for(Contact c : contacts){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){   //case-insensitive,partial-match
                return c;
            }
        }
        return null;
    }

    public void deleteContact(String name){
        Contact contactToDelete = findContact(name);

        if(contactToDelete != null){
            contacts.remove(contactToDelete);
            System.out.println("Contact successfully deleted");
        }
        else {
            System.out.println("Contact not found");
        }
    }
//FIND CONTACT BY PHONE
    public Contact findContactByPhone(String phone){
        for (Contact c : contacts){
            if(c.getPhoneNumber().equals(phone)){
                return c;
            }
        }
        return null;
    }

    public ArrayList<Contact> searchContactsByName(String name){
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                results.add(c);
            }
        }
        return results;
    }

    public int getTotalContacts(){ return contacts.size();}
}