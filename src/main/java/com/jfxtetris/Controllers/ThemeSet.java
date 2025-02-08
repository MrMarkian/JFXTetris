package com.jfxtetris.Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ThemeSet implements Serializable {
    String ThemeName;
    Paint IPiece;
    Paint OPiece;
    Paint TPiece;
    Paint JPiece;
    Paint LPiece;
    Paint SPiece;
    Paint ZPiece;
    Paint Boarder;
    Paint Padding;
    Paint DestroyedPiece;
    Paint EmptySpace;
    Paint GhostPiece;
    Paint GarbagePiece;

    public Paint getIPiece() {return IPiece;}
     public void setIPiece(Paint image) {IPiece = image; }

    public Paint getOPiece() {return OPiece;    }
    public void setOPiece(Paint image) {OPiece = image; }

    public Paint getTPiece() {
        return TPiece;
    }
    public void setTPiece(Paint image) {TPiece = image; }

    public Paint getJPiece() {
        return JPiece;
    }
    public void setJPiece(Paint image) {JPiece = image; }

    public Paint getLPiece() {
        return LPiece;
    }
    public void setLPiece(Paint image) {LPiece = image; }

    public Paint getSPiece() {
        return SPiece;
    }
    public void setSPiece(Paint image) {SPiece = image; }

    public Paint getZPiece() {return ZPiece;}
    public void setZPiece(Paint image) {ZPiece = image; }

    public Paint getBoarder() {return Boarder;}
    public void setBoarder(Paint image) {Boarder = image; }

    public Paint getPadding() {
        return Padding;
    }
    public void setPadding(Paint image) {Padding = image; }

    public Paint getDestroyedPiece() {
        return DestroyedPiece;
    }
    public void setDestroyedPiece(Paint image) {DestroyedPiece = image; }

    public Paint getEmptySpace() {return EmptySpace;}
    public void setEmptySpace(Paint image) {EmptySpace = image; }

    public Paint getGhostPiece() {return GhostPiece;}

    public String getThemeName() {return ThemeName;}
    public void setThemeName(String themeName) {ThemeName = themeName;}


    public void setGhostPiece(Paint ghostPiece) {GhostPiece = ghostPiece;}

    public Paint getGarbagePiece() {return GarbagePiece;}

    public void setGarbagePiece(Paint garbagePiece) {GarbagePiece = garbagePiece;}

    public Paint getFromIndex(int index){
        return switch (index) {
            case 1 -> IPiece;
            case 2 -> ZPiece;
            case 3 -> OPiece;
            case 4 -> LPiece;
            case 5 -> JPiece;
            case 6 -> SPiece;
            case 7 -> TPiece;
            default -> EmptySpace;
        };
    }

    public ThemeSet(){}

    public ThemeSet(Paint I, Paint O, Paint T, Paint J, Paint L, Paint S, Paint Z, Paint boarder, Paint padding, Paint destroyed, Paint emptySpace, Paint ghost){
        IPiece = I;
        OPiece = O;
        TPiece = T;
        JPiece = J;
        LPiece = L;
        SPiece = S;
        ZPiece = Z;
        Boarder = boarder;
        Padding = padding;
        DestroyedPiece = destroyed;
        EmptySpace = emptySpace;
        this.GhostPiece = ghost;
    }

    @Override
    public String toString() {
        return ThemeName;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
          //  ImageIO.write(IPiece., "png", out); // png is lossless
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
           ImageIO.read(in);

    }

    public BufferedImage ImageToBufferedImage(Image input){

        return SwingFXUtils.fromFXImage(input, null);
    }
}
