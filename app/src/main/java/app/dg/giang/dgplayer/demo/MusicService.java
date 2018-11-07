package app.dg.giang.dgplayer.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import app.dg.giang.dgplayer.models.Song;


public class MusicService extends Service implements OnPreparedListener, OnCompletionListener, OnErrorListener {

    private static final String TAG = "MusicService";
    private static MediaPlayerState mState = MediaPlayerState.Stopped;
    // PlayListItem playing for this Music service
//    private static PlaylistManager mPlaylistMgr = null;
    // Media player
    private static MediaPlayer mMediaPlayer = null;
    // SongItem current playing
    private Song playItem = null;
    // Handler xu ly cap nhat timer
    private Handler mHandler;
    private int mUpdateInterval = 500; // 1000ms update interval
    // Audio focus helper
    private AudioFocusHelper mAudioFocusHelper;
    private float mDuckVolume = 0.5f;
    // Notification
    private NotificationHelper mNotificationHelper;
    // Error continue
    private int mAttempCount = 0;
    private Runnable mUpdateTimeTask = new Runnable() {

        public void run() {
            sendStatus("");
            mHandler.postDelayed(mUpdateTimeTask, mUpdateInterval);
        }
    };

//    public static PlaylistManager getPlaylistManager() {
//        return mPlaylistMgr;
//    }

    public static MediaPlayerState getState() {
        return mState;
    }

