package app.dg.giang.dgplayer.player;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.dg.giang.dgplayer.models.PlayList;
import app.dg.giang.dgplayer.models.Song;
import app.dg.giang.dgplayer.utils.Logs;

/**
 * Created by Giang Long on 11/6/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class Player implements MediaPlayer.OnCompletionListener, IPlayerCallback{

    private static volatile Player sInstance;

    private MediaPlayer mPlayer;

    private PlayList mPlayList;

    // Default size 2: for service and UI
    private List<ActionCallback> mCallbacks = new ArrayList<>(2);

    // Player status
    private boolean isPaused;



    private Player() {
        mPlayer = new MediaPlayer();
        mPlayList = new PlayList();
        mPlayer.setOnCompletionListener(this);
    }

    public static Player getInstance() {
        if (sInstance == null) {
            synchronized (Player.class) {
                if (sInstance == null) {
                    sInstance = new Player();
                }
            }
        }
        return sInstance;
    }



    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void setPlayList(PlayList list) {
        if (list == null) {
            list = new PlayList();
        }
        mPlayList = list;
    }

    @Override
    public boolean play() {
        if (isPaused) {
            mPlayer.start();
            notifyPlayStatusChanged(true);
            return true;
        }
        if (mPlayList.prepare()) {
            Song song = mPlayList.getCurrentSong();
            try {
                mPlayer.reset();
                mPlayer.setDataSource(song.getPath());
                mPlayer.prepare();
                mPlayer.start();
                notifyPlayStatusChanged(true);
            } catch (IOException e) {
                Logs.e("play: " + e);
                notifyPlayStatusChanged(false);
                return false;
            }
            return true;
        }
        return false;
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
        if (song == null) return false;

        isPaused = false;
        mPlayList.getSongs().clear();
        mPlayList.getSongs().add(song);
        return play();
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
        return false;
    }

    @Override
    public boolean isPlaying() {
        return  mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public Song getPlayingSong() {
        return null;
    }

    @Override
    public boolean seekTo(int progress) {
        if (mPlayList.getSongs().isEmpty()) return false;

        Song currentSong = mPlayList.getCurrentSong();
        if (currentSong != null) {
            if (currentSong.getDuration() <= progress) {
                onCompletion(mPlayer);
            } else {
                mPlayer.seekTo(progress);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setPlayMode(PlayMode playMode) {

    }

    @Override
    public void registerCallback(ActionCallback callback) {

    }

    @Override
    public void unregisterCallback(ActionCallback callback) {

    }

    @Override
    public void removeCallbacks() {

    }

    @Override
    public void releasePlayer() {
        mPlayList = null;
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        sInstance = null;
    }


    private void notifyPlayStatusChanged(boolean isPlaying) {
        for (ActionCallback callback : mCallbacks) {
            callback.onPlayStatusChanged(isPlaying);
        }
    }

    private void notifyPlayLast(Song song) {
        for (ActionCallback callback : mCallbacks) {
            callback.onSwitchLast(song);
        }
    }

    private void notifyPlayNext(Song song) {
        for (ActionCallback callback : mCallbacks) {
            callback.onSwitchNext(song);
        }
    }

    private void notifyComplete(Song song) {
        for (ActionCallback callback : mCallbacks) {
            callback.onComplete(song);
        }
    }
}
