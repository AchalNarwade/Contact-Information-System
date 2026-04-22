package app;

import ContactInfoSystem.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class ContactUI extends JFrame { //to create window based application

    //VARIABLES
    private ContactManager manager = new ContactManager(); //connects ui to backend
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField; //for live search

    //FONTS
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 16);
    private Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

    public ContactUI() {

        //WINDOW SETUP
        setTitle("Contact Manager");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //TOP PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); //for button and search

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(buttonFont);

        searchField = new JTextField(20); //input for searching
        searchField.setFont(mainFont);

        //buttons for operation
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        addBtn.setFont(buttonFont);
        updateBtn.setFont(buttonFont);
        deleteBtn.setFont(buttonFont);

        addBtn.setPreferredSize(new Dimension(100, 35));
        updateBtn.setPreferredSize(new Dimension(100, 35));
        deleteBtn.setPreferredSize(new Dimension(100, 35));

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(addBtn);
        topPanel.add(updateBtn);
        topPanel.add(deleteBtn);

        add(topPanel, BorderLayout.NORTH);

        // TABLE-defines table column
        model = new DefaultTableModel(
                new String[]{"Name", "Phone", "Email", "Address"}, 0);

        //connects data with table
        table = new JTable(model);
        table.setFont(mainFont);
        table.setRowHeight(28);

        JTableHeader header = table.getTableHeader();
        header.setFont(buttonFont);

        add(new JScrollPane(table), BorderLayout.CENTER);

        //BUTTON ACTIONS-calls methods on clicking
        addBtn.addActionListener(e -> openAddDialog());
        updateBtn.addActionListener(e -> openUpdateDialog());
        deleteBtn.addActionListener(e -> deleteContact());

        //LIVE SEARCH
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }

            private void search() {
                liveSearch(searchField.getText()); //calls search whenever user type
            }
        });

        //INITIAL LOAD
        loadContacts(); //loads all the data when the app starts
        setVisible(true);//show window
    }

    //LOAD CONTACTS
    private void loadContacts() {
        model.setRowCount(0); //clear tables and reload the fresh data

        ArrayList<Contact> list = manager.getAllContacts(); //fetch the contacts from the database

        for (Contact c : list) {
            model.addRow(new Object[]{
                    c.getName(),
                    c.getPhoneNumber(),
                    c.getEmail(),
                    c.getAddress()
            });
        }
    }

    //LIVE SEARCH
    private void liveSearch(String text) {
        model.setRowCount(0);

        ArrayList<Contact> list = manager.searchContactsByName(text);

        for (Contact c : list) {
            model.addRow(new Object[]{
                    c.getName(),
                    c.getPhoneNumber(),
                    c.getEmail(),
                    c.getAddress()
            });
        }
    }

    //ADD CONTACT
    private void openAddDialog() {
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JTextField address = new JTextField();

        name.setFont(mainFont);
        phone.setFont(mainFont);
        email.setFont(mainFont);
        address.setFont(mainFont);

        Object[] fields = {
                "Name:", name,
                "Phone:", phone,
                "Email:", email,
                "Address:", address
        };

        int option = JOptionPane.showConfirmDialog( //this ensures that no separate window
                this, fields, "Add Contact", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            //this handles all the errors validation while adding contacts
            if (name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
                return;
            }

            if (!phone.getText().matches("\\d{10}")) { //used regex for validating the number should be 10 digits
                JOptionPane.showMessageDialog(this, "Invalid phone number");
                return;
            }

            if (!email.getText().contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email");
                return;
            }

            //convert input to the object
            Contact c = new Contact(
                    name.getText(),
                    phone.getText(),
                    email.getText(),
                    address.getText()
            );

            manager.addContact(c); //sends the newly added data to the backend
            loadContacts();
        }
    }

    //UPDATE CONTACT
    private void openUpdateDialog() {

        String name = JOptionPane.showInputDialog(this, "Enter name to update:");

        if (name == null || name.isEmpty()) return;

        ArrayList<Contact> results = manager.searchContactsByName(name);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No contacts found");
            return;
        }

        String[] options = new String[results.size()];

        for (int i = 0; i < results.size(); i++) {
            Contact c = results.get(i);
            options[i] = c.getName() + " - " + c.getPhoneNumber();
        }

        int choice = JOptionPane.showOptionDialog(
                this,
                "Select contact to update:",
                "Update Contact",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == -1) return;

        Contact selected = results.get(choice);

        JTextField newName = new JTextField(selected.getName());
        JTextField newEmail = new JTextField(selected.getEmail());
        JTextField newAddress = new JTextField(selected.getAddress());

        newName.setFont(mainFont);
        newEmail.setFont(mainFont);
        newAddress.setFont(mainFont);

        Object[] fields = {
                "Name:", newName,
                "Email:", newEmail,
                "Address:", newAddress
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Edit Contact",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {

            if (newName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty");
                return;
            }

            if (!newEmail.getText().contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email");
                return;
            }

            manager.updateContact(
                    selected.getPhoneNumber(),
                    newName.getText(),
                    newEmail.getText(),
                    newAddress.getText()
            );

            loadContacts();
        }
    }

    //DELETE CONTACT
    private void deleteContact() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a contact first");
            return;
        }

        String phone = model.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this contact?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            manager.deleteContact(phone);
            loadContacts();
        }
    }
}