    public static MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mMediaPlayers) {
        mMediaPlayer = mMediaPlayers;
    }

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
//    private ShakeDetector mShakeDetector;

    @Override
    public void onCreate() {
        mState = MediaPlayerState.Stopped;
        mHandler = new Handler();


        Song mSong = new Song();
        mSong.setPath("https://tainhachay.biz/download-music/239579");

        mAudioFocusHelper = new AudioFocusHelper(this);
        mAudioFocusHelper.requestFocus();

        mNotificationHelper = new NotificationHelper(this);
        setSensorShake();
    }

    private void setSensorShake() {
        // ShakeDetector initialization
//        final SharedPreferences myPreferences = getSharedPreferences(CommonValues.MY_PREF, Context.MODE_PRIVATE);
//        float shakeThresholdGravity = myPreferences.getFloat(CommonValues.SHAKE_THRESHOLD_GRAVITY, 2.0f);
//        int shakeSlopTimeMs = myPreferences.getInt(CommonValues.SHAKE_SLOP_TIMEMS, 250);
//
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mAccelerometer = mSensorManager
//                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        mShakeDetector = new ShakeDetector();
//
//        mShakeDetector.setShakeThresholdGravity(shakeThresholdGravity);
//        mShakeDetector.setShakeSlopTimeMs(shakeSlopTimeMs);
//        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
//
//            @Override
//            public void onShake(int count) {
//
//                boolean isTurnShake = myPreferences.getBoolean(CommonValues.ENABLE_SHAKE_DEVICES, false);
//                if (isTurnShake == true) {
//                    switch (count) {
//                        case 1:
//                            MusicController.nextSong(getApplicationContext());
//                            break;
//                        case 2:
//                            MusicController.previousSong(getApplicationContext());
//                            break;
//                        case 3:
//                            MusicController.playSong(getApplicationContext());
//                            break;
//                    }
//                    count = 0;
//                }
//            }
//
//        });
//
//
//        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);


    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        // Service is being killed, so make sure we release our resources
        mState = MediaPlayerState.Stopped;
        relaxResources(true);
        mAudioFocusHelper.abandonFocus();
//        if (mSensorManager != null && mShakeDetector != null) {
//            mSensorManager.unregisterListener(mShakeDetector);
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent i, int flags, int startID) {
        String action = i.getAction();
        Log.i(TAG, "intent:" + action);

        if (action.equals(CommonValues.SUPPLY_PLAYLIST)) {
            retrievePlaylist();
            return START_NOT_STICKY;
        }

        if (action.equals(CommonValues.REQUEST_STATUS)) {
            requestStatus();
        } else {
            // check if playlist is null?
//            if (mPlaylistMgr == null)
//                return START_NOT_STICKY;

            if (action.equals(CommonValues.ACTION_PLAY))
                processPlayRequest();
            else if (action.equals(CommonValues.ACTION_NEXT))
                processNextRequest();
            else if (action.equals(CommonValues.ACTION_PREVIOUS))
                processPreviousRequest();
            else if (action.equals(CommonValues.ACTION_TOGGLE_PLAYBACK))
                processTogglePlaybackRequest();
            else if (action.equals(CommonValues.ACTION_PLAY_SPECIFIC_SONG))
                processPlaySpecificSong();
            else if (action.equals(CommonValues.ACTION_PAUSE))
                processPauseRequest();
            else if (action.equals(CommonValues.ACTION_SEEK))
                processSeek(i);
            else if (action.equals(CommonValues.ACTION_REORDER_PLAYLIST))
                processReorderPlaylist();
            else if (action.equals(CommonValues.ACTION_SETUP_AS_FOREGROUND))
                processSetupAsForeground();
        }

        processPlayRequest();

        return START_NOT_STICKY;

    }

    private void processSetupAsForeground() {
        mNotificationHelper.bringServiceToForeground();
    }

    private void processReorderPlaylist() {
        // try to refresh current song playing index
//        mPlaylistMgr.setCurrentSong(playItem);
    }

    private void requestStatus() {
        // stop foreground of service if application came into foreground
        sendStatus(CommonValues.STATUS_BC_ALL);
    }

    private void retrievePlaylist() {
//        Singleton s = Singleton.getInstance();
//        mPlaylistMgr = s.getPlaylistManager();
    }

    private void processPlaySpecificSong() {
        if (mState == MediaPlayerState.Preparing)
            return;
//        Singleton singleton = Singleton.getInstance();
//        SongItem si = singleton.getCurrentSongItem();
//
//        if (mPlaylistMgr.setCurrentSong(si))
            playSong(playItem);
    }

    private void processTogglePlaybackRequest() {
        if (mState == MediaPlayerState.Paused || mState == MediaPlayerState.Stopped)
            processPlayRequest();
        else
            processPauseRequest();
    }

    private void processPlayRequest() {
        if (mState == MediaPlayerState.Stopped) {
//            playSong(/*mPlaylistMgr.gotoNextSong()*/);
            playSong(playItem);
        } else if (mState == MediaPlayerState.Paused) {
            mState = MediaPlayerState.Playing;
            mNotificationHelper.bringServiceToForeground();
            configAndStartMediaPlayer();
        }
    }

    private void processPauseRequest() {
        if (mState == MediaPlayerState.Playing) {
            mState = MediaPlayerState.Paused;
            mMediaPlayer.pause();

            sendStatus(CommonValues.STATUS_BC_NOW_PLAYING);
            relaxResources(false);
        }
    }

    private void processPreviousRequest() {
//        if (mState == MediaPlayerState.Playing || mState == MediaPlayerState.Paused) {
//            playSong(mPlaylistMgr.gotoPreviousSong());
//        }
    }

    private void processNextRequest() {
//        if (mState == MediaPlayerState.Playing || mState == MediaPlayerState.Paused) {
//            playSong(mPlaylistMgr.gotoNextSong());
//        }
    }

    private void processSeek(Intent i) {
//        if ((mState != MediaPlayerState.Playing) && (mState != MediaPlayerState.Paused))
//            return;
//        Bundle b = i.getExtras();
//        int pos = Util.percentageToPosition(b.getInt(CommonValues.BKEY_PERCENTAGE), mMediaPlayer.getDuration());
//        mMediaPlayer.seekTo(pos);
    }

    private void processStopRequest(boolean force) {
        if (mState == MediaPlayerState.Playing || mState == MediaPlayerState.Paused || force) {
            mState = MediaPlayerState.Stopped;

            // let go of all resources...
            relaxResources(true);
            mAudioFocusHelper.abandonFocus();

            // service is no longer necessary. Will be started again if needed.
            stopSelf();
        }

    }

    public void onPrepared(MediaPlayer mp) {
        Log.i(TAG, "onPrepared");
        // set state to playing, even if lost audio focus
        mState = MediaPlayerState.Playing;

        if (mAudioFocusHelper.getLastFocusChange() != AudioManager.AUDIOFOCUS_GAIN
                && mAudioFocusHelper.getLastFocusChange() != AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            return;
        if (mAudioFocusHelper.getLastFocusChange() != AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            mMediaPlayer.setVolume(mDuckVolume, mDuckVolume);
        configAndStartMediaPlayer();
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "Error: what=" + String.valueOf(what) + ", extra=" + String.valueOf(extra));

        mState = MediaPlayerState.Stopped;
        relaxResources(true);

        if (mAttempCount < 2) {
            mAttempCount += 1;
            Toast.makeText(getApplicationContext(), "Can't playing music! Trying next song. ", Toast.LENGTH_SHORT).show();
            processPlayRequest(); // attempt to play next song
        } else { // reset and do nothing
            Toast.makeText(getApplicationContext(), "Error! Stop. ", Toast.LENGTH_SHORT).show();
            mAttempCount = 0;
        }

        return true;
    }

    public void onCompletion(MediaPlayer mp) {
//        playSong(mPlaylistMgr.gotoNextSong());
    }

    private void createMediaPlayerIfNeeded() {
        Log.i(TAG, "Create mediaplayer now is:" + String.valueOf(mMediaPlayer));
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            // make mediaplayer wakelock
            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
        } else
            mMediaPlayer.reset();
    }

    /**
     * Releases resources used by the service for playback. This includes the
     * "foreground service" status and notification, the wake locks and possibly
     * the MediaPlayer.
     *
     * @param releaseMediaPlayer Indicates whether the Media Player should also be released or
     *                           not
     */
    void relaxResources(boolean releaseMediaPlayer) {

        // stop being a foreground service
        stopForeground(true);

        // stop update playing time to activity
        stopUpdatePlaytime();

        // stop and release the Media Player, if it's available
        if (releaseMediaPlayer && mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

    }

    private void playSong(Song si) {
        mState = MediaPlayerState.Stopped;
        relaxResources(false);

        mAudioFocusHelper.requestFocus();

        try {
            playItem = si;
            if (playItem == null) {
                Toast.makeText(
                        this,
                        "Can't play music. Please check insert your SDCard or check yout list song!."
                        , Toast.LENGTH_SHORT).show();
                processStopRequest(true); // stop everything!
                return;
            }

            createMediaPlayerIfNeeded();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(playItem.getPath()));
            mState = MediaPlayerState.Preparing;
            mMediaPlayer.prepareAsync();
        } catch (Exception ex) {
            Log.e("MusicService", "IOException playing next song: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void configAndStartMediaPlayer() {
        if (mMediaPlayer.isPlaying() == false) {
            mState = MediaPlayerState.Playing;
            mMediaPlayer.start();
            mNotificationHelper.bringServiceToForeground();
            startUpdatePlaytime();
            sendStatus(CommonValues.STATUS_BC_NOW_PLAYING);
        }
    }

    /**
     * Send broadcast media playing status Any activity can receive this
     * information and display
     *
     * @param action start with STATUS_
     */
    private void sendStatus(String action) {
        Intent i = new Intent();
        i.setAction(action);

        Bundle b = new Bundle();

//        Singleton singleton = Singleton.getInstance();

        // data send tuy theo action
        if (action.equals(CommonValues.STATUS_BC_NOW_PLAYING) || action.equals(CommonValues.STATUS_BC_ALL)) {
//            singleton.setCurrentSongItem(playItem);
        }

        if (action.equals(CommonValues.STATUS_BC_PLAYTIME) || action.equals(CommonValues.STATUS_BC_ALL)) {
            int pos = 0, dura = 0;
            // Check mediaplayer and state before try to call any method
            if (mMediaPlayer != null && mState != MediaPlayerState.Preparing) {
                pos = mMediaPlayer.getCurrentPosition();
                dura = mMediaPlayer.getDuration();
            }
            b.putInt(CommonValues.BKEY_CURSONG_POSITION, pos);
            b.putInt(CommonValues.BKEY_CURSONG_DURATION, dura);
        }

//        if (action.equals(CommonValues.STATUS_BC_PLAYMODE) || action.equals(CommonValues.STATUS_BC_ALL)) {
//            if (mPlaylistMgr != null) {
//                b.putSerializable(CommonValues.BKEY_REPEATMODE, mPlaylistMgr.getRepeatMode());
//                b.putBoolean(CommonValues.BKEY_SHUFFLE, mPlaylistMgr.isShuffle());
//            }
//        }
//
//        if (action.equals(CommonValues.STATUS_BC_NOWPLAYING_PLAYLIST) || action.equals(CommonValues.STATUS_BC_ALL)) {
//            singleton.setPlaylistManager(mPlaylistMgr);
//        }

        // state
        b.putSerializable(CommonValues.BKEY_STATE, mState);

        // Put into intent and send broadcast
        i.putExtras(b);
        sendBroadcast(i);
    }

    private void startUpdatePlaytime() {
        mHandler.postDelayed(mUpdateTimeTask, mUpdateInterval);
    }

    private void stopUpdatePlaytime() {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    // Media state
    // Enum state activity of Service
    public enum MediaPlayerState {
        Stopped, Preparing, Playing, Paused
    }

    /**
     * Class AudioFocusHelper to help capsulating OnAudioFocusChange
     *
     * @author Phuongle
     */
    private class AudioFocusHelper implements OnAudioFocusChangeListener {
        // context
        Context mContext;

        // Audio focus management
        AudioManager mAudioManager;

        //
        int lastFocusChange = AudioManager.AUDIOFOCUS_GAIN;

        public AudioFocusHelper(Context context) {
            mContext = context;
            // Audio manager
            mAudioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        }

        public boolean requestFocus() {
            return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager.requestAudioFocus(this,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }

        public boolean abandonFocus() {
            return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager.abandonAudioFocus(this);
        }

        public int getLastFocusChange() {
            return lastFocusChange;
        }

        public void onAudioFocusChange(int focusChange) {
            // save this
            lastFocusChange = focusChange;
            // Nothing to do if mediaplayer is nothing
            if (mMediaPlayer == null)
                return;
            // If preparing then return;
            if (mState == MediaPlayerState.Preparing)
                return;

            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    // resume playback
                    Log.i(TAG, "Audio focus gain");
                    if (mState == MediaPlayerState.Playing)
                        configAndStartMediaPlayer();
                    mMediaPlayer.setVolume(1.0f, 1.0f);
                    break;

                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.i(TAG, "Audio focus loss");
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    // Lost focus for a short time, but we have to stop
                    // playback. We don't release the media player because
                    // playback
                    // is likely to resume
                    // mState will be keep, if we gain focus again, it will be
                    // run again
                    Log.i(TAG, "Audio focus loss transient");
                    if (mMediaPlayer.isPlaying()) {
                        processPauseRequest();
                        // maintain state playing
                        mState = MediaPlayerState.Playing;
                    }
                    break;

                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // Lost focus for a short time, but it's ok to keep playing
                    // at an attenuated level
                    Log.i(TAG, "Audio focus loss transient can duck");
                    if (mMediaPlayer.isPlaying())
                        mMediaPlayer.setVolume(mDuckVolume, mDuckVolume);
                    break;
            }

        }
    }

    /**
     * This class to help capsulating the notification
     *
     * @author Phuongle
     */
    private class NotificationHelper {
        private final int NOTIFICATION_ID = 1;
        Notification mNotification = null;
        private Context mContext;
        private NotificationManager mManager;

        public NotificationHelper(Context c) {
            mContext = c;
            mManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        /**
         * Updates the notification.
         */
        @SuppressWarnings("unused")
        private void updateNotification(Song mSongItem) {
            buildNotification(mContext, mSongItem);
            mManager.notify(NOTIFICATION_ID, mNotification);
        }

        // setup as foreground
        private void setUpAsForeground(Song mSongItem) {
            buildNotification(mContext, mSongItem);
            mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
            startForeground(NOTIFICATION_ID, mNotification);
        }

        private void buildNotification(Context context, Song mSongItem) {
//            mNotification = CustomNotification.showNotification(context, mSongItem);
        }

        /**
         * Bring service to foreground with notification If application is still
         * in foreground then do nothing If application isn't in foreground, and
         * music stop, then kill service Else: application isn't in foreground,
         * music playing, then bring service to foreground
         */
        public void bringServiceToForeground() {
            // if no playlist, no playing song
//            if (mPlaylistMgr == null)
//                return;
//            SongItem si = mPlaylistMgr.getCurrentSong();
//            if (si != null)
//                setUpAsForeground(si);
        }
    }
}
