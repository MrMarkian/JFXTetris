package com.jfxtetris.Controllers;
import com.jfxtetris.Models.SoundTypes;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import javax.sound.midi.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MediaManager {
    Sequencer sequencer;
    private int currentBackgroundSong = 0;

    private final List<AudioClip> voiceNumbers = new ArrayList<>();
    private final List<URI> backgroundSongs = new ArrayList<>();

    private final List<ThemeSet> themes = new ArrayList<>();
    private int themeInUse =2;
    private int setsToLoad = 2;

    public MediaManager() throws URISyntaxException {
        try {
            this.sequencer =MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

        voiceNumbers.add(LoadClip("/Voice/1.wav"));
        voiceNumbers.add(LoadClip("/Voice/2.wav"));
        voiceNumbers.add(LoadClip("/Voice/3.wav"));
        voiceNumbers.add(LoadClip("/Voice/4.wav"));
        voiceNumbers.add(LoadClip("/Voice/5.wav"));

        URI a = null;
        a = Objects.requireNonNull(getClass().getResource("/Midi/TetrisA.mid")).toURI();

        backgroundSongs.add(a);

        //backgroundSongs.add(new File("src/main/resources/Midi/TetrisA.mid"));
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/TetrisB.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/TetrisC.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/1.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/2.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/3.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/4.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/5.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/6.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/7.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/8.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/9.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/10.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/11.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/12.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/13.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/14.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/15.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/16.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/17.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/18.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/19.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/20.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/21.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/22.mid")).toURI());
        backgroundSongs.add(Objects.requireNonNull(getClass().getResource("/Midi/23.mid")).toURI());



        themes.add(new ThemeSet());


        themes.add(LoadImageSet(1));
        themes.add(LoadImageSet(2));

        themes.get(2).MoveSound = LoadClip("/Sounds/Move.wav");
        themes.get(2).GameOverSound = LoadClip("/Sounds/game-over-yeah.mp3");
        themes.get(2).SoftDropSound = LoadClip("/Sounds/SoftDrop.wav");
        themes.get(2).SpinSound = LoadClip("/Sounds/Rotate.wav");

        themes.get(0).ThemeName = "Classic";
        themes.get(1).ThemeName = "Rounded";
        themes.get(2).ThemeName = "GameBoy";

    }

    //Todo: Add Move sound
    //Todo: Add Spin sound
    //Todo: Add Line Specific clear sounds
    //Todo: Switch on off sounds / background - Also background music random or sequential

    private ThemeSet LoadImageSet(int count){
        return
                new ThemeSet(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set" + count + "/I.png")).toString())),
                        new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set"+ count + "/O.png")).toString())),
                        new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set" + count + "/T.png")).toString())),
                        new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set" + count + "/J.png")).toString())),
                        new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set" + count + "/L.png")).toString())),
                        new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set" + count + "/S.png")).toString())),
                        new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/Images/set" + count + "/Z.png")).toString())),
                        Color.DARKGREEN,
                        Color.DARKGREEN,
                        Color.FIREBRICK,
                        Color.LIGHTGREEN);
    }

    public void NextTheme(){
        themeInUse++;
        if (themeInUse > setsToLoad)
            themeInUse =0;
    }

    public ThemeSet GetCurrentTheme(){
        return themes.get(themeInUse);
    }

    public List<ThemeSet> GetAllThemes(){return themes ;}

    private AudioClip LoadClip(String path){
        try {
            return new AudioClip(Objects.requireNonNull(getClass().getResource(path)).toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void PlayVoice(int index){
        AudioClip media = voiceNumbers.get(index -1);
        media.play();
    }

    public void PlaySoundClip(SoundTypes type){
        switch (type){
            case MoveSound -> {
                if (themes.get(themeInUse).MoveSound == null)
                    return;
                if(!themes.get(themeInUse).MoveSound.isPlaying())
                    themes.get(themeInUse).MoveSound.play();
            }
            case SpinSound -> {
                if (themes.get(themeInUse).SpinSound == null)
                    return;
                if(!themes.get(themeInUse).SpinSound.isPlaying())
                    themes.get(themeInUse).SpinSound.play();
            }
            case HardDrop -> {
                if (themes.get(themeInUse).HardDropSound == null)
                    return;
                if(!themes.get(themeInUse).HardDropSound.isPlaying())
                    themes.get(themeInUse).HardDropSound.play();
            }
            case SoftDrop -> {
                if (themes.get(themeInUse).SoftDropSound == null)
                    return;
                if(!themes.get(themeInUse).SoftDropSound.isPlaying())
                    themes.get(themeInUse).SoftDropSound.play();
            }
            case GameOver -> {
                if (themes.get(themeInUse).GameOverSound == null)
                    return;
                if(!themes.get(themeInUse).GameOverSound.isPlaying())
                    themes.get(themeInUse).GameOverSound.play();
            }
        }

    }

    public void StartBackgroundMusic(){
       PlayMidi(0);
    }
    public void StopBackgroundMusic(){
        if(sequencer != null)
            if(sequencer.isOpen())
                sequencer.stop();
    }

    public void PlayNext(){
        currentBackgroundSong ++;
        if(currentBackgroundSong > backgroundSongs.size()-1){
            currentBackgroundSong = 0;
        }

        PlayMidi(currentBackgroundSong);

    }

    public void PlayRandom(){
        PlayMidi(ThreadLocalRandom.current().nextInt(0, backgroundSongs.size()-1));
    }


    private void PlayMidi(int tuneIndex){
        if(sequencer == null)
            return;
        if(tuneIndex > backgroundSongs.size()-1)
            return;
        if(sequencer.isRunning()) {
            sequencer.stop();
        }

        try {
            sequencer.open();

            sequencer.setSequence(backgroundSongs.get(tuneIndex).toURL().openStream());

            sequencer.start();
            SetBackgroundVolume(1);
            currentBackgroundSong = tuneIndex;

            sequencer.addMetaEventListener(meta -> {
                if(meta.getType() == 47){
                    PlayNext();
                }
            });

        } catch (MidiUnavailableException | IOException | InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    private void SetBackgroundVolume(double gain){ //Todo: Midi Volume needs more work
        Synthesizer synth;

        try {
            synth = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }


        if(synth !=null){
            for (MidiChannel channel: synth.getChannels()) {
                channel.controlChange(7, (int) (gain));
            }
        }
    }

    public void OnClose(){
        if(sequencer.isRunning()) {
            sequencer.stop();
            sequencer.close();
        }
    }

}