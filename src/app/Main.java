package app;


import ContactInfoSystem.Contact;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Contact c1 = new Contact("Achal","987654321","pae71192@gmail.com","Pune");
        c1.displayContact();
    }
}