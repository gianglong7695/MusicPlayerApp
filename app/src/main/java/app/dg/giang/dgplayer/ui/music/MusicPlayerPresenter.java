package app.dg.giang.dgplayer.ui.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import app.dg.giang.dgplayer.models.Song;
import app.dg.giang.dgplayer.player.PlayService;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class MusicPlayerPresenter implements MusicPlayerContract.Presenter {

    private Context mContext;
    private MusicPlayerContract.View mView;


    private PlayService mPlayService;
    private boolean mIsServiceBound;



    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mPlayService = ((PlayService.LocalBinder) service).getService();
            mView.onPlaybackServiceBound(mPlayService);
            mView.onSongUpdated(mPlayService.getPlayingSong());
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mPlayService = null;
            mView.onPlaybackServiceUnbound();
        }
    };

    public MusicPlayerPresenter(Context context, MusicPlayerContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void retrieveLastPlayMode() {

    }

    @Override
    public void setSongAsFavorite(Song song, boolean favorite) {

    }

    @Override
    public void bindPlaybackService() {
        mContext.bindService(new Intent(mContext, PlayService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsServiceBound = true;
    }

    @Override
    public void unbindPlaybackService() {
        if (mIsServiceBound) {
            // Detach our existing connection.
            mContext.unbindService(mConnection);
            mIsServiceBound = false;
        }
    }

    @Override
    public void subscribe() {
        bindPlaybackService();

    }

    @Override
    public void unsubscribe() {
        unbindPlaybackService();
        // Release context reference
        mContext = null;
        mView = null;
    }
}
