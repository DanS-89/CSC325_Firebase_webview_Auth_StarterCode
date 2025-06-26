package com.example.csc325_firebase_webview_auth.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenController {
    @FXML
    private ImageView splashImage;

    @FXML
    public void initialize() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/Login.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) splashImage.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 3000);
    }
}
