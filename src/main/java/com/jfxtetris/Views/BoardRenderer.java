package com.jfxtetris.Views;

import com.jfxtetris.Controllers.GameManager;
import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Models.Tetrominos;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class BoardRenderer {

    GameBoard pFieldToRender;
    int RenderSize = 40;
    int padding = 0;
    int[] DoubleBuffer;
    Tetrominos pieces = new Tetrominos();

    public BoardRenderer(GameBoard board){
        pFieldToRender = board;
        DoubleBuffer = pFieldToRender.GetBoard().clone();
        RefreshBuffer();
    }

    public void SetRenderSize(int size){
        if(size > 0 && size < 125){
            RenderSize = size;
        }
    }

    public void SetPaddingSize(int size){
        if(size >= 0 && size < 125){
            padding = size;
        }
    }

    private void RefreshBuffer(){
        DoubleBuffer = pFieldToRender.GetBoard().clone();
    }

    public Pane RenderBoardToPane(GameManager.CurrentPiece player){
        RefreshBuffer();

        Pane graphicsContext = new Pane();
        graphicsContext.setBackground(Background.fill(Color.BLUEVIOLET));
        int xPos=0, yPos =0, currentSquare =0;
        int TopOffset = 0, LeftOffset = 0;

        for(int px = 0; px < 4; px++){ //render player overlayed into double buffer
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
                    r.setFill(Color.BLUEVIOLET);
                    break;
                }
                case 1:{
                    r.setFill(Color.RED);
                    break;
                }
                case 2:{
                    r.setFill(Color.CYAN);
                }
                case 3:{
                    r.setFill(Color.BLUE);
                    break;
                }
                case 4:{
                    r.setFill(Color.GREEN);
                    break;
                }
                case 5:{
                    r.setFill(Color.PINK);
                    break;
                }
                case 6:{
                    r.setFill(Color.YELLOWGREEN);
                    break;
                }
                case 7:{
                    r.setFill(Color.MAGENTA);
                    break;
                }
                case 8:{
                    r.setFill(Color.ORANGE);
                    break;
                }
                case -1:{
                    Stop[] newstop = {new Stop(0.5, Color.RED),
                    new Stop(0.5, Color.GREEN),
                    new Stop(1, Color.BLUE)};
                    LinearGradient newgradient = new LinearGradient(0, 0,
                            1, 0, true, CycleMethod.NO_CYCLE, newstop);
                    r.setFill(newgradient);
                    break;
                }
                case 9:{
                    r.setFill(Color.BLACK);
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
