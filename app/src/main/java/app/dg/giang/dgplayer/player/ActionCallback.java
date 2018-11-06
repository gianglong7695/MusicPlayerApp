package app.dg.giang.dgplayer.player;

import android.support.annotation.Nullable;

import app.dg.giang.dgplayer.models.Song;

/**
 * Created by Giang Long on 11/6/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public interface ActionCallback {
    void onSwitchLast(@Nullable Song last);

    void onSwitchNext(@Nullable Song next);

    void onComplete(@Nullable Song next);

    void onPlayStatusChanged(boolean isPlaying);
}
