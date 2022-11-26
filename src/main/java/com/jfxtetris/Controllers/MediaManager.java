package com.jfxtetris.Controllers;
import javafx.scene.media.AudioClip;

import javax.sound.midi.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MediaManager {
    Sequencer sequencer;
    private int currentBackgroundSong = 0;

    private final List<AudioClip> voiceNumbers = new ArrayList<>();
    private final List<File> backgroundSongs = new ArrayList<>();

    private final List<ThemeSet> themes = new ArrayList<>();
    private int themeInUse =0;

    public MediaManager() {
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

        backgroundSongs.add(new File("src/main/resources/Midi/TetrisA.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/TetrisB.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/TetrisC.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/1.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/2.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/3.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/4.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/5.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/6.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/7.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/8.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/9.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/10.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/11.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/12.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/13.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/14.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/15.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/16.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/17.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/18.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/19.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/20.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/21.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/22.mid"));
        backgroundSongs.add(new File("src/main/resources/Midi/23.mid"));

        themes.add(new ThemeSet());

    }

    public ThemeSet GetCurrentTheme(){
        return themes.get(themeInUse);
    }

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

    public void StartBackgroundMusic(){
       PlayMidi(0);
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
            InputStream is = new BufferedInputStream(new FileInputStream(backgroundSongs.get(tuneIndex)));
            sequencer.setSequence(is);
            sequencer.start();
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

    public void OnClose(){
        if(sequencer.isRunning()) {
            sequencer.stop();
            sequencer.close();
        }
    }

}
