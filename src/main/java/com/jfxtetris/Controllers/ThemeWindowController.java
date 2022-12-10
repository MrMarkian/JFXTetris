package com.jfxtetris.Controllers;

import com.jfxtetris.Tetris;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ThemeWindowController extends Application {
    @FXML
     final ListView<ThemeSet> themeList = new ListView<>();

    MediaManager mediaManager;

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
}
