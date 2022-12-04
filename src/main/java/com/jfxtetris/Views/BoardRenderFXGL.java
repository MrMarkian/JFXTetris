package com.jfxtetris.Views;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.jfxtetris.Controllers.GameManager;
import com.jfxtetris.Models.GameBoard;
import javafx.scene.shape.Rectangle;

public class BoardRenderFXGL extends GameApplication {

    GameBoard gBoard;
    GameManager gm;



    public BoardRenderFXGL(GameBoard gBoard, GameManager gameManager) {
        this.gBoard = gBoard;
        this.gm = gameManager;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Tetris - By Markian Rutkowskyj");
        gameSettings.setHeight(600);
        gameSettings.setWidth(800);

    }


    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);


        int xPos=0, yPos =0, currentSquare =0;
        int TopOffset = 0, LeftOffset = 0;
        int padding = 0; int RenderSize = 20;

        for (int square : gBoard.GetBoard()) {
            Rectangle r = new Rectangle(LeftOffset + xPos + padding, TopOffset+ yPos + padding,RenderSize,RenderSize);

            switch (square){
                case 0:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getEmptySpace());
                    break;
                }
                case 1:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getIPiece());
                    break;
                }
                case 2:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getZPiece());
                    break;
                }
                case 3:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getOPiece());
                    break;
                }
                case 4:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getLPiece());
                    break;
                }
                case 5:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getJPiece());
                    break;
                }
                case 6:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getSPiece());
                    break;
                }
                case 7:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getTPiece());
                    break;
                }
                case -1:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getDestroyedPiece());
                    break;
                }
                case 9:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getBoarder());
                    break;
                }
                default:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getEmptySpace());
                    break;
                }

            }
        }


    }
}
