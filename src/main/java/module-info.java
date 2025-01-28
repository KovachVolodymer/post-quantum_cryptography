module org.example.postquantum_cryptography {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires org.bouncycastle.provider;


    opens org.example.postquantum_cryptography to javafx.fxml;
    exports org.example.postquantum_cryptography;
    exports org.example.postquantum_cryptography.controller;
    opens org.example.postquantum_cryptography.controller to javafx.fxml;
}