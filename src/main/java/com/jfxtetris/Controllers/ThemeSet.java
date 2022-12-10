package com.jfxtetris.Controllers;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ThemeSet {
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

    AudioClip MoveSound;
    AudioClip SpinSound;
    AudioClip HardDropSound;
    AudioClip SoftDropSound;
    AudioClip GameOverSound;


    public Paint getIPiece() {
        return IPiece;
    }

    public Paint getOPiece() {
        return OPiece;
    }

    public Paint getTPiece() {
        return TPiece;
    }

    public Paint getJPiece() {
        return JPiece;
    }

    public Paint getLPiece() {
        return LPiece;
    }

    public Paint getSPiece() {
        return SPiece;
    }

    public Paint getZPiece() {
        return ZPiece;
    }

    public Paint getBoarder() {
        return Boarder;
    }

    public Paint getPadding() {
        return Padding;
    }

    public Paint getDestroyedPiece() {
        return DestroyedPiece;
    }

    public Paint getEmptySpace() {
        return EmptySpace;
    }

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

    public ThemeSet(Paint I, Paint O, Paint T, Paint J, Paint L, Paint S, Paint Z, Paint boarder, Paint padding, Paint destroyed, Paint emptySpace){
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

    }

    public ThemeSet(){
       IPiece = Color.CYAN;
       OPiece = Color.YELLOW;
       TPiece = Color.PURPLE;
       SPiece = Color.GREEN;
       ZPiece = Color.RED;
       JPiece = Color.BLUE;
       LPiece = Color.ORANGE;
       Boarder = Color.WHITE;
       Padding = Color.GRAY;
       DestroyedPiece = Color.WHITESMOKE;
       EmptySpace = Color.BLACK;
    }

    @Override
    public String toString() {
        return "ThemeSet{" +
                "ThemeName='" + ThemeName + '\'' +
                '}';
    }
}