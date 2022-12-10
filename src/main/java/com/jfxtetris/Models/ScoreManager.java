package com.jfxtetris.Models;

import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    public List<ScoreList> tetrisScoreList = new ArrayList<>();
    int SchemeInUse = 0;

    public ScoreManager(){
            ScoreList nesScheme = new ScoreList();
            nesScheme.Name = "Nes Scheme";
            nesScheme.tetrisScore.put(TetrisType.Single, 40);
            nesScheme.tetrisScore.put(TetrisType.Double, 100);
            nesScheme.tetrisScore.put(TetrisType.Triple, 300);
            nesScheme.tetrisScore.put(TetrisType.Quad, 1200);
            nesScheme.useLevelMultiplyer = true;
            tetrisScoreList.add(nesScheme);

        ScoreList testScore = new ScoreList();
        testScore.Name = "Test Scheme";
        testScore.tetrisScore.put(TetrisType.Single, 10);
        testScore.tetrisScore.put(TetrisType.Double, 30);
        testScore.tetrisScore.put(TetrisType.Triple, 60);
        testScore.tetrisScore.put(TetrisType.Quad, 120);
        testScore.useLevelMultiplyer = true;
        tetrisScoreList.add(testScore);
    }

    public int getScore(GameBoard.CurrentPiece piece, TetrisType tetrisType){
        //Todo: implement level multiplyer
        return tetrisScoreList.get(SchemeInUse).tetrisScore.get(tetrisType) ;
    }
}
