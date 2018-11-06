package app.dg.giang.dgplayer.player;

import android.support.annotation.Nullable;

import app.dg.giang.dgplayer.models.PlayList;
import app.dg.giang.dgplayer.models.Song;

/**
 * Created by Giang Long on 11/6/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public interface IPlayerCallback {

    void setPlayList(PlayList list);

    boolean play();

    boolean play(PlayList list);

    boolean play(PlayList list, int startIndex);

    boolean play(Song song);

    boolean playLast();

    boolean playNext();

    boolean pause();

    boolean isPlaying();

    int getProgress();

    Song getPlayingSong();

    boolean seekTo(int progress);

    void setPlayMode(PlayMode playMode);

    void registerCallback(ActionCallback actionCallback);

    void unregisterCallback(ActionCallback actionCallback);

    void removeCallbacks();

    void releasePlayer();
}
