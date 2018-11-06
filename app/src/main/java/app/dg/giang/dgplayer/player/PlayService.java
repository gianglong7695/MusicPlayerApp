package app.dg.giang.dgplayer.player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import app.dg.giang.dgplayer.models.PlayList;
import app.dg.giang.dgplayer.models.Song;

/**
 * Created by Giang Long on 11/6/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class PlayService extends Service implements IPlayerCallback, ActionCallback {

    private static final String ACTION_PLAY_TOGGLE = "app.dg.giang.dgplayer.ACTION.PLAY_TOGGLE";
    private static final String ACTION_PLAY_LAST = "app.dg.giang.dgplayer.ACTION.PLAY_LAST";
    private static final String ACTION_PLAY_NEXT = "app.dg.giang.dgplayer.ACTION.PLAY_NEXT";
    private static final String ACTION_STOP_SERVICE = "app.dg.giang.dgplayer.ACTION.STOP_SERVICE";


    private static final int NOTIFICATION_ID = 1;
    private Player mPlayer;


    private final Binder mBinder = new LocalBinder();




    public class LocalBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = Player.getInstance();
        mPlayer.registerCallback(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_PLAY_TOGGLE.equals(action)) {
                if (isPlaying()) {
                    pause();
                } else {
                    play();
                }
            } else if (ACTION_PLAY_NEXT.equals(action)) {
                playNext();
            } else if (ACTION_PLAY_LAST.equals(action)) {
                playLast();
            } else if (ACTION_STOP_SERVICE.equals(action)) {
                if (isPlaying()) {
                    pause();
                }
                stopForeground(true);
                unregisterCallback(this);
            }
        }
        return START_STICKY;
    }


    @Override
    public boolean stopService(Intent name) {
        stopForeground(true);
        unregisterCallback(this);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }








    @Override
    public void setPlayList(PlayList list) {

    }

    @Override
    public boolean play() {
        return mPlayer.play();
    }

    @Override
    public boolean play(PlayList list) {
        return false;
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        return false;
    }

    @Override
    public boolean play(Song song) {
        return mPlayer.play(song);
    }

    @Override
    public boolean playLast() {
        return false;
    }

    @Override
    public boolean playNext() {
        return false;
    }

    @Override
    public boolean pause() {
        return mPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();

    }

    @Override
    public int getProgress() {
        return mPlayer.getProgress();
    }

    @Override
    public Song getPlayingSong() {
        return null;
    }

    @Override
    public boolean seekTo(int progress) {
        return mPlayer.seekTo(progress);
    }

    @Override
    public void setPlayMode(PlayMode playMode) {

    }

    @Override
    public void registerCallback(ActionCallback actionCallback) {

    }

    @Override
    public void unregisterCallback(ActionCallback actionCallback) {

    }

    @Override
    public void removeCallbacks() {

    }

    @Override
    public void releasePlayer() {
        mPlayer.releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onSwitchLast(@Nullable Song last) {

    }

    @Override
    public void onSwitchNext(@Nullable Song next) {

    }

    @Override
    public void onComplete(@Nullable Song next) {

    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {

    }
}
