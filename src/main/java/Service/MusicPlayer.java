package Service;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    private InputStream is;

    private Player player;
    private String musicFilePath;

    public void play( String musicFilePath ) throws JavaLayerException {

        this.musicFilePath = musicFilePath;

        is = this.getClass().getResourceAsStream( musicFilePath );
        player = new Player( is );

        new Thread(() -> {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
