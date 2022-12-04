package com.jfxtetris.Controllers;

import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Models.GameModes;
import com.jfxtetris.Models.GameSettings;
import com.jfxtetris.Models.PieceRandomizer;
import com.jfxtetris.Tetris;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGameWindowController extends Application implements Initializable {

    @FXML
    private ToggleGroup History;

    @FXML
    private Button StartButton;

    @FXML
    private CheckBox allowWallKicks;

    @FXML
    private ComboBox<GameModes> gameModeCombo;
    @FXML
    private ComboBox<PieceRandomizer.GameModeRND> randomiserDropdown;

    @FXML
    private TextField playerNameText;

    @FXML
    private CheckBox renderAboveTop;

    @FXML
    private CheckBox saveHighScore;

    @FXML
    private CheckBox showGhostPiece;

    @FXML
    private CheckBox timedGameCheckBox;
    @FXML
    private CheckBox backgroundMusicCheckbox;
    @FXML
    private CheckBox playSoundsCheckbox;
    @FXML
    private CheckBox tetrisCallOutCheckBox;

    private GameSettings settings;

    @FXML
    void StartNewGameClicked(ActionEvent event) {
        settings = new GameSettings();
        settings.playerName = playerNameText.getText();
        settings.allowWallKicks = allowWallKicks.isSelected();
        settings.allowSavedGame = saveHighScore.isSelected();
        settings.showGhostPiece = showGhostPiece.isSelected();
        settings.renderAboveTopBoarder = renderAboveTop.isSelected();
        settings.playBackGroundMusic = backgroundMusicCheckbox.isSelected();
        settings.playRowVoices = tetrisCallOutCheckBox.isSelected();
        settings.playSoundEffects = playSoundsCheckbox.isSelected();
        settings.timedGame = timedGameCheckBox.isSelected();
        settings.randomizer = new PieceRandomizer(randomiserDropdown.getSelectionModel().getSelectedItem());
        MainWindowController MainUi = Tetris.getMainUIController();

        MainUi.gameSettings = settings;
       Stage closed = (Stage) StartButton.getScene().getWindow();
       closed.close();

    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        randomiserDropdown.getItems().setAll(PieceRandomizer.GameModeRND.values());
        gameModeCombo.getItems().setAll(GameModes.values());
        randomiserDropdown.getSelectionModel().select(0);
        gameModeCombo.getSelectionModel().select(0);
    }
}
