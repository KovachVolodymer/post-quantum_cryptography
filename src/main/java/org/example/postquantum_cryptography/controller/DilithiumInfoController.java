package org.example.postquantum_cryptography.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class DilithiumInfoController {

    @FXML
    private MFXButton backButton;

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> returnToMainPage());
    }

    private void returnToMainPage() {
        try {
            // Завантажуємо головне вікно
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/postquantum_cryptography/main-view.fxml"));
            Parent root = loader.load();

            // Отримуємо поточне вікно (Stage)
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Встановлюємо нову сцену
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
