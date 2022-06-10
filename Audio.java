import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Audio {
    private File[] files;
    private Clip[] clips;

    public Audio() {
        files = new File[] {
                new File("sound/box_sound.wav"),
                new File("sound/next_level.wav")
                };
        clips = new Clip[2];
    }

    public void playSound(int sound) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(files[sound]);
            clips[sound] = AudioSystem.getClip();
            clips[sound].open(audioStream);
            clips[sound].start();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
