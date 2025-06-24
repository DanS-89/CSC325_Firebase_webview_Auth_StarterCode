/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.csc325_firebase_webview_auth.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MoaathAlrajab
 *
 *
 *
 *
 * Model:
 *
 *
 */
public class Person {
    private final SimpleStringProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty department;
    private final SimpleStringProperty major;
    private final SimpleStringProperty email;
    private final SimpleStringProperty imageUrl;

    public Person(String id, String firstName, String lastName, String department,String major, String email, String imageUrl) {
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.department = new SimpleStringProperty(department);
        this.major = new SimpleStringProperty(major);
        this.email = new SimpleStringProperty(email);
        this.imageUrl = new SimpleStringProperty(imageUrl);
    }

    public String getId() { return id.get(); }
    public void setId(String value) { id.set(value); }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String value) { firstName.set(value); }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String value) { lastName.set(value); }

    public String getDepartment() { return department.get(); }
    public void setDepartment(String value) { department.set(value); }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }

    public String getMajor() { return major.get(); }
    public void setMajor(String value) { major.set(value); }

    public String getImageUrl() { return imageUrl.get(); }
    public void setImageUrl(String value) { imageUrl.set(value); }
}
