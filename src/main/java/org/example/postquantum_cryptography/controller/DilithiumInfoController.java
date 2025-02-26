package org.example.postquantum_cryptography.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import org.example.postquantum_cryptography.util.SceneUtil;

public class DilithiumInfoController {

    @FXML
    private MFXButton backButton;

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> SceneUtil.returnToMainPage(backButton, "dilithium-algorithm.fxml"));
    }
}
