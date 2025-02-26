package org.example.postquantum_cryptography.algoritm;

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class PostQuantumEncryptionUtil {


    private final KeyPair keyPair;

    public PostQuantumEncryptionUtil() throws NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastlePQCProvider());
        this.keyPair = generateKeyPair();
    }

    /**
     * Генерація ключової пари ML-KEM
     */
    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ML-KEM-512", "BCPQC");
        return keyGen.generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    /**
     * Генерує секретний ключ AES (256 біт)
     */
    public SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    /**
     * Обгортання (шифрування) AES-ключа з використанням ML-KEM
     */
    public byte[] wrapKey(SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ML-KEM-512", "BCPQC");
        cipher.init(Cipher.WRAP_MODE, keyPair.getPublic());
        return cipher.wrap(secretKey);
    }

    /**
     * Розгортання (дешифрування) AES-ключа з використанням ML-KEM
     */
    public SecretKey unwrapKey(byte[] wrappedKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ML-KEM-512", "BCPQC");
        cipher.init(Cipher.UNWRAP_MODE, keyPair.getPrivate());
        return (SecretKey) cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
    }

    /**
     * Шифрування даних з використанням AES
     */
    public byte[] encryptData(byte[] data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * Дешифрування даних з використанням AES
     */
    public byte[] decryptData(byte[] encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }

    public static void main(String[] args) {
        try {
            // 1. Створення об'єкта для роботи з Post-Quantum шифруванням
            PostQuantumEncryptionUtil pqUtil = new PostQuantumEncryptionUtil();

            // 2. Генерація симетричного ключа AES
            SecretKey aesKey = pqUtil.generateAesKey();

            // 3. Шифрування AES-ключа за допомогою ML-KEM
            byte[] wrappedKey = pqUtil.wrapKey(aesKey);
            System.out.println("Wrapped AES Key: " + new String(wrappedKey));

            // 4. Шифрування повідомлення
            String message = "Hello, Post-Quantum World!";
            byte[] encryptedMessage = pqUtil.encryptData(message.getBytes(), aesKey);
            System.out.println("Ciphertext: " + new String(encryptedMessage));

            // 5. Розгортання AES-ключа
            SecretKey unwrappedKey = pqUtil.unwrapKey(wrappedKey);

            // 6. Розшифрування повідомлення
            byte[] decryptedMessage = pqUtil.decryptData(encryptedMessage, unwrappedKey);
            System.out.println("Decrypted: " + new String(decryptedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
