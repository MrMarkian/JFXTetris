package com.jfxtetris.Controllers;

import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Models.Tetrominos;
import com.jfxtetris.Views.BoardRenderer;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GameManager {
    GameBoard gameBoard = new GameBoard();
    BoardRenderer boardRenderer = new BoardRenderer(gameBoard);
    Pane RenderPane;
    Timer FPSTimer = new Timer();
    Timer LogicTimer = new Timer();
    Timer InputTimer = new Timer();
    long FPSdelay =5;
    long lOGICdelay = 3;
    long INPUTdelay = 1;
    int gameSpeed = 3;
    int totalGameTicks = 0;

    boolean forceDown = false;

    InputHandler input ;

    CurrentPiece player1 = new CurrentPiece();

    boolean gameOver = false;

    Sequencer sequencer;

    public class CurrentPiece{
        public int PieceType = ThreadLocalRandom.current().nextInt(1,7+1);
        public int Rotation = 0;
        public int XPos = gameBoard.GetBoardWidth() /2;
        public int YPos = 0;
    }



    public GameManager(Pane renderPane) throws MidiUnavailableException {
        RenderPane = renderPane;
        input = new InputHandler(RenderPane.getScene());
    }

    public void StartNewGame() throws MidiUnavailableException, InvalidMidiDataException, IOException, URISyntaxException {
            //process input
            InputTimer.scheduleAtFixedRate(new OnTimerInputUpdate(),0,INPUTdelay + 10L);
            //Game Logic
            LogicTimer.scheduleAtFixedRate(new OnTimerLogicUpdate(),0, lOGICdelay + 10L);
            //Game Timing & Rendering
            FPSTimer.scheduleAtFixedRate(new OnTimerEndGameTick(),0, FPSdelay * 100L);
        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

        sequencer.open();
        InputStream is = new BufferedInputStream(new FileInputStream(new File("C:\\GIT\\JFXTetris\\src\\main\\resources\\Midi\\Tetris - A Theme.mid")));
        sequencer.setSequence(is);
        sequencer.start();

    }

    private void RunInputs(){

        if(input.leftPressed.get()){ //LEFT KEY
            if(gameBoard.DoesPieceFit(player1.PieceType, player1.Rotation, player1.XPos -1, player1.YPos)){
                player1.XPos --;
                input.leftPressed.set(false);
                UpdateBoards();
                UpdateBackgroundMusic();
            }
        }

        if(input.rightPressed.get()){ //RIGHT KEY
            if(gameBoard.DoesPieceFit(player1.PieceType, player1.Rotation, player1.XPos + 1, player1.YPos)){
                player1.XPos ++;
                input.rightPressed.set(false);
                UpdateBoards();
            }
        }

        if(input.downPressed.get()){
            if(gameBoard.DoesPieceFit(player1.PieceType, player1.Rotation, player1.XPos, player1.YPos + 1)){
                player1.YPos++;
                input.downPressed.set(false);
                UpdateBoards();
            }
        }


        if(input.spacePressed.get() || input.upPressed.get()){
            if(gameBoard.DoesPieceFit(player1.PieceType, player1.Rotation + 1, player1.XPos, player1.YPos)){
                player1.Rotation ++;
                input.spacePressed.set(false);
                input.upPressed.set(false);
                UpdateBoards();
            }
        }
    }

    private void UpdateBackgroundMusic() {
        if(sequencer.getTickLength() == sequencer.getTickPosition()){

        }

    }

    private class OnTimerInputUpdate extends TimerTask{

        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    RunInputs();
                    CheckTimers();
                }
            });
        }
    }

    private void CheckTimers(){
        if(gameOver) {
            InputTimer.cancel();
            InputTimer.purge();
            FPSTimer.cancel();
            FPSTimer.purge();
            LogicTimer.cancel();
            LogicTimer.purge();
        }
    }

    private class OnTimerLogicUpdate extends TimerTask{

        @Override
        public void run() {
            if(forceDown){
                if(gameBoard.DoesPieceFit(player1.PieceType, player1.Rotation, player1.XPos, player1.YPos+1)){
                    player1.YPos++;
                    forceDown = false;
                }else{ //end turn
                    // - lock the playfield

                    for(int px = 0; px < 4; px++){
                        for(int py = 0; py < 4; py++){
                            if(Tetrominos.shapes.get(player1.PieceType).charAt(gameBoard.Rotate(px,py, player1.Rotation)) == 'x'){
                                gameBoard.GetBoard()[(player1.YPos + py) * gameBoard.GetBoardWidth() + (player1.XPos + px)] = player1.PieceType;
                            }
                        }

                    }

                   //check for full lines

                    for (int py = 0; py < 4; py++){
                        if(player1.YPos + py < gameBoard.GetBoardHeight() -1){
                            boolean bLine = true;
                            for(int px = 1; px < gameBoard.GetBoardWidth() -1 ; px++) {
                                bLine &= (gameBoard.GetBoard()[(player1.YPos + py) * gameBoard.GetBoardWidth() + px]) != 0;
                            }

                            if(bLine){
                               for(int px = 1; px < gameBoard.GetBoardWidth() -1; px++){
                                      gameBoard.GetBoard()[(player1.YPos + py) * gameBoard.GetBoardWidth() + px] = 8;
                                  }
                              }

                        }
                    }

                    // choose next piece
                    player1 = null;
                    player1 = new CurrentPiece();


                    if(!gameBoard.DoesPieceFit(player1.PieceType, player1.Rotation, player1.XPos, player1.YPos)){
                        MainWindowController.DisplayAlert("Game Over", "Game Over man.. ", "Game Over");
                        gameOver = true;
                    }


                }

            }
        }
    }


   private class OnTimerEndGameTick extends TimerTask{

       @Override
       public void run() {
           totalGameTicks++;

           Platform.runLater(()->{
               if(totalGameTicks % gameSpeed == 0){
                   forceDown = true;
               }

               UpdateBoards();
               UpdateUI();

           });
       }
   }

    private void UpdateUI() {

    }

    public void UpdateBoards(){
        RenderPane.getChildren().clear();
        RenderPane.getChildren().add(boardRenderer.RenderBoardToPane(player1));
    }

}
