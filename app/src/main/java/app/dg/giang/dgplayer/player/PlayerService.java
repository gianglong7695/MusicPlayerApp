package app.dg.giang.dgplayer.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class PlayerService extends Service{

    private static int mState = MediaPlayerState.STATE_STOPPED;

    // Media player
    private static MediaPlayer mMediaPlayer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    public static void setmMediaPlayer(MediaPlayer mMediaPlayer) {
        PlayerService.mMediaPlayer = mMediaPlayer;
    }
}
