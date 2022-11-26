package com.jfxtetris.Controllers;

import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Models.Tetrominos;
import com.jfxtetris.Views.BoardRenderer;
import com.jfxtetris.Views.TetrinomoRenderer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class GameManager {
    GameBoard gameBoard = new GameBoard();
    BoardRenderer boardRenderer = new BoardRenderer(this);
    MediaManager media = new MediaManager();
    TetrinomoRenderer tetrinomoRenderer = new TetrinomoRenderer();

    Pane RenderPane;
    VBox outputBox;
    HBox nextPieceRender;
    GridPane stats;

    Timer FPSTimer;
    Timer LogicTimer;
    Timer InputTimer;
    long FPSdelay =1;
    long lOGICdelay = 2;
    long INPUTdelay = 3;

    InputHandler input ;

    public GameManager(Pane renderPane, VBox out, HBox nextPiece, GridPane StatsGrid) throws URISyntaxException {
        RenderPane = renderPane;
        input = new InputHandler(RenderPane.getScene());
        outputBox = out;
        nextPieceRender = nextPiece;
        stats = StatsGrid;
    }

    public void StartNewGame() {
            //process input
            InputTimer = new Timer();
            InputTimer.scheduleAtFixedRate(new OnTimerInputUpdate(),0,INPUTdelay + 10L);
            //Game Logic
            LogicTimer = new Timer();
            LogicTimer.scheduleAtFixedRate(new OnTimerLogicUpdate(),0, lOGICdelay + 10L);
            //Game Timing & Rendering
            FPSTimer = new Timer();
            FPSTimer.scheduleAtFixedRate(new OnTimerEndGameTick(),0, FPSdelay * 10L);
            media.StartBackgroundMusic();
    }

    private void RunInputs(){
        UpdateBackgroundMusic();

        if(gameBoard.player1 == null)
            return;

        if(input.leftPressed.get()){ //LEFT KEY
            if(gameBoard.DoesPieceFit(gameBoard.player1.PieceType, gameBoard.player1.Rotation, gameBoard.player1.XPos -1, gameBoard.player1.YPos)){
                gameBoard.player1.XPos --;
                input.leftPressed.set(false);
                UpdateBoards();

            }
        }

        if(input.rightPressed.get()){ //RIGHT KEY
            if(gameBoard.DoesPieceFit(gameBoard.player1.PieceType, gameBoard.player1.Rotation, gameBoard.player1.XPos + 1, gameBoard.player1.YPos)){
                gameBoard.player1.XPos ++;
                input.rightPressed.set(false);
                UpdateBoards();
            }
        }

        if(input.downPressed.get()){
            if(gameBoard.DoesPieceFit(gameBoard.player1.PieceType, gameBoard.player1.Rotation, gameBoard.player1.XPos, gameBoard.player1.YPos + 1)){
                gameBoard.player1.YPos++;
                //input.downPressed.set(false);
                UpdateBoards();
            }
        }


        if(input.spacePressed.get() || input.upPressed.get()){
            if(gameBoard.DoesPieceFit(gameBoard.player1.PieceType, gameBoard.player1.Rotation + 1, gameBoard.player1.XPos, gameBoard.player1.YPos)){
                gameBoard.player1.Rotation ++;
                input.spacePressed.set(false);
                input.upPressed.set(false);
                UpdateBoards();
            }
        }
    }

    private void UpdateBackgroundMusic() {


    }

    private class OnTimerInputUpdate extends TimerTask{

        @Override
        public void run() {
            Platform.runLater(() -> {
                RunInputs();
                CheckTimers();
            });
        }
    }

    private void CheckTimers(){
        if(InputTimer==null)
            return;
        if(gameBoard.gameOver) {
            InputTimer.cancel();
            FPSTimer.cancel();
            LogicTimer.cancel();
        }
    }
    private void CheckTimers(boolean forceShutdown){
        gameBoard.gameOver = forceShutdown;
        CheckTimers();
    }


    private class OnTimerLogicUpdate extends TimerTask{

        @Override
        public void run() {

            if(gameBoard.gameOver)
                return;

            if(gameBoard.forceDown){
                if(gameBoard.DoesPieceFit(gameBoard.player1.PieceType, gameBoard.player1.Rotation, gameBoard.player1.XPos, gameBoard.player1.YPos+1)){
                    gameBoard.player1.YPos++;
                    gameBoard.forceDown = false;
                }else{ //end turn
                    // - lock the playfield

                    for(int px = 0; px < 4; px++){
                        for(int py = 0; py < 4; py++){
                            if(Tetrominos.shapes.get(gameBoard.player1.PieceType).charAt(gameBoard.Rotate(px,py, gameBoard.player1.Rotation)) == 'x'){
                                gameBoard.GetBoard()[(gameBoard.player1.YPos + py) * gameBoard.GetBoardWidth() + (gameBoard.player1.XPos + px)] = gameBoard.player1.PieceType;
                            }
                        }

                    }

                   //check for full lines

                    for (int py = 0; py < 4; py++){
                        if(gameBoard.player1.YPos + py < gameBoard.GetBoardHeight() -1){
                            boolean bLine = true;
                            for(int px = 1; px < gameBoard.GetBoardWidth() -1 ; px++) {
                                bLine &= (gameBoard.GetBoard()[(gameBoard.player1.YPos + py) * gameBoard.GetBoardWidth() + px]) != 0;
                            }

                            if(bLine){

                               for(int px = 1; px < gameBoard.GetBoardWidth() -1; px++){
                                      gameBoard.GetBoard()[(gameBoard.player1.YPos + py) * gameBoard.GetBoardWidth() + px] = -1;
                                  }
                                gameBoard.vLines.add(gameBoard.player1.YPos + py);
                                for (int a=1; a <= gameBoard.vLines.size(); a++){
                                    if(gameBoard.totalLines %(a+9) == 0 && gameBoard.totalLines > 0)
                                        LevelUp();
                                }

                              }

                        }
                    }

                    // choose next piece
                    gameBoard.pieceHistory.add(gameBoard.player1.PieceType);
                    gameBoard.player1 = null;
                    gameBoard.player1 = gameBoard.new CurrentPiece();
                    input.downPressed.set(false);

                    if(!gameBoard.DoesPieceFit(gameBoard.player1.PieceType, gameBoard.player1.Rotation, gameBoard.player1.XPos, gameBoard.player1.YPos)){
                        MainWindowController.DisplayAlert("Game Over", "Game Over man.. ", "Game Over");
                        gameBoard.gameOver = true;
                    }


                }

            }
        }
    }


   private class OnTimerEndGameTick extends TimerTask{

       @Override
       public void run() {
           if(gameBoard.gameOver)
               return;
           gameBoard.totalGameTicks++;

           Platform.runLater(()->{
               if(gameBoard.totalGameTicks % CalculateGameSpeedNES(gameBoard.playerLevel) == 0){
                   gameBoard.forceDown = true;
               }



               UpdateBoards();
               UpdateUI();


               nextPieceRender.getChildren().clear();
               for(int a = 0; a < gameBoard.numberOfNextPieces; a++) {
                   nextPieceRender.getChildren().add(tetrinomoRenderer.RenderTetrinomo(gameBoard.pieceRandomizer.PeekAt(a), 20, 3, media));
               }

               if(!gameBoard.vLines.isEmpty()){
                   for (int line : gameBoard.vLines) {
                       for(int px = 1; px < gameBoard.GetBoardWidth() -1; px++){
                           for (int py = line; py > 0; py--){
                               gameBoard.GetBoard()[py * gameBoard.GetBoardWidth() + px] = gameBoard.GetBoard()[(py -1) * gameBoard.GetBoardWidth() + px];
                               gameBoard.GetBoard()[px] =0;
                           }
                       }
                   }
                   gameBoard.gameScore += (gameBoard.vLines.size() * 100);
                   gameBoard.totalLines += gameBoard.vLines.size();


                   switch (gameBoard.vLines.size()) {
                       case 1 -> media.PlayVoice(1);
                       case 2 -> media.PlayVoice(2);
                       case 3 -> media.PlayVoice(3);
                       case 4 -> media.PlayVoice(4);
                       case 5 -> media.PlayVoice(5);
                       default -> {

                       }
                   }

                   gameBoard.vLines.clear();

               }
           });
       }
   }

    private void UpdateUI() {
        for (Node n:outputBox.getChildren()) {
            if(n.getId() == null)
                continue;
           if(n.getId().equals("ScoreLabel")){
               ((Label)n).setText("SCORE: " + gameBoard.gameScore);
           }
           if(n.getId().equals("TotalLinesLabel")){
                ((Label)n).setText("TOTAL LINES: " + gameBoard.totalLines);
           }
            if(n.getId().equals("LevelLabel")){
                ((Label)n).setText("LEVEL: " + gameBoard.playerLevel);
            }
        }
        RenderStatsGrid(stats);

    }

    public void RequestShutdown(){
        gameBoard.gameOver = true;
        media.OnClose();
        CheckTimers(true);
    }

    private void RenderStatsGrid(GridPane renderer) {
        renderer.getChildren().clear();

        int[] tetrinomoStats = new int[9];

        if (!gameBoard.pieceHistory.isEmpty()) {
            for (int piece : gameBoard.pieceHistory) {
                tetrinomoStats[piece]++;
            }

            for (int a = 1; a < 8; a++) {
                renderer.add(tetrinomoRenderer.RenderTetrinomo(a,10,0,media),0,a,1,1);

                Label tmpLabel =new Label(String.valueOf(tetrinomoStats[a]));
                tmpLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                renderer.add(tmpLabel,1,a,1,1);
            }

        }
    }


    public void UpdateBoards(){
        RenderPane.getChildren().clear();
        RenderPane.getChildren().add(boardRenderer.RenderBoardToPane(gameBoard.player1));
    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public MediaManager getMediaManager(){
        return media;
    }

    public void LevelUp(){
        gameBoard.playerLevel++;
    }

    private int CalculateGameSpeedNES(int level){
        switch (level){
            case 0: return 48;
            case 1: return 43;
            case 2: return 38;
            case 3: return 33;
            case 4: return 28;
            case 5: return 23;
            case 6: return 18;
            case 7: return 13;
            case 8: return 8;
            case 9: return 6;
            case 10:
            case 11:
            case 12: return 5;
            case 13:
            case 14:
            case 15: return 4;
            case 16:
            case 17:
            case 18: return 3;

        }

        if (level >= 19 && level <= 28)
            return 2;
        if(level> 28)
            return 1;
        return 0;
    }


    public void SaveGame(String Filepath){
        try {
            FileOutputStream fileOut = new FileOutputStream(Filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameBoard);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void LoadGame(String Filepath){
        RequestShutdown();

        try {
            FileInputStream fileIn = new FileInputStream(Filepath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameBoard = (GameBoard) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        MainWindowController.DisplayAlert("Game Loaded", "Game Loaded", "Game Loaded");
        gameBoard.gameOver = false;
        try {
            StartNewGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
