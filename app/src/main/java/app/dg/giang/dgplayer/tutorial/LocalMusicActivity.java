package app.dg.giang.dgplayer.tutorial;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import app.dg.giang.dgplayer.R;

public class LocalMusicActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    //song list variables
    private ArrayList<Song> songList;
    private ListView songView;

    //service
    private MusicService musicService;
    private Intent playIntent;
    //binding
    private boolean musicBound = false;

    //controller
    private MusicController controller;

    //activity and playback pause flags
    private boolean paused=false, playbackPaused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);

        //retrieve list view
        songView = (ListView) findViewById(R.id.song_list);
        //instantiate list
        songList = new ArrayList<Song>();
        //get songs from device

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//
//                // Should we show an explanation?
//                if (shouldShowRequestPermissionRationale(
//                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    // Explain to the user why we need to read the contacts
//                }
//
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        1);
//
//                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
//                // app-defined int constant that should be quite unique
//
//                return;
//            }
//        }

        getSongList();

        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        //create and set adapter
        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);
    }


    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicService = binder.getService();
            //pass list
            musicService.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


    //start and bind the service when the activity starts
    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }


    //method to retrieve song info from device
    public void getSongList(){
        songList.add(new Song(0, "1", "1"));
        songList.add(new Song(1, "2", "2"));
        songList.add(new Song(2, "3", "3"));
        songList.add(new Song(3, "4", "4"));
        songList.add(new Song(4, "5", "5"));
    }



    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getSongList();
                //sort alphabetically by title
            } else {
                // User refused to grant permission.
            }
        }
    }


    //user song select
    public void songPicked(View view){
        musicService.setSongIndex(Integer.parseInt(view.getTag().toString()));
        musicService.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        //controller.show(0);
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if(musicService!=null && musicBound && musicService.isPlaying())
            return musicService.getPosn();
        else return 0;
    }

    @Override
    public int getDuration() {
        if(musicService!=null && musicBound && musicService.isPlaying())
            return musicService.getDur();
        else return 0;
    }

    @Override
    public boolean isPlaying() {
        if(musicService!=null && musicBound)
            return musicService.isPlaying();
        return false;
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicService.pausePlayer();
    }

    @Override
    public void seekTo(int pos) {
//        musicService.seek(pos);
    }

    @Override
    public void start() {
        musicService.go();
    }

    //set the controller up
    private void setController(){
//        controller = new MusicController(this);
//        //set previous and next button listeners
//        controller.setPrevNextListeners(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playNext();
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playPrev();
//            }
//        });
//        //set and show
//        controller.setMediaPlayer(this);
//        controller.setAnchorView(findViewById(R.id.song_list));
//        controller.setEnabled(true);
    }

    private void playNext(){
        musicService.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
//        controller.show(0);
    }

    private void playPrev(){
        musicService.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
//        controller.show(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }

    @Override
    protected void onStop() {
//        controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicService=null;
        super.onDestroy();
    }
}
