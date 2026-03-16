package ContactInfoSystem;

import java.util.ArrayList;

public class ContactManager {

    private ArrayList<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact){
        if(findContact(contact.getName()) !=null) {
            System.out.println("Contact with Name: " + contact.getName() + " already exists!");
            return;
        }
        contacts.add(contact);
        System.out.println("Contact Successfully Added");
    }

    public void showAllContacts(){
        if(contacts.isEmpty()){
            System.out.println("No Contacts Available!");
        }else{
            for(Contact c : contacts){
                c.displayContact();
            }
        }
    }

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


    public int getTotalContacts(){ return contacts.size();}
}