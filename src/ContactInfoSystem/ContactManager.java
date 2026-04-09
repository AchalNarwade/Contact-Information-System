package ContactInfoSystem;

import java.sql.*;
import java.util.ArrayList;

public class ContactManager {

    // ADD CONTACT
    public void addContact(Contact contact) {

        String query = "INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhoneNumber());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getAddress());

            ps.executeUpdate();
            System.out.println("Contact added successfully!");

        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate")) {
                System.out.println("Contact with this phone number already exists!");
            } else {
                System.out.println("Error adding contact");
                e.printStackTrace();
            }
        }
    }

    // SHOW ALL CONTACTS
    public ArrayList<Contact> getAllContacts() {

        ArrayList<Contact> list = new ArrayList<>();
        String query = "SELECT * FROM contacts ORDER BY LOWER(name) ASC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error fetching contacts");
            e.printStackTrace();
        }

        return list;
    }

    // SEARCH BY NAME
    public ArrayList<Contact> searchContactsByName(String name) {

        ArrayList<Contact> results = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE LOWER(name) LIKE LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                results.add(new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error searching contact");
            e.printStackTrace();
        }

        return results;
    }

    //  FIND BY PHONE (FIXED → DB BASED)
    public Contact findContactByPhone(String phone) {

        String query = "SELECT * FROM contacts WHERE phone = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, phone);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }

        } catch (Exception e) {
            System.out.println("Error finding contact");
            e.printStackTrace();
        }

        return null;
    }

    // DELETE
    public void deleteContact(String phone) {

        String query = "DELETE FROM contacts WHERE phone = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, phone);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Contact deleted successfully!");
            } else {
                System.out.println("Contact not found!");
            }

        } catch (Exception e) {
            System.out.println("Error deleting contact");
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateContact(String phone, String name, String email, String address) {

        StringBuilder query = new StringBuilder("UPDATE contacts SET ");
        boolean first = true;

        if (name != null) {
            query.append("name = ?");
            first = false;
        }

        if (email != null) {
            if (!first) query.append(", ");
            query.append("email = ?");
            first = false;
        }

        if (address != null) {
            if (!first) query.append(", ");
            query.append("address = ?");
        }

        query.append(" WHERE phone = ?");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            int index = 1;

            if (name != null) ps.setString(index++, name);
            if (email != null) ps.setString(index++, email);
            if (address != null) ps.setString(index++, address);

            ps.setString(index, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Contact updated successfully!");
            } else {
                System.out.println("Contact not found!");
            }

        } catch (Exception e) {
            System.out.println("Error updating contact");
            e.printStackTrace();
        }
    }

    //  TOTAL CONTACTS (FIXED → DB COUNT)
    public int getTotalContacts() {

        String query = "SELECT COUNT(*) FROM contacts";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error counting contacts");
            e.printStackTrace();
        }

        return 0;
    }
}