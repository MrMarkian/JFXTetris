package com.jfxtetris;

import com.jfxtetris.Controllers.MainWindowController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Tetris extends Application {
    private static MainWindowController ui;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Tetris.class.getResource("/Main-Window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 1000);
        ui = fxmlLoader.getController();
        stage.setTitle("Tetris!");
        stage.setFullScreen(true);
      //  stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean lostFocus, Boolean gainFocus) {
                if(getMainUIController().GetGameManager() == null || getMainUIController().GetGameManager().getGameBoard() == null)
                    return;
                if(lostFocus) {
                    if(!getMainUIController().GetGameManager().getGameBoard().gameOver) {
                        getMainUIController().GetGameManager().PauseGame(true);
                        stage.setTitle("PAUSED!");
                    }
                }
                if(gainFocus) {
                    if(!getMainUIController().GetGameManager().getGameBoard().gameOver) {
                        getMainUIController().GetGameManager().PauseGame(false);
                        stage.setTitle("Tetris!");
                    }
                }
            }
        });
        stage.show();

        stage.setOnCloseRequest(we -> {
            System.out.println("Stage is closing");
             ui = fxmlLoader.getController();
            if(ui.GetGameManager() != null)
                ui.GetGameManager().RequestShutdown();
        });

    }
    public static MainWindowController getMainUIController(){
        return ui;
    }
    public static void main(String[] args) {
        launch();
    }
}