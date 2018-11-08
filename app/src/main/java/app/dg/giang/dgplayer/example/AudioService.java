package app.dg.giang.dgplayer.example;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class AudioService extends Service implements PlayerConst{

    private Messenger messenger;
    private PlayerHolder playerHolder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        messenger = new Messenger(new IncomingHandler());
        playerHolder = new PlayerHolder(getApplicationContext());
    }


    private class IncomingHandler extends Handler {
        public IncomingHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            PlayerMessage data;
            switch (msg.what) {
                case ACTION_CONFIG:
//                    PlayerConfig config = (PlayerConfig) msg.obj;
//                    playerHolder.setConfig(getApplicationContext(), config);
                    break;
                case ACTION_MEDIA_ADD_SOURCE:
//                    data = (PlayerMessage) msg.obj;
//                    if (!TextUtils.isEmpty(data.path)) {
//                        playerHolder.addMediaSources(data.path, data.isClear);
//                    }
                    break;
                case ACTION_MEDIA_SET_VIEW:
//                    data = (PlayerMessage) msg.obj;
//                    playerHolder.setPlayerView(data.view);
                    break;
                case ACTION_MEDIA_SET_SURFACE_VIEW:
//                    data = (PlayerMessage) msg.obj;
//                    playerHolder.setSurfaceView(data.sfView);
                    break;
                case ACTION_MEDIA_SET_CALLBACK:
//                    data = (PlayerMessage) msg.obj;
//                    playerHolder.setCallback(data.callback);
                    break;
                case ACTION_MEDIA_PLAY:
                    data = (PlayerMessage) msg.obj;
//                    if (data.index == -1) {
//                        playerHolder.play();
//                    } else {
//                        playerHolder.play(data.index);
//                    }

                    playerHolder.play(data);

                    break;
                case ACTION_MEDIA_RESUME:
                    playerHolder.resume();
                    break;
                case ACTION_MEDIA_PAUSE:
                    playerHolder.pause();
                    break;
                case ACTION_MEDIA_STOP:
                    playerHolder.stop();
                    break;
                case ACTION_MEDIA_RELEASE:
                    playerHolder.release();
                    break;
                case ACTION_MEDIA_TOGGLE:
                    playerHolder.toggle();
                    break;
                case ACTION_MEDIA_SEEK:
//                    data = (PlayerMessage) msg.obj;
//                    playerHolder.seekTo(data.progress);
                    break;
                case ACTION_MEDIA_CACHE:
//                    data = (PlayerMessage) msg.obj;
//                    String path = playerHolder.getPath(data.index);
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
