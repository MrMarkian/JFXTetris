package com.jfxtetris.Models;

import java.util.ArrayList;
import java.util.List;

public class Tetrominos {
    public static final List<String> shapes = new ArrayList<>();

    public Tetrominos(){

        shapes.add( "...." + //Zero state is invalid.. should not be used.
                     "...." +
                     "...." +
                     "....");

        shapes.add( "...x" +
                    "...x" +
                    "...x" +
                    "...x");

        shapes.add( "..x." +
                    ".xx." +
                    ".x.." +
                    "....");

        shapes.add( ".xx." +
                    ".xx." +
                    "...." +
                    "....");

        shapes.add( "..x." +
                    "..x." +
                    "..xx" +
                    "....");

        shapes.add( "..x." +
                    "..x." +
                    ".xx." +
                    "....");

        shapes.add( "..xx" +
                    ".xx." +
                    "...." +
                    "....");

        shapes.add( ".xx." +
                    "..xx" +
                    "...." +
                    "....");

        shapes.add( ".x.." +
                    ".xx." +
                    ".x.." +
                    "....");
    }

}
