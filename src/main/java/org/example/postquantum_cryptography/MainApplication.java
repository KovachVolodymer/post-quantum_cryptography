package org.example.postquantum_cryptography;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 620);
        stage.setScene(scene);
        stage.setTitle("Post-Quantum Cryptography");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}