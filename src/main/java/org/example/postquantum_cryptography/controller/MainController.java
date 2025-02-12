package org.example.postquantum_cryptography.controller;


import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.bouncycastle.pqc.crypto.crystals.dilithium.*;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Objects;

public class MainController {

    public MFXTextField message;

    public MFXTextField copyText;

    @FXML
    private AnchorPane mainPane;

    Stage stage = new Stage();

    private DilithiumPublicKeyParameters publicKey;
    private DilithiumPrivateKeyParameters privateKey;

    @FXML
    public void initialize() {
        mainPane.setOnMouseClicked(event -> mainPane.requestFocus());
    }

    // Генерація ключів під час ініціалізації
    public MainController() {
        generateKeys();
    }

    // Метод для генерації ключів Dilithium
    private void generateKeys() {
        try {
            DilithiumKeyPairGenerator keyPairGenerator = new DilithiumKeyPairGenerator();
            DilithiumKeyGenerationParameters params = new DilithiumKeyGenerationParameters(
                    new SecureRandom(),
                    DilithiumParameters.dilithium2 // Вибір рівня безпеки
            );
            keyPairGenerator.init(params);

            var keyPair = keyPairGenerator.generateKeyPair();
            publicKey = (DilithiumPublicKeyParameters) keyPair.getPublic();
            privateKey = (DilithiumPrivateKeyParameters) keyPair.getPrivate();

            System.out.println("Ключі Dilithium успішно згенеровано.");
            System.out.println("Публічний ключ: " + Hex.toHexString(publicKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для підпису повідомлення
    public void encryptMessage(MouseEvent actionEvent) {
        try {
            String plaintext = message.getText();
            System.out.println("Повідомлення для підпису: " + plaintext);

            if (privateKey == null) {
                System.out.println("Приватний ключ не згенеровано.");
                return;
            }

            // Створення підпису
            DilithiumSigner signer = new DilithiumSigner();
            signer.init(true, privateKey);
            byte[] signature = signer.generateSignature(plaintext.getBytes());
            System.out.println("Підпис повідомлення: " + Hex.toHexString(signature));
            copyText.setText(Hex.toHexString(signature));
            // Перевірка підпису (опціонально)
            verifySignature(plaintext.getBytes(), signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для перевірки підпису
    private void verifySignature(byte[] message, byte[] signature) {
        try {
            if (publicKey == null) {
                System.out.println("Публічний ключ не згенеровано.");
                return;
            }

            DilithiumSigner verifier = new DilithiumSigner();
            verifier.init(false, publicKey);

            boolean isValid = verifier.verifySignature(message, signature);
            System.out.println("Чи є підпис дійсним? " + isValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dilithium(MouseEvent mouseEvent) throws IOException {
        openNewScene(stage, "dilithium-info.fxml");
    }


    public void openNewScene(Stage stage, String nameScene) throws IOException {
        URL resource = getClass().getResource("/org/example/postquantum_cryptography/" + nameScene);
        Objects.requireNonNull(resource, "FXML файл не знайдено! " + nameScene);

        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);

        if (stage == null) {
            stage = new Stage();
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Закриття поточного вікна (переконайтеся, що mainPane ініціалізований)
        if (mainPane != null) {
            Stage currentStage = (Stage) mainPane.getScene().getWindow();
            currentStage.close();
        }
    }

}
