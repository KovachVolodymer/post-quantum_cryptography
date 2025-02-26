package org.example.postquantum_cryptography.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.example.postquantum_cryptography.algoritm.PostQuantumEncryptionUtil;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MlKem512AlgorithmController {

    @FXML
    private MFXTextField message;

    @FXML
    private MFXTextField copyText;

    private final PostQuantumEncryptionUtil pqUtil = new PostQuantumEncryptionUtil();

    public MlKem512AlgorithmController() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    @FXML
    public void encryptMessage(MouseEvent mouseEvent) {
        try {
            // Отримання тексту з поля введення
            String inputMessage = message.getText();

            if (inputMessage == null || inputMessage.isEmpty()) {
                copyText.setText("Введіть повідомлення!");
                return;
            }

            // Генерація AES-ключа
            SecretKey aesKey = pqUtil.generateAesKey();

            // Шифрування повідомлення
            byte[] encryptedMessage = pqUtil.encryptData(inputMessage.getBytes(), aesKey);

            // Відображення зашифрованого повідомлення
            copyText.setText(new String(encryptedMessage));

        } catch (Exception e) {
            e.printStackTrace();
            copyText.setText("Помилка при шифруванні!");
        }
    }

    public void Ml512Algorithm(MouseEvent mouseEvent) {
    }
}
