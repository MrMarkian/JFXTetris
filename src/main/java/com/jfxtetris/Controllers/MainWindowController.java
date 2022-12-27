package com.jfxtetris.Controllers;

import com.jfxtetris.Models.GameSettings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

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
    public Label gameTimeLabel;
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

    @FXML
    public CheckMenuItem DarkModeMenu;

    GameManager gameManager = null;
    public GameSettings gameSettings = new GameSettings();
    @FXML
    private void StartNewGame(){

        if (gameManager != null){
            gameManager.RequestShutdown();
        }

        try {

            Stage dialog = new Stage();
            dialog.initOwner(RenderPane.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource("/NewGameWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            dialog.setResizable(false);
            dialog.setTitle("New Game");
            dialog.setScene(scene);
            dialog.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Settings:" + gameSettings);

        Platform.runLater(() -> {

            try {
                if(gameSettings.randomizer == null)
                    return;
                gameManager = new GameManager(gameSettings,RenderPane,OutBox, NextPieceBox, StatsGrid );
                NextPiecesCount.setMax(gameManager.gameBoard.settings.randomizer.GetBagSize());
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
                gameManager = new GameManager(new GameSettings(),RenderPane, OutBox, NextPieceBox, StatsGrid);
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

    @FXML
    public void SwitchtoNextTheme(){
        if(gameManager == null)
            return;
        gameManager.media.NextTheme();
    }

    @FXML
    public void showThemeManager(){
        if(gameManager == null) //Todo: Clean this up. program should be able to show window manager if no game playing..
            return;
        ThemeWindowController themeWindowController ;
        themeWindowController = new ThemeWindowController(gameManager.media);
        themeWindowController.SetMananger(gameManager.media);
        try {
            themeWindowController.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void SwitchToDarkMode(){
        RenderPane.getScene().getStylesheets().clear();
        if (DarkModeMenu.isSelected())
            RenderPane.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/DarkMode.css")).toExternalForm());
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
