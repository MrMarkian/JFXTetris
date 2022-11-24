package com.jfxtetris.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

public class MainWindowController {

    @FXML
    public Pane RenderPane;

    @FXML
    private void StartNewGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GameManager gameManager = new GameManager(RenderPane);
                gameManager.StartNewGame();
            }
        });

    }



    public static void DisplayAlert(String title, String header, String content) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        });


    }
}
