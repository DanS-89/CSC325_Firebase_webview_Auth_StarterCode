package com.example.csc325_firebase_webview_auth.view;//package modelview;

import com.example.csc325_firebase_webview_auth.model.Person;
import com.example.csc325_firebase_webview_auth.viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccessFBView {


    @FXML
    private TextField nameField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField ageField;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton;
    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> nameColumn, majorColumn;
    @FXML
    private TableColumn<Person, Integer> ageColumn;
    @FXML
    private MenuItem registerMenuItem, closeMenuItem, deleteMenuItem, helpMenuItem;

    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;
    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @FXML
    void initialize() {

        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        personTableView.setItems(listOfUsers);
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));



        readFirebase();
    }

    @FXML
    private void addRecord(ActionEvent event) {
        addData();
    }

    @FXML
    private void readRecord(ActionEvent event) {
        readFirebase();
    }

    @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/files/WebContainer.fxml");
    }

    @FXML
    public void addData() {

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        readFirebase();
    }

    public boolean readFirebase() {
        key = false;
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            listOfUsers.clear();
            if(documents.size() > 0) {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents) {
                    String name = String.valueOf(document.getData().get("Name"));
                    String major = String.valueOf(document.getData().get("Major"));
                    int age = Integer.parseInt(document.getData().get("Age").toString());
                    System.out.println(document.getId() + " => " + document.getData().get("Name"));
                    Person person  = new Person(name, major, age);
                    listOfUsers.add(person);
                    Collections.sort(listOfUsers, Comparator.comparing(Person::getName));

                }
                personTableView.setItems(listOfUsers);
            } else {
               System.out.println("No data");
            }
            key=true;
        }
        catch (InterruptedException | ExecutionException ex) {
             ex.printStackTrace();
        }
        return key;
    }

        public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        }
        catch (Exception e) {
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            App.setRoot("/files/SignUp.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
           // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handleHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Firebase JavaFX Application");
        alert.setContentText("Author: Daniel Stevens\nVersion 1.0\nCSC325 Firestore Project");
        alert.showAndWait();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Person selected = personTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        String nameToDelete = selected.getName();

        ApiFuture<QuerySnapshot> future = App.fstore.collection("References")
                .whereEqualTo("Name", nameToDelete)
                .get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                App.fstore.collection("References").document(doc.getId()).delete();
            }
            readFirebase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
