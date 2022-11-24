package com.jfxtetris.Models;

public class GameBoard {

    int BoardWidth = 12;
    int BoardHeight = 18;
    int[] playField; //Maybe change this datatype

    //--Constructors

    public GameBoard(){
        boardInit();
    }
    public GameBoard(int width, int height){
        BoardHeight = height;
        BoardWidth = width;
        boardInit();
    }

    private void boardInit(){
        playField = new int [BoardWidth * BoardHeight];

        for (int grid:playField) {
            grid = 0;
        }
    }

    //--Getter/Setter
    public int[] GetBoard(){
        return playField;
    }

    public void SetBoard(){

    }

    public int GetBoardWidth(){
        return BoardWidth;
    }

    public int GetBoardHeight(){
        return BoardHeight;
    }

    //----- Methods

    public int Rotate(int px, int py, int r){
        switch (r % 4) {
            case 0:
                return py * 4 + px;
            case 1:
                return 12 + py - (px * 4);
            case 2:
                return 15 - (py * 4) - px;
            case 3:
                return 3 - py + (px * 4);
            default:
                return -1;
          }

    }

    public boolean DoesPieceFit(int nTetromino, int nRotation, int nPosX, int nPosY){

        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++)
            {
                // Get index into piece
                int pi = Rotate(px, py, nRotation);

                // Get index into field
                int fi = (nPosY + py) * BoardWidth + (nPosX + px);

                // Check that test is in bounds. Note out of bounds does
                // not necessarily mean a fail, as the long vertical piece
                // can have cells that lie outside the boundary, so we'll
                // just ignore them
                if (nPosX + px >= 0 && nPosX + px < BoardWidth)
                {
                    if (nPosY + py >= 0 && nPosY + py < BoardHeight)
                    {
                        // In Bounds so do collision check
                        if(Tetrominos.shapes.get(nTetromino).charAt(pi) != '.' && playField[fi] !=0)
                            return false; // fail on first hit
                    } else return false;
                } else return false;
            }

        return true;
    }

}
