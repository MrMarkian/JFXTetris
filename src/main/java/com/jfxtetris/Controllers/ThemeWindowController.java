package com.jfxtetris.Controllers;

import com.jfxtetris.Tetris;
import com.jfxtetris.Views.TetrinomoRenderer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ThemeWindowController extends Application implements Initializable {
    @FXML
    public ListView<ThemeSet> themeList = new ListView<>();

    @FXML
    public AnchorPane RenderPreviewAnchor;

    MediaManager mediaManager;
    private boolean inEditMode = false;

    public ThemeWindowController(MediaManager mediaManager) {
        this.mediaManager = mediaManager;
    }

    public ThemeWindowController() {
        super();
    }

    public void SetMananger(MediaManager manager){
        mediaManager = manager;
    }

    @Override
    public void init() throws Exception {
        super.init();
        themeList.setItems(FXCollections.observableArrayList(mediaManager.GetAllThemes()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Tetris.class.getResource("/ThemeWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 750);
        stage.setTitle("Tetris!");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void SwitchThemeSelection(){

    }

    public void RenderPreviewPane(){
        TetrinomoRenderer tetrinomoRenderer = new TetrinomoRenderer();
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        for (int a = 1; a < 8 ; a++){
            hBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(a,20,0,themeList.getSelectionModel().getSelectedItem()));
        }
        RenderPreviewAnchor.getChildren().add(hBox);
    }

    @FXML
    public void NewThemeClick(){
        TextInputDialog td = new TextInputDialog("Please Enter a Theme Name");
        EventHandler<ActionEvent> event = actionEvent -> td.show();
        td.setTitle("New Theme");
        td.setHeaderText("Enter a Theme name");
        td.setContentText("Name");
        Optional<String> result = td.showAndWait();

        result.ifPresent(name -> {
            ThemeSet newTheme = new ThemeSet();
            inEditMode = true;

        });
    }

    @FXML
    public void LoadThemeClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Theme File");
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            mediaManager.LoadTheme(file.getAbsolutePath());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            mediaManager = new MediaManager();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        for (ThemeSet theme : mediaManager.GetAllThemes()) {
         themeList.getItems().add(theme);
         themeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ThemeSet>() {
             @Override
             public void changed(ObservableValue<? extends ThemeSet> observableValue, ThemeSet themeSet, ThemeSet t1) {
                 RenderPreviewPane();
             }
         });
         System.out.println("Loading Theme: " + theme);
        }

    }
}
