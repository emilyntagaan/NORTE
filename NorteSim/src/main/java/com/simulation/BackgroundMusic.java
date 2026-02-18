package com.simulation;

import java.io.InputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class BackgroundMusic {
    private static boolean playing = false;
    private static Thread musicThread;

    public static synchronized void playMusic(String filePath) {
        if (playing) return; // ✅ Prevent multiple instances

        playing = true;
        musicThread = new Thread(() -> {
            while (playing) {
                try (InputStream inputStream = BackgroundMusic.class.getClassLoader().getResourceAsStream(filePath)) {
                    if (inputStream == null) {
                        System.err.println("Error: " + filePath + " not found.");
                        stopMusic();
                        return;
                    }

                    AdvancedPlayer player = new AdvancedPlayer(inputStream);
                    player.play(); // ✅ Play the track

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // ✅ Loop: The music restarts after finishing
            }
        });

        musicThread.setDaemon(true); // ✅ Allows program to exit cleanly
        musicThread.start();
    }

    public static synchronized void stopMusic() {
        playing = false;
        if (musicThread != null) {
            musicThread.interrupt();
            musicThread = null;
        }
    }

    public static synchronized boolean isPlaying() {
        return playing;
    }
}
