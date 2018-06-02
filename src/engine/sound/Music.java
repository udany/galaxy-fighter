package engine.sound;

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

    public double getVolume() {
        return mediaPlayer.getVolume();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }
}
