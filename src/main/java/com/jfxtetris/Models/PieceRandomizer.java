package com.jfxtetris.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PieceRandomizer implements Serializable {
    GameModeRND modeRND;
    int bagSize = 7;
    List<Integer> piecebag = new ArrayList<>();


    public enum GameModeRND{
        NES,
        TGM
    }

    public PieceRandomizer(GameModeRND mode){
        modeRND = mode;
        GenerateNewBag();
    }

    public int GetBagSize(){
        if(modeRND == GameModeRND.NES)
            return 1;
        return bagSize;
    }

    private void GenerateNewBag() {
        boolean isFound = false;
        for (int piece = 0; piece < bagSize; piece++) {
            int tmp = ThreadLocalRandom.current().nextInt(1, 7 + 1);

            for (int attempt = 0; attempt < 6; attempt++) {

                if (!piecebag.contains(tmp)) {
                    piecebag.add(tmp);
                    attempt = 7;
                    isFound = true;
                }else{
                    tmp = ThreadLocalRandom.current().nextInt(1, 7 + 1);
                    isFound = false;
                    System.out.println("Duplicate Piece found -- " + tmp + " in Index:" + piecebag.indexOf(tmp));
                }

            }
            if(!isFound) {
                System.out.println("Out of options -- " + tmp);
                piecebag.add(tmp);
            }
        }
    }

    public int GetNextPiece(){
        int poppedPiece = piecebag.get(0);
        piecebag.remove(0);
        System.out.println("Piece Removed From bag.." + piecebag.size() + " Remaining");
        if(piecebag.isEmpty())
            GenerateNewBag();
        return poppedPiece;
    }

    public int PeekAt(int index){
        if(index > piecebag.size()-1)
            return -1;
        return piecebag.get(index);
    }
}
