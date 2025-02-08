package com.jfxtetris.Controllers;

import com.jfxtetris.Models.*;
import com.jfxtetris.Views.BoardRenderer;
import com.jfxtetris.Views.TetrinomoRenderer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;


public class GameManager {
    GameBoard gameBoard;
    BoardRenderer boardRenderer;
    MediaManager media = MediaManager.getInstance();
    TetrinomoRenderer tetrinomoRenderer = new TetrinomoRenderer();

    Pane RenderPane;
    VBox outputBox;
    HBox nextPieceRender;
    GridPane stats;

    Timer FPSTimer;
    Timer LogicTimer;
    Timer InputTimer;
    Boolean isPaused;
    InputHandler input ;
    private boolean hardDrop;
    private int hardDropLines =0;
    public GameTimer gameTime;

    public GameManager(GameSettings settings, Pane renderPane, VBox out, HBox nextPiece, GridPane StatsGrid) throws URISyntaxException {
        RenderPane = renderPane;
        input = new InputHandler(RenderPane.getScene());
        outputBox = out;
        nextPieceRender = nextPiece;
        stats = StatsGrid;
        gameBoard = new GameBoard(settings);
        boardRenderer =  new BoardRenderer(this);
        gameTime = new GameTimer();
    }

    public GameManager()throws URISyntaxException{

    }

    //Todo: Add Line Limit Mode
    //Todo: Add limited time mode
    //Todo: Add garbage mode
    //Todo: Add Premade levels
    //Todo: Adjustable game timings

    public void StartNewGame() {

        if(gameBoard.settings.startWithZZZZ){
            gameBoard.pieceHistory.add(2);
            gameBoard.pieceHistory.add(2);
            gameBoard.pieceHistory.add(2);
            gameBoard.pieceHistory.add(2);
        }

        if(gameBoard.settings.startWithZSSZ){
            gameBoard.pieceHistory.add(2);
            gameBoard.pieceHistory.add(6);
            gameBoard.pieceHistory.add(6);
            gameBoard.pieceHistory.add(2);
        }

        StartGameTimers();

        if(gameBoard.settings.timedGame)
                gameTime.StartTimer();

            if(gameBoard.settings.playBackGroundMusic)
                media.StartBackgroundMusic();
    }

    private void StartGameTimers() {
        InputTimer = new Timer();
        InputTimer.scheduleAtFixedRate(new OnTimerInputUpdate(),0,gameBoard.settings.INPUTdelay);
        //Game Logic
        LogicTimer = new Timer();
        LogicTimer.scheduleAtFixedRate(new OnTimerLogicUpdate(),0, gameBoard.settings.lOGICdelay);
        //Game Timing & Rendering
        FPSTimer = new Timer();
        FPSTimer.scheduleAtFixedRate(new OnTimerEndGameTick(),0, gameBoard.settings.FPSdelay);
    }

    private void StopGameTimers(){
        InputTimer.cancel();
        LogicTimer.cancel();
        FPSTimer.cancel();
    }


    //Todo: Allow Wall Kicks
    //Todo: Show ghost piece
    //Todo: Render above top border (adjustable)

    private void RunInputs(){

        if(gameBoard.fallingPiece == null || hardDrop)
            return;

        if(input.leftPressed.get() || input.rightPressed.get())
            if(gameBoard.settings.playSoundEffects)
                media.PlaySoundClip(SoundTypes.MoveSound);

        if(input.tPressed.get())
            media.NextTheme();
        if(input.bPressed.get())
            media.PlayNext();

        if(input.leftPressed.get()){


            //LEFT KEY
            if(gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation, gameBoard.fallingPiece.XPos -1, gameBoard.fallingPiece.YPos)){
                gameBoard.fallingPiece.XPos --;
                input.leftPressed.set(false);
              //  UpdateBoards();
            }
        }

