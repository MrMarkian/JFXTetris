package com.jfxtetris.Models;

import java.io.Serializable;
import java.util.Hashtable;

public class ScoreList implements Serializable {
    public String Name;
    public Hashtable<TetrisType, Integer> tetrisScore = new Hashtable<>();
    public boolean useLevelMultiplyer =true;
    public int SoftDropPoints;
    public int HarDropPoints;

    @Override
    public String toString() {
        return Name;
    }
}
