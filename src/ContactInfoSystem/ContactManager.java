package ContactInfoSystem;

import java.util.ArrayList;

public class ContactManager {

    private ArrayList<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact){

        //Strict case
        if(findContactByPhone(contact.getPhoneNumber()) != null){
            System.out.println("A contact with this phone number already exists!");
            return;
        }

        //soft check(name)
        if(findContact(contact.getName()) !=null) {
            System.out.println("A contact with this name already exits!");
            return;
        }
        contacts.add(contact);
        System.out.println("Contact Successfully Added");
    }

    public void showAllContacts(){
        if(contacts.isEmpty()){
            System.out.println("No Contacts Available!");
        } else {
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
            if(c.getName().equals(name)){
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

    public int getTotalContacts(){ return contacts.size();}
}