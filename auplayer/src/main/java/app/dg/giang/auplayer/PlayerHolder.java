package app.dg.giang.auplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class PlayerHolder implements IPlayer {
    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;


    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private PlaybackInfoListener mPlaybackInfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;
    private String url;
    private int mResourceId;
    private PlayerListener playerListener;


    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopUpdatingCallbackWithPosition(true);
                    Logs.i("MediaPlayer playback completed");
                    if (mPlaybackInfoListener != null) {
                        mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                        mPlaybackInfoListener.onPlaybackCompleted();
                    }
                }
            });
            Logs.i("mMediaPlayer = new MediaPlayer()");
        }
    }

    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        mPlaybackInfoListener = listener;
    }

    public PlayerHolder(Context context) {
        mContext = context.getApplicationContext();
    }


    public void setPlayerListener(PlayerListener playerListener) {
        this.playerListener = playerListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void loadMedia(int resourceId) {
        mResourceId = resourceId;

        initializeMediaPlayer();

        AssetFileDescriptor assetFileDescriptor =
                mContext.getResources().openRawResourceFd(mResourceId);


        try {
            Logs.i("load() {1. setDataSource}");
            mMediaPlayer.setDataSource(assetFileDescriptor);

        } catch (Exception e) {
            Logs.i(e.toString());
        }

        try {
            Logs.i("load() {2. prepare}");
            mMediaPlayer.prepare();
        } catch (Exception e) {
            Logs.i(e.toString());
        }

        initializeProgressCallback();
        Logs.i("initializeProgressCallback()");

        playerListener.start();


    }

    @Override
    public void loadMedia(String url) {
        this.url = url;
        initializeMediaPlayer();


        Uri mUri = Uri.parse(url);
        try {
            Logs.i("load() {1. setDataSource}");
            mMediaPlayer.setDataSource(mContext, mUri);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } catch (Exception e) {
            Logs.i(e.toString());
        }

        try {
            Logs.i("load() {2. prepare}");
            mMediaPlayer.prepare();
        } catch (Exception e) {
            Logs.i(e.toString());
        }

        initializeProgressCallback();
        Logs.i("initializeProgressCallback()");

        playerListener.start();
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            Logs.i("release() and mMediaPlayer = null");
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            startUpdatingCallbackWithPosition();
        }


        playerListener.play();
    }

    @Override
    public void reset() {
        if (mMediaPlayer != null) {
            Logs.i("playbackReset()");
            mMediaPlayer.reset();
//            loadMedia(mResourceId);

            loadMedia(url);
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }
            Logs.i("playbackPause()");
        }


        playerListener.pause();
    }

    @Override
    public void initializeProgressCallback() {
        final int duration = mMediaPlayer.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
            Logs.i(String.format("firing setPlaybackDuration(%d sec)",
                    TimeUnit.MILLISECONDS.toSeconds(duration)));
            Logs.i("firing setPlaybackPosition(0)");
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            Logs.i(String.format("seekTo() %d ms", position));
            mMediaPlayer.seekTo(position);
        }
    }


    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    // Reports media playback position to mPlaybackProgressCallback.
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(0);
            }
        }
    }


    private void updateProgressCallbackTask() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            int currentPosition = mMediaPlayer.getCurrentPosition();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }
}
