package com.jfxtetris.Views;

import com.jfxtetris.Controllers.GameManager;
import com.jfxtetris.Models.GameBoard;
import com.jfxtetris.Models.Tetrominos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class BoardRenderer {
    int RenderSize = 40;
    int padding = 0;
    int[] DoubleBuffer;
    Tetrominos pieces = new Tetrominos();
    final GameManager gm;
    double ghostPieceFadeValue;

    //Todo: Add method to re-render the boarder and background on theme change

    public BoardRenderer(GameManager gameManager){
        gm = gameManager;
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
        DoubleBuffer = gm.getGameBoard().GetBoard().clone();
    }

    public Pane RenderBoardToPane(GameBoard.CurrentPiece player){

        RefreshBuffer();

        Pane graphicsContext = new Pane();
        if(player == null)
            return graphicsContext;

        graphicsContext.setBackground(Background.fill(gm.getMediaManager().GetCurrentTheme().getPadding()));


        //Render ghostpiece
        if(gm.getGameBoard().settings.showGhostPiece && gm.getGameBoard().fallingPiece != null) {
            for (int a = 0 ; a < gm.getGameBoard().GetBoardHeight(); a++) {
                if (!gm.getGameBoard().DoesPieceFit(player.PieceType, gm.getGameBoard().fallingPiece.Rotation, player.XPos, a+4)) {
                    a = a + 4;
                    if (a > (player.YPos -4) && a < gm.getGameBoard().GetBoardHeight()) {

                        for (int px = 0; px < 4; px++) { //render player overlayed into double buffer
                            for (int py = 0; py < 4; py++) {

                                if (Tetrominos.shapes.get(player.PieceType).charAt(gm.getGameBoard().Rotate(px, py, player.Rotation)) == 'x') {
                                    DoubleBuffer[(a - 1 + py) * gm.getGameBoard().GetBoardWidth() + (player.XPos + px)] = 10;
                                }
                            }
                        }
                        a = gm.getGameBoard().GetBoardHeight();
                    }
                }
                // System.out.println("Finding Y for Ghost:" + a + " GameHeight:" + gm.getGameBoard().GetBoardHeight());
            }



        }

        int xPos=0, yPos =0, currentSquare =0;
        int TopOffset = 0, LeftOffset = 0;

        for(int px = 0; px < 4; px++){ //render player overlayed into double buffer
            for(int py=0; py < 4; py++){

                if(Tetrominos.shapes.get(player.PieceType).charAt(gm.getGameBoard().Rotate(px,py, player.Rotation)) == 'x'){
                    DoubleBuffer[(player.YPos + py) * gm.getGameBoard().GetBoardWidth() + (player.XPos + px)] = player.PieceType;
                }
            }
        }

        for (int grid: DoubleBuffer) {
            Rectangle r = new Rectangle(LeftOffset + xPos + padding, TopOffset+ yPos + padding,RenderSize,RenderSize);

            switch (grid){
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
                    Glow glow = new Glow();
                    glow.setLevel(0.9);
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getDestroyedPiece());
                    DropShadow drop = new DropShadow(ThreadLocalRandom.current().nextInt(1,30), Color.GRAY);
                    r.setEffect(glow);
                    r.setEffect(drop);
                    r.setArcWidth(ThreadLocalRandom.current().nextInt(1,20));
                    r.setArcHeight(ThreadLocalRandom.current().nextInt(1,20));
                    r.setOpacity(ghostPieceFadeValue = ghostPieceFadeValue - 0.0002);
                   if(ghostPieceFadeValue < 0)
                        ghostPieceFadeValue = 1;
                    break;
                }
                case 9:{

                    r.setFill(gm.getMediaManager().GetCurrentTheme().getBoarder());

                    break;
                }
                case 10:{ //Ghost Piece
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getGhostPiece());
                    break;
                }
                default:{
                    r.setFill(gm.getMediaManager().GetCurrentTheme().getEmptySpace());
                    break;
                }

            }

           // r.setArcWidth(ThreadLocalRandom.current().nextInt(1,20));
           // r.setArcHeight(ThreadLocalRandom.current().nextInt(1,20));

            graphicsContext.getChildren().add(r);
            currentSquare ++;
            xPos += RenderSize + padding;

            if(currentSquare % gm.getGameBoard().GetBoardWidth() == 0){
                yPos += RenderSize + padding;
                xPos = 0;
            }

        }
        return graphicsContext;
    }
}
