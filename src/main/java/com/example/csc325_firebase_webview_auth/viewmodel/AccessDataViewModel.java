package com.example.csc325_firebase_webview_auth.viewmodel;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccessDataViewModel {

	private final StringProperty userId = new SimpleStringProperty();
	private final StringProperty userFirstName= new SimpleStringProperty();
	private final StringProperty userLastName = new SimpleStringProperty();
	private final StringProperty userEmail = new SimpleStringProperty();
	private final StringProperty userDepartment = new SimpleStringProperty();
	private final StringProperty userMajor = new SimpleStringProperty();
	private final StringProperty userImageUrl = new SimpleStringProperty();
	private final ReadOnlyBooleanWrapper writePossible = new ReadOnlyBooleanWrapper();

	public AccessDataViewModel() {
		writePossible.bind(
				userFirstName.isNotEmpty()
				.and(userLastName.isNotEmpty())
				.and(userEmail.isNotEmpty())
				.and(userMajor.isNotEmpty())
				.and(userDepartment.isNotEmpty())
		);
	}

	public StringProperty userIdProperty() {
		return userId;
	}

	public StringProperty userDepartment(){
		return userDepartment;
	}

	public StringProperty userFirstNameProperty() {
		return userFirstName;
	}

	public StringProperty userLastNameProperty() {
		return userLastName;
	}

	public StringProperty userEmailProperty() {
		return userEmail;
	}

	public StringProperty userMajorProperty() {
		return userMajor;
	}

	public StringProperty userImageUrlProperty() {
		return userImageUrl;
	}

	public ReadOnlyBooleanProperty isWritePossibleProperty() {
		return writePossible.getReadOnlyProperty();
	}
}
