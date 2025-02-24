package org.example.postquantum_cryptography.algoritm;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class PostQuantumEncryption {
    public static void main(String[] args) throws Exception {
        // Додаємо провайдера Bouncy Castle PQC
        Security.addProvider(new BouncyCastlePQCProvider());

        // Генерація ключової пари ML-KEM (Kyber)
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ML-KEM-512", "BCPQC");
        KeyPair keyPair = keyGen.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Повідомлення
        String message = "Hello, Post-Quantum World!";
        byte[] plaintext = message.getBytes();

        // Етап 1: Генерація секретного ключа за допомогою ML-KEM
        KeyGenerator aesKeyGen = KeyGenerator.getInstance("AES");
        aesKeyGen.init(256);
        SecretKey aesKey = aesKeyGen.generateKey();

        // Обгортання (encapsulation) секретного ключа AES
        Cipher kemCipher = Cipher.getInstance("ML-KEM-512", "BCPQC");
        kemCipher.init(Cipher.WRAP_MODE, publicKey);
        byte[] wrappedAesKey = kemCipher.wrap(aesKey);

        System.out.println("Wrapped AES Key: " + new String(wrappedAesKey));

        // Етап 2: Шифрування повідомлення за допомогою AES
        Cipher aesCipherEnc = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipherEnc.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] ciphertext = aesCipherEnc.doFinal(plaintext);
        System.out.println("Ciphertext: " + new String(ciphertext));

        // === Декодування ===

        // Розгортання (decapsulation) секретного ключа AES
        kemCipher.init(Cipher.UNWRAP_MODE, privateKey);
        Key unwrappedAesKey = kemCipher.unwrap(wrappedAesKey, "AES", Cipher.SECRET_KEY);

        // Дешифрування повідомлення за допомогою AES
        Cipher aesCipherDec = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipherDec.init(Cipher.DECRYPT_MODE, unwrappedAesKey);
        byte[] decryptedText = aesCipherDec.doFinal(ciphertext);
        System.out.println("Decrypted: " + new String(decryptedText));
    }
}
