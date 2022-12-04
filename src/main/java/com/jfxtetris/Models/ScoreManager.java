package com.jfxtetris.Models;

import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    List<ScoreList> tetrisScoreList = new ArrayList<>();
    int SchemeInUse = 0;


    public ScoreManager(){
            ScoreList nesScheme = new ScoreList();
            nesScheme.Name = "Nes Scheme";
            nesScheme.tetrisScore.put(TetrisType.Single, 100);
            nesScheme.tetrisScore.put(TetrisType.Double, 200);
            tetrisScoreList.add(nesScheme);
    }

    public int getScore(GameBoard.CurrentPiece piece, TetrisType tetrisType){
        //Todo: implement level multiplyer
        return tetrisScoreList.get(SchemeInUse).tetrisScore.get(tetrisType) ;
    }
}
