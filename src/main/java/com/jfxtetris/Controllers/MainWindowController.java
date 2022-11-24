package com.jfxtetris.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.net.URISyntaxException;

public class MainWindowController {

    @FXML
    public Pane RenderPane;

    @FXML
    private void StartNewGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GameManager gameManager = null;
                try {
                    gameManager = new GameManager(RenderPane);
                } catch (MidiUnavailableException e) {
                    throw new RuntimeException(e);
                }
                try {
                    gameManager.StartNewGame();
                } catch (MidiUnavailableException | InvalidMidiDataException | IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
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
