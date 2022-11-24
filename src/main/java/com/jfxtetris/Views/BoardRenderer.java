package com.jfxtetris.Views;

import com.jfxtetris.Controllers.GameManager;
import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Models.Tetrominos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardRenderer {

    GameBoard pFieldToRender;
    int RenderSize = 20;

    int[] DoubleBuffer;
    Tetrominos pieces = new Tetrominos();

    public BoardRenderer(GameBoard board){
        pFieldToRender = board;
        DoubleBuffer = new int[pFieldToRender.GetBoardWidth() * pFieldToRender.GetBoardHeight()];
        RefreshBuffer();
    }

    private void RefreshBuffer(){
        DoubleBuffer = pFieldToRender.GetBoard().clone();
    }

    public Pane RenderBoardToPane(GameManager.CurrentPiece player){
        Pane graphicsContext = new Pane();
        int xPos=0, yPos =0, currentSquare =0;
        int TopOffset = 10, LeftOffset = 10, padding = 5;

        for(int px = 0; px < 4; px++){
            for(int py=0; py < 4; py++){

                if(Tetrominos.shapes.get(player.PieceType).charAt(pFieldToRender.Rotate(px,py, player.Rotation)) == 'x'){
                    DoubleBuffer[(player.YPos + py) * pFieldToRender.GetBoardWidth() + (player.XPos + px)] = player.PieceType;
                }
            }
        }

        for (int grid: DoubleBuffer) {
            Rectangle r = new Rectangle(LeftOffset + xPos + padding, TopOffset+ yPos + padding,RenderSize,RenderSize);

            switch (grid){
                case 0:{
                    r.setFill(Color.GRAY);
                    break;
                }
                case 1:{
                    r.setFill(Color.RED);
                    break;
                }

            }

            graphicsContext.getChildren().add(r);
            currentSquare ++;
            xPos += RenderSize + padding;

            if(currentSquare % pFieldToRender.GetBoardWidth() == 0){
                yPos += RenderSize + padding;
                xPos = 0;
            }

        }
        return graphicsContext;
    }

}
