package org.example.postquantum_cryptography.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SceneUtil {

    // Відкриває нову сцену
    public static void openNewScene(Stage stage, String sceneName, AnchorPane currentPane) throws IOException {
        URL resource = SceneUtil.class.getResource("/org/example/postquantum_cryptography/" + sceneName);
        Objects.requireNonNull(resource, "FXML файл не знайдено! " + sceneName);

        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);

        if (stage == null) {
            stage = new Stage();
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Закриває поточне вікно
        if (currentPane != null) {
            Stage currentStage = (Stage) currentPane.getScene().getWindow();
            currentStage.close();
        }
    }

    // Знімає фокус з AnchorPane при кліку
    public static void removeFocusOnClick(AnchorPane pane) {
        if (pane != null) {
            pane.setOnMouseClicked(event -> pane.requestFocus());
        }
    }

    // Повертає користувача на головну сторінку
    public static void returnToMainPage(Button button, String sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneUtil.class.getResource("/org/example/postquantum_cryptography/" + sceneName));
            Parent root = loader.load();

            // Отримуємо поточне вікно (Stage)
            Stage stage = (Stage) button.getScene().getWindow();

            // Встановлюємо нову сцену
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeContent(AnchorPane pane, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneUtil.class.getResource("/org/example/postquantum_cryptography/" + fxmlFile));
            AnchorPane newPane = loader.load();
            pane.getChildren().setAll(newPane.getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeSceneToMain(MouseEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneUtil.class.getResource("/org/example/postquantum_cryptography/" + fxmlFile));
            Parent root = loader.load();

            // Отримуємо поточний Stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Збереження розміру вікна
            double width = stage.getWidth();
            double height = stage.getHeight();

            // Встановлення нової сцени
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Відновлення розміру
            stage.setWidth(width);
            stage.setHeight(height);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
