package org.example.postquantum_cryptography.util;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Set;

public class McElieceEncryption1 {
    public static void main(String[] args) throws Exception {
        // Додаємо провайдери BouncyCastle
        Security.addProvider(new BouncyCastlePQCProvider());
        Security.addProvider(new BouncyCastleProvider());

        provider();
        System.out.println(Security.getProvider("BCPQC"));
        if (Security.getProvider("BCPQC") == null) {
            System.out.println("BCPQC provider is not available!");
        } else {
            System.out.println("BCPQC provider is loaded successfully.");
        }

        for (Provider.Service service : Security.getProvider("BCPQC").getServices()) {
            System.out.println(service.getType() + ": " + service.getAlgorithm());
        }

        // Генерація ключової пари
        KeyPair keyPair = generateMcElieceKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Повідомлення для шифрування
        String message = "Hello, Post-Quantum World!";
        byte[] plaintext = message.getBytes();

        // Шифрування
        byte[] ciphertext = encrypt(publicKey, plaintext);
        System.out.println("Ciphertext: " + new String(ciphertext));

        // Розшифрування
        byte[] decryptedText = decrypt(privateKey, ciphertext);
        System.out.println("Decrypted: " + new String(decryptedText));



    }



    public static KeyPair generateMcElieceKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("McElieceCCA2", "BCPQC"); // Використовуй CCA2 варіант
        keyGen.initialize(new McElieceCCA2KeyGenParameterSpec(10)); // Використання конкретного варіанту

        return keyGen.generateKeyPair();
    }


    public static byte[] encrypt(PublicKey publicKey, byte[] plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance("McElieceCCA2", "BCPQC"); // Використовуємо McElieceCCA2
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance("McElieceCCA2", "BCPQC"); // Використовуємо McElieceCCA2
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(ciphertext);
    }

    public static void provider() {
        Provider[] providers = Security.getProviders();

        for (Provider provider : providers) {
            System.out.println("Provider: " + provider.getName());

            // Отримуємо всі доступні алгоритми для цього провайдера
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                String algorithm = service.getAlgorithm();
                if (algorithm.toLowerCase().contains("mceliece")) {
                    System.out.println("\tSupported Algorithm: " + algorithm);
                }
            }
        }
    }
}



