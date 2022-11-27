package com.jfxtetris;

import com.jfxtetris.Controllers.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Tetris extends Application {


    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Tetris.class.getResource("/Main-Window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 900);
        stage.setTitle("Tetris!");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(we -> {
            System.out.println("Stage is closing");
            MainWindowController ui = fxmlLoader.getController();
            if(ui.GetGameManager() != null)
                ui.GetGameManager().RequestShutdown();
        });

    }

    public static void main(String[] args) {
        launch();
    }
}