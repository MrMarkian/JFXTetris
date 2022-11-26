package com.jfxtetris.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
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
    public Label LevelLabel;
    @FXML
    public VBox OutBox;
    @FXML
    public Slider GridSizeSlider;
    @FXML
    public Slider PaddingSlider;
    @FXML
    public Slider NextPiecesCount;

    @FXML
    public AnchorPane RenderAnchor;
    @FXML
    public HBox NextPieceBox;
    @FXML
    public GridPane StatsGrid;

    GameManager gameManager = null;

    @FXML
    private void StartNewGame(){

        if (gameManager != null){
            gameManager.RequestShutdown();
        }

        Platform.runLater(() -> {

            try {
                gameManager = new GameManager(RenderPane,OutBox, NextPieceBox, StatsGrid );
                NextPiecesCount.setMax(gameManager.gameBoard.pieceRandomizer.GetBagSize());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            try {
                gameManager.StartNewGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public GameManager GetGameManager(){
        return gameManager;
    }

    @FXML
    public void SetGridSize(){
        gameManager.boardRenderer.SetRenderSize((int)GridSizeSlider.getValue());
        gameManager.boardRenderer.SetPaddingSize((int) PaddingSlider.getValue());
        gameManager.gameBoard.numberOfNextPieces = (int) NextPiecesCount.getValue();
        RenderPane.requestFocus();
        gameManager.UpdateBoards();

    }

    @FXML
    public void PlayNextTune(){
        gameManager.media.PlayNext();
    }

    @FXML
    public void TestNextPiece(){

        RenderAnchor.getChildren().add(gameManager.tetrinomoRenderer.RenderTetrinomo(2,50,4, gameManager.media));
    }

    @FXML
    public void LevelUp(){
        gameManager.LevelUp();
    }

    @FXML
    public void LoadGameClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Level File");
        File file = fileChooser.showOpenDialog(null);
        if (gameManager == null) {
            try {
                gameManager = new GameManager(RenderPane, OutBox, NextPieceBox, StatsGrid);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        gameManager.LoadGame(file.getPath());
    }

    @FXML
    public void SaveGameClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Level File");
        File file =  fileChooser.showSaveDialog(null);

        gameManager.SaveGame(file.getPath());
    }


    public static void DisplayAlert(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        });
    }
}
