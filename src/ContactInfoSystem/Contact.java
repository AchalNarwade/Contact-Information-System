package ContactInfoSystem;

public class Contact {
//PRIVATE VARIABLES
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

//CONSTRUCTOR - initializes objects with values
    public Contact(String name,String phoneNumber, String email,String address ){
        this.name = name;
        this.phoneNumber= phoneNumber;
        this.email = email;
        this.address = address;
    }
//DISPLAY METHODS - used in console
    public void displayContact(){
        System.out.println("CONTACT Details: ");
        System.out.println(
                "Name    : " + name +
                "\nPhone   : " + phoneNumber +
                "\nEmail   : " + email +
                "\nAddress : " + address + "\n"
        );
    }

//DISPLAY SHORT CONTACTS - used in console
    public void displayShortContacts(){
        System.out.println(name + " |" + phoneNumber + " |" + email + " |");
    }



//getters - to read data
    public String getName(){
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

//setter to update data

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAddress(String address){
        this.address = address;
    }
}
