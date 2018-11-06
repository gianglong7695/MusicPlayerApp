package app.dg.giang.dgplayer.ui.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import app.dg.giang.dgplayer.models.Song;
import app.dg.giang.dgplayer.player.PlayMode;
import app.dg.giang.dgplayer.player.PlayService;
import app.dg.giang.dgplayer.ui.base.BasePresenter;
import app.dg.giang.dgplayer.ui.base.BaseView;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public interface MusicPlayerContract {
    interface View extends BaseView<Presenter> {

        void handleError(Throwable error);

        void onPlaybackServiceBound(PlayService service);

        void onPlaybackServiceUnbound();

        void onSongSetAsFavorite(@NonNull Song song);

        void onSongUpdated(@Nullable Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

        void updateFavoriteToggle(boolean favorite);
    }

    interface Presenter extends BasePresenter {

        void retrieveLastPlayMode();

        void setSongAsFavorite(Song song, boolean favorite);

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}
