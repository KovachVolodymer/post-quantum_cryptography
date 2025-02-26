package org.example.postquantum_cryptography.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.postquantum_cryptography.util.SceneUtil;

public class MainPageController {

    @FXML
    private AnchorPane mainPane;

    // Відкриває сторінку Dilithium всередині mainPane (без створення нового вікна)
    public void DilithiumAlgoritm(MouseEvent mouseEvent) {
        SceneUtil.changeContent(mainPane, "dilithium-algorithm.fxml");
    }

    // Відкриває сторінку ML-KEM-512 (заміна тільки вмісту)
    public void mlkm512Algoritm(MouseEvent mouseEvent) {
        SceneUtil.changeContent(mainPane, "ml-kem-512-algorithm.fxml");
    }

}
