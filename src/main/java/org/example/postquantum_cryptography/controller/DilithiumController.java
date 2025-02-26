package org.example.postquantum_cryptography.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.postquantum_cryptography.algoritm.DilithiumUtil;
import org.example.postquantum_cryptography.util.SceneUtil;

import java.io.IOException;

public class DilithiumController {

    public MFXTextField message;
    public MFXTextField copyText;

    @FXML
    private AnchorPane mainPane;

    private final DilithiumUtil dilithiumUtil = new DilithiumUtil();
    private Stage stage = new Stage();

    @FXML
    public void initialize() {
        SceneUtil.removeFocusOnClick(mainPane);
    }

    // Виконує підпис повідомлення
    public void encryptMessage(MouseEvent actionEvent) {
        try {
            String plaintext = message.getText();
            if (plaintext == null || plaintext.isEmpty()) {
                copyText.setText("Введіть повідомлення!");
                return;
            }

            String signature = dilithiumUtil.signMessage(plaintext);
            copyText.setText(signature);
        } catch (Exception e) {
            e.printStackTrace();
            copyText.setText("Помилка підпису!");
        }
    }

    // Перевірка підпису
    public void verifySignature(MouseEvent actionEvent) {
        try {
            String plaintext = message.getText();
            String signature = copyText.getText();

            if (plaintext.isEmpty() || signature.isEmpty()) {
                copyText.setText("Немає даних для перевірки!");
                return;
            }

            boolean isValid = dilithiumUtil.verifySignature(plaintext, signature);
            copyText.setText(isValid ? "Підпис валідний" : "Підпис НЕ валідний");
        } catch (Exception e) {
            e.printStackTrace();
            copyText.setText("Помилка перевірки підпису!");
        }
    }

    // Відкриває нову сцену "dilithium-info.fxml"
    public void dilithium(MouseEvent mouseEvent) throws IOException {
        SceneUtil.openNewScene(stage, "dilithium-info.fxml", mainPane);
    }

    public void backButton(MouseEvent mouseEvent) {
        SceneUtil.changeSceneToMain(mouseEvent, "main-page.fxml");
    }


}
