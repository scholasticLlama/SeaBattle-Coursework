package com.seabattle.view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Audio {
    private final String track;
    private FloatControl volumeC = null;
    private  double wt;

    public Audio(String track, double wt) {
        this.track = track;
        this.wt = wt;
    }

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
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.setFramePosition(0);
            clip.start();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setVolume() {
        if (wt < 0) wt = 0;
        if (wt > 1) wt = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max - min) * (float) wt +min);
    }
}
