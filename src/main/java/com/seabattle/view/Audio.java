package com.seabattle.view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * This class plays audio
 * @author Kiril Vashchuk
 * @param track path to the audio file to be played
 */
public record Audio(String track) {

    public void sound() {
        File f = new File(track);
        AudioInputStream tr = null;
        try {
            tr = AudioSystem.getAudioInputStream(f);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(tr);
            clip.setFramePosition(0);
            clip.start();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
