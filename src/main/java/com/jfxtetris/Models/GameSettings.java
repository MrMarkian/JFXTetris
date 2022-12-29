package com.jfxtetris.Models;

public class GameSettings {
    public String playerName;
    public ScoreList scoreList;
    public boolean timedGame;
    public boolean allowSavedGame;
    public boolean renderAboveTopBoarder;
    public boolean showGhostPiece;
    public boolean allowWallKicks;

    public GameModes mode;
    public PieceRandomizer randomizer;
    public boolean playBackGroundMusic;
    public boolean isPlayBackGroundMusicRandomOrder;
    public int backGroundMusicVolume;
    public boolean playRowVoices;
    public int rowVoicesVolume;
    public boolean playSoundEffects;
    public int soundEffectsVolume;

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
                ", FPSdelay=" + FPSdelay +
                ", lOGICdelay=" + lOGICdelay +
                ", INPUTdelay=" + INPUTdelay +
                ", rotationDirection=" + rotationDirection +
                '}';
    }
}
