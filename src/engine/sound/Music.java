package engine.sound;

import javax.sound.sampled.FloatControl;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Music {
    private MediaPlayer mediaPlayer;
    private Media media;

    private static boolean started = false;
    private static void startup(){
        if (started) return;

        com.sun.javafx.application.PlatformImpl.startup(()->{});
        started = true;
    }


    public Music(String file){
        startup();

        URL url = getClass().getResource(file);

        try{
            media = new Media(url.toString());
            mediaPlayer = new MediaPlayer(media);
        }catch (Exception e){
            System.out.println("Failed loading sound "+url);
        }
    }

    public void start(){
        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.seek(new Duration(0));
            mediaPlayer.play();
        }
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public float getVolume() {
//        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        return (float) Math.pow(10f, gainControl.getValue() / 20f);
        return 0;
    }

    public void setVolume(double volume) {
//        if (volume < 0f || volume > 1f)
//            throw new IllegalArgumentException("Volume not valid: " + volume);
//        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
