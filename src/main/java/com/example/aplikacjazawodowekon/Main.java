package com.example.aplikacjazawodowekon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private TableColumn<Contact, String> nameColumn;

    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private static Contact selectedContact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        if (contacts.isEmpty()) {
            contacts.add(new Contact("Jan Kowalski", "jan.kowalski@example.com", "123456789"));
        }

        contactsTable.setItems(contacts);

        contactsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Contact contact = contactsTable.getSelectionModel().getSelectedItem();
                if (contact != null) {
                    selectedContact = contact;
                    try {
                        HelloApplication.changeScene("contacts-views.fxml", "Contact Details");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    protected void Add() {
        try {
            HelloApplication.changeScene("add_contact-view.fxml", "Add Contact");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void Logout() {
        try {
            HelloApplication.changeScene("login-view.fxml", "Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Contact getSelectedContact() {
        return selectedContact;
    }

    public static void addContact(Contact contact) {
        contacts.add(contact);
    }
}