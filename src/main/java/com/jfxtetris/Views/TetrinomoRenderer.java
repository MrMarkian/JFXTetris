package com.jfxtetris.Views;

import com.jfxtetris.Controllers.ThemeSet;
import com.jfxtetris.Models.Tetrominos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrinomoRenderer {
    Tetrominos pieces = new Tetrominos();


    public Group RenderTetrinomo(int index, int RenderSize,  int padding, ThemeSet mediaTheme){
        Group renderArea = new Group();

       if(index == -1)
            return renderArea;

        int xPos=0, yPos=0, currentSquare=0;

        for (char c : Tetrominos.shapes.get(index).toCharArray()) {
            Rectangle r = new Rectangle(xPos + padding, yPos + padding, RenderSize, RenderSize);

            if(c == 'x'){
                r.setFill(mediaTheme.getFromIndex(index));
            }else
                r.setFill(Color.TRANSPARENT); // Todo: Maybe expand this out a bit.. maybe a image grid?
            renderArea.getChildren().add(r);
            currentSquare ++;
            xPos += RenderSize + padding;

            if(currentSquare % 4 == 0){
                yPos += RenderSize + padding;
                xPos = 0;
            }
        }
        return renderArea;
    }
}