        if(input.rightPressed.get()){ //RIGHT KEY
            if(gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation, gameBoard.fallingPiece.XPos + 1, gameBoard.fallingPiece.YPos)){
                gameBoard.fallingPiece.XPos ++;
                input.rightPressed.set(false);
              //  UpdateBoards();
            }
        }

        if(input.downPressed.get()){
            if(gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation, gameBoard.fallingPiece.XPos, gameBoard.fallingPiece.YPos + 1)){
                gameBoard.fallingPiece.YPos++;
                //input.downPressed.set(false);
                if(gameBoard.settings.playSoundEffects)
                    media.PlaySoundClip(SoundTypes.SoftDrop);
              //  UpdateBoards();
            }
        }

        if(input.upPressed.get()){

            if(gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation + 1, gameBoard.fallingPiece.XPos, gameBoard.fallingPiece.YPos)){
                gameBoard.fallingPiece.Rotation ++;
                input.spacePressed.set(false);
                input.upPressed.set(false);
                if(gameBoard.settings.playSoundEffects)
                    media.PlaySoundClip(SoundTypes.SpinSound);
              //  UpdateBoards();
            } else if (gameBoard.settings.allowWallKicks && gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation + 1, gameBoard.fallingPiece.XPos + 1, gameBoard.fallingPiece.YPos) ) { //wallkick

                gameBoard.fallingPiece.Rotation ++;
                gameBoard.fallingPiece.XPos ++;
                input.spacePressed.set(false);
                input.upPressed.set(false);
                if(gameBoard.settings.playSoundEffects)
                    media.PlaySoundClip(SoundTypes.SpinSound);
               // UpdateBoards();

            } else if (gameBoard.settings.allowWallKicks && gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation + 1, gameBoard.fallingPiece.XPos - 1, gameBoard.fallingPiece.YPos)) {
                gameBoard.fallingPiece.Rotation ++;
                gameBoard.fallingPiece.XPos --;
                input.spacePressed.set(false);
                input.upPressed.set(false);
                if(gameBoard.settings.playSoundEffects)
                    media.PlaySoundClip(SoundTypes.SpinSound);
              //  UpdateBoards();
            }
        }

        if(input.spacePressed.get()){ //Hard Drop
            hardDrop = true;
//            if(gameBoard.settings.playSoundEffects)
//                media.PlaySoundClip(SoundTypes.HardDrop);
        }

       // UpdateBoards();
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
                if(gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation, gameBoard.fallingPiece.XPos, gameBoard.fallingPiece.YPos+1)){
                    gameBoard.fallingPiece.YPos++;
                    if(hardDrop){
                        gameBoard.forceDown = true;
                        hardDropLines ++;
                    }
                    else
                        gameBoard.forceDown = false;
                }else{ //end turn
                    // - lock the playfield
                    if(hardDrop) {
                        if(gameBoard.settings.playSoundEffects)
                            media.PlaySoundClip(SoundTypes.HardDrop);
                    }
                        hardDrop = false;

                    gameBoard.player.Score += (hardDropLines * 10);
                    hardDropLines = 0;

                    for(int px = 0; px < 4; px++){
                        for(int py = 0; py < 4; py++){
                            if(Tetrominos.shapes.get(gameBoard.fallingPiece.PieceType).charAt(gameBoard.Rotate(px,py, gameBoard.fallingPiece.Rotation)) == 'x'){
                                gameBoard.GetBoard()[(gameBoard.fallingPiece.YPos + py) * gameBoard.GetBoardWidth() + (gameBoard.fallingPiece.XPos + px)] = gameBoard.fallingPiece.PieceType;
                            }
                        }

                    }

                   //check for full lines

                    for (int py = 0; py < 4; py++){
                        if(gameBoard.fallingPiece.YPos + py < gameBoard.GetBoardHeight() -1){
                            boolean bLine = true;
                            for(int px = 1; px < gameBoard.GetBoardWidth() -1 ; px++) {
                                bLine &= (gameBoard.GetBoard()[(gameBoard.fallingPiece.YPos + py) * gameBoard.GetBoardWidth() + px]) != 0;
                            }

                            if(bLine){

                               for(int px = 1; px < gameBoard.GetBoardWidth() -1; px++){
                                      gameBoard.GetBoard()[(gameBoard.fallingPiece.YPos + py) * gameBoard.GetBoardWidth() + px] = -1;
                                  }
                                    gameBoard.vLines.add(gameBoard.fallingPiece.YPos + py);
                                    gameBoard.player.Level = gameBoard.totalLines / 10;
                              }

                        }
                    }

                    switch (gameBoard.vLines.size()) {
                        case 1 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Single);

                            media.PlayVoice(1);
                        }
                        case 2 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Double);

                            media.PlayVoice(2);
                        }
                        case 3 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Triple);

                            media.PlayVoice(3);
                        }
                        case 4 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Quad);

                            media.PlayVoice(4);
                        }
                        default -> {
                        }
                    }

                    if(gameBoard.settings.playSoundEffects)
                        media.PlaySoundClip(SoundTypes.TetrisLock);

                    // choose next piece                                                                                                                                                                                                                                                                                                                                                                                      8
                    gameBoard.pieceHistory.add(gameBoard.fallingPiece.PieceType);
                    gameBoard.fallingPiece = null;
                    gameBoard.fallingPiece = gameBoard.new CurrentPiece();
                    input.downPressed.set(false);

                    if(!gameBoard.DoesPieceFit(gameBoard.fallingPiece.PieceType, gameBoard.fallingPiece.Rotation, gameBoard.fallingPiece.XPos, gameBoard.fallingPiece.YPos)){
                        MainWindowController.DisplayAlert("Game Over", "Game Over man.. ", "Game Over");
                        gameBoard.gameOver = true;
                        media.StopBackgroundMusic();
                        if(gameBoard.settings.playSoundEffects)
                            media.PlaySoundClip(SoundTypes.GameOver);
                    }
                }
            }
        }
    }


   private class OnTimerEndGameTick extends TimerTask{

       @Override
       public void run() {
           if(gameBoard.gameOver){
               UpdateBoards();

               return;
           }

           gameBoard.totalGameTicks++;
           if(gameBoard.totalGameTicks % CalculateGameSpeedNES(gameBoard.player.Level) == 0){
               gameBoard.forceDown = true;
           }


           Platform.runLater(()->{

               nextPieceRender.getChildren().clear();
               for(int a = 0; a < gameBoard.numberOfNextPieces; a++) {
                   nextPieceRender.getChildren().add(tetrinomoRenderer.RenderTetrinomo(gameBoard.settings.randomizer.PeekAt(a), 20, 3, media.GetCurrentTheme()));
               }
               if(gameBoard.totalGameTicks % (gameBoard.settings.FPSdelay * 4) == 0) {
                   if (!gameBoard.vLines.isEmpty()) {
                       for (int line : gameBoard.vLines) {
                           for (int px = 1; px < gameBoard.GetBoardWidth() - 1; px++) {
                               for (int py = line; py > 0; py--) {
                                   gameBoard.GetBoard()[py * gameBoard.GetBoardWidth() + px] = gameBoard.GetBoard()[(py - 1) * gameBoard.GetBoardWidth() + px];
                                   gameBoard.GetBoard()[px] = 0;
                               }
                           }
                       }
                       gameBoard.totalLines += gameBoard.vLines.size();
                       hardDropLines = 0;
                       gameBoard.vLines.clear();
                   }
               }
               UpdateBoards();
               UpdateUI();
           });



       }
   }

    private void UpdateUI() {
        for (Node n:outputBox.getChildren()) {
            if(n.getId() == null)
                continue;
           if(n.getId().equals("ScoreLabel")){
               ((Label)n).setText("SCORE: " + gameBoard.player.Score);
           }
           if(n.getId().equals("TotalLinesLabel")){
                ((Label)n).setText("TOTAL LINES: " + gameBoard.totalLines);
           }
            if(n.getId().equals("LevelLabel")){
                ((Label)n).setText("LEVEL: " + gameBoard.player.Level);
            }
            if(n.getId().equals("gameTimeLabel")){
                ((Label)n).setText("TIME: " + gameTime.GetGameTime());
            }
        }
        RenderStatsGrid(stats);

    }

    public void RequestShutdown(){
        gameBoard.gameOver = true;
        gameTime.StopTimer();
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
                renderer.add(tetrinomoRenderer.RenderTetrinomo(a,10,0,media.GetCurrentTheme()),0,a,1,1);

                Label tmpLabel =new Label(String.valueOf(tetrinomoStats[a]));
                tmpLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                renderer.add(tmpLabel,1,a,1,1);
            }

        }
    }

    public void PauseGame(boolean toggle){
        if(gameBoard.gameOver)
            return;
        isPaused = toggle;
        if(isPaused){
            StopGameTimers();
            media.PauseBackgroundMusic(true);
        }else{
           StartGameTimers();
           media.PauseBackgroundMusic(false);
        }

    }


    public void UpdateBoards(){
        Platform.runLater(() -> {
            RenderPane.getChildren().clear();
            RenderPane.getChildren().add(boardRenderer.RenderBoardToPane(gameBoard.fallingPiece));
        });

    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public MediaManager getMediaManager(){
        return media;
    }

    public void LevelUp(){
        gameBoard.player.Level++;
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
            MainWindowController.DisplayAlert("Game Saved", "Game Saved", "High Score and Game Timer will be disabled.");
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
            if(gameBoard.settings.playBackGroundMusic){
                media.StartBackgroundMusic();
            }
            gameBoard.settings.timedGame = false;

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        MainWindowController.DisplayAlert("Game Loaded", "Game Loaded", "Game Loaded");
        gameBoard.gameOver = false;
        try {
            StartGameTimers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
