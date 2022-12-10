package com.jfxtetris.Controllers;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ThemeSet {
    String ThemeName;
    final Paint IPiece;
    final Paint OPiece;
    final Paint TPiece;
    final Paint JPiece;
    final Paint LPiece;
    final Paint SPiece;
    final Paint ZPiece;
    final Paint Boarder;
    final Paint Padding;
    final Paint DestroyedPiece;
    final Paint EmptySpace;
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

    public Paint getGhostPiece() {
        return GhostPiece;
    }

    public void setGhostPiece(Paint ghostPiece) {
        GhostPiece = ghostPiece;
    }

    public Paint getGarbagePiece() {
        return GarbagePiece;
    }

    public void setGarbagePiece(Paint garbagePiece) {
        GarbagePiece = garbagePiece;
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
