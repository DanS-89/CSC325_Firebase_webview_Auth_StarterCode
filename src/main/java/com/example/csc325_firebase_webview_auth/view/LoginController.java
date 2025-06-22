package com.example.csc325_firebase_webview_auth.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton, signUpButton;

    private static final String API_KEY ="AIzaSyBV-Csx6BSIUBGWiU2ar3OltY_1yH3sU9o";

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()){
            showAlert("Error", "Please enter a valid email address and password");
            return;
        }

        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",email,password);

         try (OutputStream os = conn.getOutputStream()) {
             byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
             os.write(input, 0, input.length);
         }

         InputStream inputStream = conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream();
         String response = new BufferedReader(new InputStreamReader(inputStream)).lines().reduce("", String::concat);
         JSONObject jsonObject = new JSONObject(response);

         if(conn.getResponseCode() == 200) {
             String idToken = jsonObject.getString("idToken");
             try {
                 App.setRoot("/files/AccessFBView.fxml");
             } catch (Exception e) {
                 e.printStackTrace();
             }
         } else {
             String error = jsonObject.getJSONObject("error").getString("message");

             showAlert("Login Failed", error);
         }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
        try {
            App.setRoot("/files/SignUp.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
