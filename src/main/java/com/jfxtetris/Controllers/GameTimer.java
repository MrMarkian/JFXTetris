package com.jfxtetris.Controllers;

import javafx.beans.value.ObservableValue;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Duration gameTimer = Duration.ZERO;
    private final Timer timer = new Timer();

    public ObservableValue<String> timerText;

    public GameTimer(){

    }

    public String GetGameTime(){
        return formatTime(gameTimer);
    }

    public void StartTimer(){
        timer.schedule(new timerRunner(), 1000, 1000);
    }

    private class timerRunner extends TimerTask {

        @Override
        public void run() {
            IncrementTime(1);
        }
    }

    private void IncrementTime(long timeIncrease) {
        gameTimer = gameTimer.plusSeconds(timeIncrease);
    }

    public static String formatTime(Duration d) {

        long seconds = d.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
