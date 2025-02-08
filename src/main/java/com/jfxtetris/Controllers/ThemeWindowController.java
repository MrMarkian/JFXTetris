package com.jfxtetris.Controllers;

import com.jfxtetris.Tetris;
import com.jfxtetris.Views.TetrinomoRenderer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ThemeWindowController extends Application implements Initializable {
    @FXML public ListView<ThemeSet> themeList = new ListView<>();
    @FXML public AnchorPane RenderPreviewAnchor;
    @FXML public AnchorPane IPane;
    @FXML public AnchorPane ZPane;
    @FXML public AnchorPane OPane;
    @FXML public AnchorPane LPane;
    @FXML public AnchorPane JPane;
    @FXML public AnchorPane SPane;
    @FXML public AnchorPane TPane;
    @FXML public AnchorPane BorderPane;
    @FXML public AnchorPane EmptyPane;
    @FXML public AnchorPane BackgroundPane;

  //  MediaManager mediaManager;
    private boolean inEditMode = false;
    public ThemeWindowController() {
        super();
    }

    public void SetMananger(MediaManager manager){
      //  mediaManager = manager;
    }

    @Override
    public void init() throws Exception {
        super.init();
        themeList.setItems(FXCollections.observableArrayList(MediaManager.getInstance().GetAllThemes()));
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

    public void RenderAllPanes(){
        TetrinomoRenderer tetrinomoRenderer = new TetrinomoRenderer();

        //I Piece
        HBox ihBox = new HBox();
        ihBox.setSpacing(5);
        ihBox.setPadding(new Insets(15, 12, 15, 12));
        ihBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(1,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button iUpdateImageButton = new Button("Update");
        iUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        ihBox.getChildren().add(iUpdateImageButton);
        IPane.getChildren().add(ihBox);

        //Z Piece
        HBox zhBox = new HBox();
        zhBox.setSpacing(5);
        zhBox.setPadding(new Insets(15, 12, 15, 12));
        zhBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(2,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button zUpdateImageButton = new Button("Update");
        zUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        zhBox.getChildren().add(zUpdateImageButton);
        ZPane.getChildren().add(zhBox);

        //O Piece
        HBox ohBox = new HBox();
        ohBox.setSpacing(5);
        ohBox.setPadding(new Insets(15, 12, 15, 12));
        ohBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(3,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button oUpdateImageButton = new Button("Update");
        oUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        ohBox.getChildren().add(oUpdateImageButton);
        OPane.getChildren().add(ohBox);

        //L Piece
        HBox lhBox = new HBox();
        lhBox.setSpacing(5);
        lhBox.setPadding(new Insets(15, 12, 15, 12));
        lhBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(4,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button lUpdateImageButton = new Button("Update");
        oUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        lhBox.getChildren().add(lUpdateImageButton);
        LPane.getChildren().add(lhBox);

        //J Piece
        HBox jhBox = new HBox();
        jhBox.setSpacing(5);
        jhBox.setPadding(new Insets(15, 12, 15, 12));
        jhBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(5,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button jUpdateImageButton = new Button("Update");
        oUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        jhBox.getChildren().add(jUpdateImageButton);
        JPane.getChildren().add(jhBox);

        //S Piece
        HBox shBox = new HBox();
        shBox.setSpacing(5);
        shBox.setPadding(new Insets(15, 12, 15, 12));
        shBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(6,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button sUpdateImageButton = new Button("Update");
        oUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        shBox.getChildren().add(sUpdateImageButton);
        SPane.getChildren().add(shBox);

        //T Piece
        HBox thBox = new HBox();
        thBox.setSpacing(5);
        thBox.setPadding(new Insets(15, 12, 15, 12));
        thBox.getChildren().add(tetrinomoRenderer.RenderTetrinomo(7,20,0,themeList.getSelectionModel().getSelectedItem()));

        //Control buttons:
        Button tUpdateImageButton = new Button("Update");
        oUpdateImageButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        thBox.getChildren().add(tUpdateImageButton);
        TPane.getChildren().add(thBox);




    }


    @FXML
    public void NewThemeClick(){
        TextInputDialog td = new TextInputDialog("Please Enter a Theme Name");
     //   EventHandler<ActionEvent> event = actionEvent -> td.show();
        td.setTitle("New Theme");
        td.setHeaderText("Enter a Theme name");
        td.setContentText("Name");
        Optional<String> result = td.showAndWait();

        result.ifPresent(name -> {
            ThemeSet tmpTheme = new ThemeSet();
            tmpTheme.setThemeName(name);
            MediaManager.getInstance().GetAllThemes().add(tmpTheme);
           // System.out.println("Theme Count: " + MediaManager.getInstance().GetAllThemes().stream().count());
            themeList.setItems(FXCollections.observableArrayList(MediaManager.getInstance().GetAllThemes()));
        });
    }

    @FXML
    public void LoadThemeClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Theme File");
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            MediaManager.getInstance().LoadTheme(file.getAbsolutePath());
        }

    }

    @FXML
    public void SaveThemeClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Theme File");
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            MediaManager.getInstance().SaveTheme(file.getAbsolutePath(), themeList.getSelectionModel().getSelectedItem());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (ThemeSet theme : MediaManager.getInstance().GetAllThemes()) {
         themeList.getItems().add(theme);
         themeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ThemeSet>() {
             @Override
             public void changed(ObservableValue<? extends ThemeSet> observableValue, ThemeSet themeSet, ThemeSet t1) {
                 RenderPreviewPane();
                 RenderAllPanes();
             }
         });
         System.out.println("Loading Theme: " + theme);
        }

    }
}
