package org.example.postquantum_cryptography.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.postquantum_cryptography.util.SceneUtil;

import java.io.IOException;

public class MainPageController {

    @FXML
    private AnchorPane mainPane;

    // Відкриває сторінку Dilithium
    public void DilithiumAlgoritm(MouseEvent mouseEvent) throws IOException {
        SceneUtil.openNewScene(new Stage(), "dilithium-algorithm.fxml", mainPane);
    }

    // Відкриває сторінку ML-KEM-512
    public void mlkm512Algoritm(MouseEvent mouseEvent) throws IOException {
        SceneUtil.openNewScene(new Stage(), "ml-kem-512-algorithm.fxml", mainPane);
    }
}
