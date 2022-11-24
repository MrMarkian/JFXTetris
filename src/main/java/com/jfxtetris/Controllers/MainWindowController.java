package com.jfxtetris.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.net.URISyntaxException;

public class MainWindowController {

    @FXML
    public Pane RenderPane;

    @FXML
    public Label ScoreLabel;

    @FXML
    public Label TotalLinesLabel;

    @FXML
    public Label StatsLabel;

    @FXML
    public VBox OutBox;

    @FXML
    public Slider GridSizeSlider;

    @FXML
    public Slider PaddingSlider;

    GameManager gameManager = null;

    @FXML
    private void StartNewGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    gameManager = new GameManager(RenderPane,OutBox );
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

    @FXML
    public void SetGridSize(MouseEvent event){
        gameManager.boardRenderer.SetRenderSize((int)GridSizeSlider.getValue());
        gameManager.boardRenderer.SetPaddingSize((int) PaddingSlider.getValue());
        gameManager.UpdateBoards();
        RenderPane.requestFocus();


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
