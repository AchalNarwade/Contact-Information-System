package ContactInfoSystem;

public class Contact {
//PRIVATE VARIABLES
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

//CONSTRUCTOR
    public Contact(String name,String phoneNumber, String email,String address ){
        this.name = name;
        this.phoneNumber= phoneNumber;
        this.email = email;
        this.address = address;
    }
//DISPLAY METHODS
    public void displayContact(){
        System.out.println("CONTACT Details: ");
        System.out.println(
                "\nName    : " + name +
                "\nPhone   : " + phoneNumber +
                "\nEmail   : " + email +
                "\nAddress : " + address
        );
    }



//GETTER METHODS
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


}
