package com.jfxtetris.Models;

import com.jfxtetris.Controllers.GameTimer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameBoard implements Serializable {

    final int BoardWidth = 10;
    final int BoardHeight = 24;
    int[] playField; //Maybe change this datatype
    final public List<Integer> pieceHistory = new ArrayList<>();
    final public List<Integer> vLines = new ArrayList<>();
    public int totalGameTicks = 0;
    public int numberOfNextPieces=1;

    public boolean forceDown = false;
    public boolean gameOver = false;

    public int totalLines = 0;

    final public Player player = new Player();
    final public GameSettings settings;
    public CurrentPiece fallingPiece;
    public GameTimer gameTime;

    public GameBoard(GameSettings settings){
        boardInit();
        this.settings = settings;
        fallingPiece = new CurrentPiece();
        gameTime = new GameTimer();
    }

    private void boardInit(){
        playField = new int [BoardWidth * BoardHeight];

        for(int x=0; x< BoardWidth; x++){
            for(int y = 0; y < BoardHeight; y++){
                playField[y * BoardWidth + x] =(x == 0 || x == BoardWidth - 1 || y == BoardHeight - 1) ? 9 : 0;
            }
        }
    }

    public class CurrentPiece implements Serializable{
        final public int PieceType = settings.randomizer.GetNextPiece();
        public int Rotation = 0;
        public int XPos = GetBoardWidth() / 2;
        public int YPos = 0;
    }

    //--Getter/Setter
    public int[] GetBoard(){
        return playField;
    }
    public int GetBoardWidth(){
        return BoardWidth;
    }
    public int GetBoardHeight(){
        return BoardHeight;
    }

    //----- Methods

    public int Rotate(int px, int py, int r){
        return switch (r % 4) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            case 3 -> 3 - py + (px * 4);
            default -> -1;
        };



    }

    public boolean DoesPieceFit(int nTetromino, int nRotation, int nPosX, int nPosY){

        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++)
            {
                // Get index into piece
                int pi = Rotate(px, py, nRotation);
                // Get index into field
                int fi = (nPosY + py) * BoardWidth + (nPosX + px);

                if (nPosX + px >= 0 && (nPosX + px < BoardWidth))
                {
                    if (nPosY + py >= 0 && nPosY + py < BoardHeight)
                    {
                        // In Bounds so do collision check
                        if (Tetrominos.shapes.get(nTetromino).charAt(pi) == 'x' && playField[fi] != 0)
                            return false;
                    }
                }
                //Todo: Wallkicks go here
            }

        return true;
    }
}
