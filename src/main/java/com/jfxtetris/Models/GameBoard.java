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
        return switch (r % 4) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            case 3 -> 3 - py + (px * 4);
            default -> 0;
        };

    }

    boolean DoesPieceFit(int nTetromino, int nRotation, int nPosX, int nPosY){

        for(int px = 0; px < 4; px++){
            for(int py = 0; py< 4; py++){

                int pi = Rotate(px,py, nRotation);
                int fi = (nPosY + py) * BoardWidth + (nPosX + px);

                if (nPosX + px >= 0 && nPosX + px < BoardHeight){
                    if(nPosY + py >= 0 && nPosY + py < BoardWidth) {
                        if(Tetrominos.shapes.get(nTetromino).charAt(pi) == 'x' && playField[fi] !=0) return false;
                    }
                }
            }
        }

        return true;
    }

}
