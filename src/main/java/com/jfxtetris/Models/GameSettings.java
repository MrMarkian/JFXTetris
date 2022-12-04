package com.jfxtetris.Models;

public class GameSettings {
    public String playerName;
    public ScoreManager scoreManager;
    public boolean timedGame;
    public boolean allowSavedGame;
    public boolean renderAboveTopBoarder;
    public boolean showGhostPiece;
    public boolean allowWallKicks;

    public GameModes mode;
    public PieceRandomizer randomizer;
    public boolean playBackGroundMusic;
    public int backGroundMusicVolume;
    public boolean playRowVoices;
    public int rowVoicesVolume;
    public boolean playSoundEffects;
    public int soundEffectsVolume;

    @Override
    public String toString() {
        return "GameSettings{" +
                "playerName='" + playerName + '\'' +
                ", scoreManager=" + scoreManager +
                ", timedGame=" + timedGame +
                ", allowSavedGame=" + allowSavedGame +
                ", renderAboveTopBoarder=" + renderAboveTopBoarder +
                ", showGhostPiece=" + showGhostPiece +
                ", allowWallKicks=" + allowWallKicks +
                ", mode=" + mode +
                ", randomizer=" + randomizer +
                ", playBackGroundMusic=" + playBackGroundMusic +
                ", backGroundMusicVolume=" + backGroundMusicVolume +
                ", playRowVoices=" + playRowVoices +
                ", rowVoicesVolume=" + rowVoicesVolume +
                ", playSoundEffects=" + playSoundEffects +
                ", soundEffectsVolume=" + soundEffectsVolume +
                '}';
    }

}
