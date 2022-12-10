package com.jfxtetris.Controllers;

import com.jfxtetris.Models.*;
import com.jfxtetris.Tetris;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    private ComboBox<ScoreList> scoringPresetDropDown;

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
    @FXML
    private AnchorPane ScoreingAnchorPane;
    @FXML
    private AnchorPane TimingsAnchorPane;
    @FXML
    private TextField inputDelayEntry;
    @FXML
    private TextField logicDelayEntry;
    @FXML
    private TextField fpsDelayEntry;
    @FXML
    private Slider backroundMusicVolume;

    ScoreManager sm = new ScoreManager();

    @FXML
    void StartNewGameClicked(ActionEvent event) {
        GameSettings settings = new GameSettings();
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
        settings.INPUTdelay = Integer.parseInt(inputDelayEntry.getText());
        settings.lOGICdelay = Integer.parseInt(logicDelayEntry.getText());
        settings.FPSdelay = Integer.parseInt(fpsDelayEntry.getText());
        settings.backGroundMusicVolume = ((int) backroundMusicVolume.getValue());
        settings.scoreList = scoringPresetDropDown.getSelectionModel().getSelectedItem();
        MainWindowController MainUi = Tetris.getMainUIController();
        MainUi.gameSettings = settings;
       Stage closed = (Stage) StartButton.getScene().getWindow();
       closed.close();
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
        scoringPresetDropDown.getItems().setAll(sm.tetrisScoreList);
        scoringPresetDropDown.getSelectionModel().select(0);
        RefreshScoringList();
    }

    @FXML
    private void RefreshScoringList() {
        ScoreingAnchorPane.getChildren().clear();
        VBox scorelistVbox = new VBox();
        scoringPresetDropDown.getSelectionModel().getSelectedItem().tetrisScore.forEach((k, v) -> {
                    HBox tmpBox = new HBox();
                    tmpBox.setSpacing(5);
                    tmpBox.setPadding(new Insets(5));
                    Label lineType = new Label(k.toString());
                    lineType.setMinWidth(60);
                    TextField value = new TextField(v.toString());
                    tmpBox.getChildren().add(lineType);
                    tmpBox.getChildren().add(value);
                    scorelistVbox.getChildren().add(tmpBox);
                });
        ScoreingAnchorPane.getChildren().add(scorelistVbox);
    }
}
