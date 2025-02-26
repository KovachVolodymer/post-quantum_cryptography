package org.example.postquantum_cryptography.algoritm;

import org.bouncycastle.pqc.crypto.crystals.dilithium.*;
import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;

public class DilithiumUtil {

    private DilithiumPublicKeyParameters publicKey;
    private DilithiumPrivateKeyParameters privateKey;

    public DilithiumUtil() {
        generateKeys();
    }

    // Генерація ключів Dilithium
    public void generateKeys() {
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

    // Підпис повідомлення
    public String signMessage(String message) {
        try {
            if (privateKey == null) {
                throw new IllegalStateException("Приватний ключ не згенеровано.");
            }

            DilithiumSigner signer = new DilithiumSigner();
            signer.init(true, privateKey);
            byte[] signature = signer.generateSignature(message.getBytes());

            return Hex.toHexString(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return "Помилка при підписуванні";
        }
    }

    // Перевірка підпису
    public boolean verifySignature(String message, String signatureHex) {
        try {
            if (publicKey == null) {
                throw new IllegalStateException("Публічний ключ не згенеровано.");
            }

            DilithiumSigner verifier = new DilithiumSigner();
            verifier.init(false, publicKey);

            byte[] signature = Hex.decode(signatureHex);
            return verifier.verifySignature(message.getBytes(), signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Геттер для публічного ключа
    public String getPublicKeyHex() {
        return Hex.toHexString(publicKey.getEncoded());
    }

    // Геттер для приватного ключа
    public String getPrivateKeyHex() {
        return Hex.toHexString(privateKey.getEncoded());
    }
}

