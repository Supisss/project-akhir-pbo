/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUTHFI
 */
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Class untuk memainkan musik di latar belakang.
 */
public class MusicPlayer implements Runnable {
    private String filePath;

    public MusicPlayer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread musicThread = new Thread(new MusicPlayer("path/to/your/musicfile.wav"));
        musicThread.start();
    }
}

