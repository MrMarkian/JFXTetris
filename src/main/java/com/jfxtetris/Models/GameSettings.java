package com.jfxtetris.Models;

import java.io.Serializable;

public class GameSettings implements Serializable {
    public String playerName;
    public ScoreList scoreList;
    public boolean timedGame;
    public boolean allowSavedGame;
    public boolean renderAboveTopBoarder;
    public boolean showGhostPiece;
    public boolean allowWallKicks;

    public GameModes mode;
    public PieceRandomizer randomizer;
    public PieceRandomizer.GameModeRND randomizerMode;
    public boolean playBackGroundMusic;
    public boolean isPlayBackGroundMusicRandomOrder;
    public int backGroundMusicVolume;
    public boolean playRowVoices;
    public int rowVoicesVolume;
    public boolean playSoundEffects;
    public int soundEffectsVolume;
    public boolean skipSZOonFirstPiece;
    public boolean startWithZZZZ;
    public boolean startWithZSSZ;
    public boolean startWithNoHistory;
    public long FPSdelay;
    public long lOGICdelay;
    public long INPUTdelay;

    public boolean rotationDirection;

    @Override
    public String toString() {
        return "GameSettings{" +
                "playerName='" + playerName + '\'' +
                ", scoreList=" + scoreList +
                ", timedGame=" + timedGame +
                ", allowSavedGame=" + allowSavedGame +
                ", renderAboveTopBoarder=" + renderAboveTopBoarder +
                ", showGhostPiece=" + showGhostPiece +
                ", allowWallKicks=" + allowWallKicks +
                ", mode=" + mode +
                ", randomizer=" + randomizer +
                ", playBackGroundMusic=" + playBackGroundMusic +
                ", isPlayBackGroundMusicRandomOrder=" + isPlayBackGroundMusicRandomOrder +
                ", backGroundMusicVolume=" + backGroundMusicVolume +
                ", playRowVoices=" + playRowVoices +
                ", rowVoicesVolume=" + rowVoicesVolume +
                ", playSoundEffects=" + playSoundEffects +
                ", soundEffectsVolume=" + soundEffectsVolume +
                ", skipSZOonFirstPiece=" + skipSZOonFirstPiece +
                ", startWithZZZZ=" + startWithZZZZ +
                ", startWithZSSZ=" + startWithZSSZ +
                ", FPSdelay=" + FPSdelay +
                ", lOGICdelay=" + lOGICdelay +
                ", INPUTdelay=" + INPUTdelay +
                ", rotationDirection=" + rotationDirection +
                '}';
    }
}
