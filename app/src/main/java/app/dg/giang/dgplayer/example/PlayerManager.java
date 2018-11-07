package app.dg.giang.dgplayer.example;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import app.dg.giang.dgplayer.utils.Logs;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class PlayerManager {
    private Context context;
    private Intent intent;

    private IPlayer callback;
    private Messenger messenger = null;
    private boolean isBound;


    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder s) {
            messenger = new Messenger(s);
            isBound = true;
            if (callback != null) {
//                setCallback(callback);
            }


            Logs.d("onServiceConnected...");
        }

        public void onServiceDisconnected(ComponentName className) {
            messenger = null;
            isBound = false;
            Logs.d("onServiceDisconnected");

        }
    };


    public PlayerManager(Context context) {
        this.context = context;
        intent = new Intent(context, AudioService.class);
    }


    public void startService() {
        context.startService(intent);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void stopSerivce() {
        if (isBound) {
            context.unbindService(connection);
            isBound = false;
        }
        context.stopService(intent);
    }


    public void play() {
        play(-1);
    }

    public void play(int index) {
        if (!isBound) return;
        PlayerMessage data = new PlayerMessage();
        data.setIndex(index);
        Message msg = Message.obtain(null, AudioService.ACTION_MEDIA_PLAY, data);
        send(msg);
    }


    public void play(PlayerMessage playerMessage) {
//        if (!isBound) return;
        Message msg = Message.obtain(null, AudioService.ACTION_MEDIA_PLAY, playerMessage);
        send(msg);
    }

    public void resume() {
        if (!isBound) return;
        PlayerMessage data = new PlayerMessage();
        Message msg = Message.obtain(null, AudioService.ACTION_MEDIA_RESUME, data);
        send(msg);
    }

    public void pause() {
        if (!isBound) return;
        PlayerMessage data = new PlayerMessage();
        Message msg = Message.obtain(null, AudioService.ACTION_MEDIA_PAUSE, data);
        send(msg);
    }


    private void send(Message msg) {
        try {
            messenger.send(msg);
            Logs.i("Test Activity sent message: " + msg);
        } catch (RemoteException e) {
            Logs.e(e);

        }
    }
}
