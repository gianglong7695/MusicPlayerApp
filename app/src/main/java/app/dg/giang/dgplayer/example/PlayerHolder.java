package app.dg.giang.dgplayer.example;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import app.dg.giang.dgplayer.utils.Logs;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class PlayerHolder {
    private IPlayer callback;
    private Handler handler;
    private Context context;
    private MediaPlayer mediaPlayer;
    private PlayerMessage playerMessage;


    public PlayerHolder(Context context) {
        this.context = context;
        handler = new Handler();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Logs.i("onCompletion");
            }
        });
    }


    public void play(PlayerMessage playerMessage) {
        try {
            this.playerMessage = playerMessage;
//            Uri mUri = Uri.parse(playerMessage.getPath());
            Uri mUri = Uri.parse("https://tainhachay.biz/download-music/239579");


            mediaPlayer.setDataSource(context, mUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();


        } catch (Exception e) {
            Logs.e(e);
        }
    }


    public void play() {
        try {
            Uri mUri = Uri.parse(playerMessage.getPath());
            mediaPlayer.setDataSource(context, mUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();

        } catch (Exception e) {
            Logs.e(e);
        }
    }


    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();

    }


    public void resume() {
        if (!mediaPlayer.isPlaying())
            play();

    }


    public void stop() {
        mediaPlayer.stop();
    }


    public void release() {
        mediaPlayer.release();
    }

    public void toggle() {
        if (mediaPlayer.isPlaying())
            pause();
        play();
    }
}
