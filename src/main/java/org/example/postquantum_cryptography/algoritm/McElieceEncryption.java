package org.example.postquantum_cryptography.algoritm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import javax.crypto.Cipher;
import java.security.*;

public class McElieceEncryption {
    public static void main(String[] args) throws Exception {
        // Генерація ключової пари
        Security.addProvider(new BouncyCastlePQCProvider());
        Security.addProvider(new BouncyCastleProvider());

//        Provider bcpqc = Security.getProvider("BCPQC");
//        if (bcpqc != null) {
//            System.out.println("BCPQC provider is loaded.");
//            for (Provider.Service service : bcpqc.getServices()) {
//                System.out.println(service.getType() + ": " + service.getAlgorithm());
//            }
//        } else {
//            System.out.println("BCPQC provider is NOT available!");
//        }
        for (Provider.Service service : Security.getProvider("BCPQC").getServices()) {
            if (service.getType().equals("KeyPairGenerator")) {
                System.out.println("Supported KeyPairGenerator: " + service.getAlgorithm());
            }
        }



        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("McElieceCCA2", "BCPQC");
        keyGen.initialize(new McElieceCCA2KeyGenParameterSpec(11,50,"SHA-256")); // Використовуємо CCA2 варіант



        KeyPair keyPair = keyGen.generateKeyPair();



        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();



        // Повідомлення для шифрування
        String message = "Hello, Post-Quantum World!";
        byte[] plaintext = message.getBytes();

        // Шифрування
        Cipher cipherEnc = Cipher.getInstance("McElieceCCA2", "BCPQC");
        cipherEnc.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] ciphertext = cipherEnc.doFinal(plaintext);
        System.out.println("Ciphertext: " + new String(ciphertext));

        // Розшифрування
        Cipher cipherDec = Cipher.getInstance("McElieceCCA2", "BCPQC");
        cipherDec.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedText = cipherDec.doFinal(ciphertext);
        System.out.println("Decrypted: " + new String(decryptedText));
    }
}
