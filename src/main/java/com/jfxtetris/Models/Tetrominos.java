package com.jfxtetris.Models;

import java.util.ArrayList;
import java.util.List;

public class Tetrominos {
    public static final List<String> shapes = new ArrayList<>();

    public enum PieceNames{

    }

    public Tetrominos(){

        shapes.add( "...." + //Zero state is invalid.. should not be used.
                     "...." +
                     "...." +
                     "....");

        shapes.add( "...x" +
                    "...x" +
                    "...x" + //I PIECE
                    "...x");

        shapes.add( "..x." +
                    ".xx." +
                    ".x.." + // Z PIECE
                    "....");

        shapes.add( ".xx." +
                    ".xx." + //O PIECE
                    "...." +
                    "....");

        shapes.add( "..x." +
                    "..x." + // L PIECE
                    "..xx" +
                    "....");

        shapes.add( "..x." +
                    "..x." + // J PIECE
                    ".xx." +
                    "....");

        shapes.add( "..xx" +
                    ".xx." + //  S PEICE
                    "...." +
                    "....");

        shapes.add( ".x.." +
                    ".xx." + // T PIECE
                    ".x.." +
                    "....");
    }

}
