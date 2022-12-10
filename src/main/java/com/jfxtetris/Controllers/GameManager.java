package com.jfxtetris.Controllers;

import com.jfxtetris.Models.*;
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
    GameBoard gameBoard;
    BoardRenderer boardRenderer;
    MediaManager media = new MediaManager();
    TetrinomoRenderer tetrinomoRenderer = new TetrinomoRenderer();

    Pane RenderPane;
    VBox outputBox;
    HBox nextPieceRender;
    GridPane stats;

    Timer FPSTimer;
    Timer LogicTimer;
    Timer InputTimer;


    InputHandler input ;
    private boolean hardDrop;
    private int hardDropLines =0;

    public GameManager(GameSettings settings, Pane renderPane, VBox out, HBox nextPiece, GridPane StatsGrid) throws URISyntaxException {
        RenderPane = renderPane;
        input = new InputHandler(RenderPane.getScene());
        outputBox = out;
        nextPieceRender = nextPiece;
        stats = StatsGrid;
        gameBoard = new GameBoard(settings);
        boardRenderer =  new BoardRenderer(this);
    }

    //Todo: Add Line Limit Mode
    //Todo: Add limited time mode
    //Todo: Add garbage mode
    //Todo: Add Premade levels
    //Todo: Adjustable game timings

    public void StartNewGame() {
            //process input

            InputTimer = new Timer();
            InputTimer.scheduleAtFixedRate(new OnTimerInputUpdate(),0,gameBoard.settings.INPUTdelay);
            //Game Logic
            LogicTimer = new Timer();
            LogicTimer.scheduleAtFixedRate(new OnTimerLogicUpdate(),0, gameBoard.settings.lOGICdelay);
            //Game Timing & Rendering
            FPSTimer = new Timer();
            FPSTimer.scheduleAtFixedRate(new OnTimerEndGameTick(),0, gameBoard.settings.FPSdelay);
            if(gameBoard.settings.playBackGroundMusic)
             media.StartBackgroundMusic();
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
            if(gameBoard.settings.playSoundEffects)
                media.PlaySoundClip(SoundTypes.HardDrop);
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
                    hardDrop = false;
                    System.out.println("Hard Drop Lines:" + hardDropLines);
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
                            System.out.println("Adding :" + gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Single) + " To score .. now:" + gameBoard.player.Score);
                            media.PlayVoice(1);
                        }
                        case 2 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Double);
                            System.out.println("Adding :" + gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Double) + " To score");
                            media.PlayVoice(2);
                        }
                        case 3 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Triple);
                            System.out.println("Adding :" + gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Triple) + " To score");
                            media.PlayVoice(3);
                        }
                        case 4 -> {
                            gameBoard.player.Score += gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Quad);
                            System.out.println("Adding :" + gameBoard.settings.scoreList.tetrisScore.get(TetrisType.Quad) + " To score");
                            media.PlayVoice(4);
                        }
                        default -> {
                        }
                    }

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

           Platform.runLater(()->{
               if(gameBoard.totalGameTicks % CalculateGameSpeedNES(gameBoard.player.Level) == 0){
                   gameBoard.forceDown = true;
               }

               nextPieceRender.getChildren().clear();
               for(int a = 0; a < gameBoard.numberOfNextPieces; a++) {
                   nextPieceRender.getChildren().add(tetrinomoRenderer.RenderTetrinomo(gameBoard.settings.randomizer.PeekAt(a), 20, 3, media));
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
                    gameBoard.totalLines += gameBoard.vLines.size();
                   hardDropLines = 0;
                   gameBoard.vLines.clear();
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
