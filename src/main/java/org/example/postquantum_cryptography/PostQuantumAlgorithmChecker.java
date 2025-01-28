package org.example.postquantum_cryptography;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

public class PostQuantumAlgorithmChecker {
    public static void main(String[] args) {
        // Додаємо провайдера BouncyCastle
        Security.addProvider(new BouncyCastleProvider());

        // Отримуємо провайдера
        Provider bcProvider = Security.getProvider("BC");

        if (bcProvider == null) {
            System.out.println("BouncyCastle не встановлено.");
            return;
        }

        System.out.println("Перевірка підтримуваних алгоритмів у BouncyCastle...");

        // Виводимо всі сервіси, доступні у BouncyCastle
        Set<Provider.Service> services = bcProvider.getServices();
        for (Provider.Service service : services) {
            String type = service.getType(); // Тип сервісу (Cipher, KeyPairGenerator, Signature тощо)
            String algorithm = service.getAlgorithm(); // Назва алгоритму

            // Виводимо всі алгоритми
            System.out.println(type + ": " + algorithm);
        }

        // Перевірка наявності конкретних алгоритмів
        System.out.println("\nПеревіряємо наявність пост-квантових алгоритмів:");
        checkAlgorithm(services, "KeyPairGenerator", "NTRU");
        checkAlgorithm(services, "Signature", "SPHINCS256");
        checkAlgorithm(services, "Signature", "Dilithium");
    }

    private static void checkAlgorithm(Set<Provider.Service> services, String type, String algorithm) {
        boolean found = services.stream()
                .anyMatch(service -> service.getType().equalsIgnoreCase(type) &&
                        service.getAlgorithm().equalsIgnoreCase(algorithm));

        if (found) {
            System.out.println("Підтримується: " + type + " - " + algorithm);
        } else {
            System.out.println("Не підтримується: " + type + " - " + algorithm);
        }
    }
}
