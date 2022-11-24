package com.jfxtetris.Controllers;

import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Views.BoardRenderer;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    GameBoard gameBoard = new GameBoard();
    BoardRenderer boardRenderer = new BoardRenderer(gameBoard);
    Pane RenderPane;
    Timer FPSTimer = new Timer();
    long delay =5;
    int totalGameTicks = 0;

    CurrentPiece player1 = new CurrentPiece();

    boolean gameOver = false;

    public class CurrentPiece{
        public int PieceType = 1;
        public int Rotation = 0;
        public int XPos = gameBoard.GetBoardWidth() /2;
        public int YPos = 0;
    }



    public GameManager(Pane renderPane){
        RenderPane = renderPane;
    }

    public void StartNewGame(){
            //process input


            //Game Logic

            //Game Timing & Rendering

            FPSTimer.scheduleAtFixedRate(new OnTimer(),0, delay * 100L);
    }

   private class OnTimer extends TimerTask{

       @Override
       public void run() {
           totalGameTicks++;

           Platform.runLater(()->{

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
