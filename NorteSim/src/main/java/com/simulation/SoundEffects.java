package com.simulation;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffects {
    public static void playSound(String fileName) {
        new Thread(() -> {
            try {
                // ✅ Load sound from resources/sfx/
                InputStream audioSrc = SoundEffects.class.getClassLoader().getResourceAsStream("sfx/" + fileName);
                if (audioSrc == null) {
                    System.err.println("Sound file not found: " + fileName);
                    return;
                }
                
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

