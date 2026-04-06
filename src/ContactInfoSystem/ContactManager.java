package ContactInfoSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

        String query = "SELECT * FROM contacts ORDER BY LOWER(name) ASC";

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int i = 1;

            System.out.println("\n--- Contact List ---");

            // Header
            System.out.printf("%-5s %-20s %-15s %-30s\n",
                    "No.", "Name", "Phone", "Email");
            System.out.println("---------------------------------------------------------------");

            while(rs.next()){
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                System.out.printf("%-5d %-20s %-15s %-30s\n",
                        i++, name, phone, email);
            }

        } catch (Exception e){
            System.out.println("Error fetching contacts");
            e.printStackTrace();
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

    public void deleteContact(String phone){

        String query = "DELETE FROM contacts WHERE phone = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, phone);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Contact deleted successfully!");
            } else {
                System.out.println("Contact not found!");
            }

        } catch (Exception e){
            System.out.println("Error deleting contact");
            e.printStackTrace();
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

    public void updateContact(String phone, String name, String email, String address){

        StringBuilder query = new StringBuilder("UPDATE contacts SET ");
        boolean first = true;

        if(name != null){
            query.append("name = ?");
            first = false;
        }

        if(email != null){
            if(!first) query.append(", ");
            query.append("email = ?");
            first = false;
        }

        if(address != null){
            if(!first) query.append(", ");
            query.append("address = ?");
        }

        query.append(" WHERE phone = ?");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString());

            int index = 1;

            if(name != null) ps.setString(index++, name);
            if(email != null) ps.setString(index++, email);
            if(address != null) ps.setString(index++, address);

            ps.setString(index, phone);

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("Contact updated successfully!");
            } else {
                System.out.println("Contact not found!");
            }

        } catch (Exception e){
            System.out.println("Error updating contact");
            e.printStackTrace();
        }
    }

    public int getTotalContacts(){ return contacts.size();}
}