package app.dg.giang.dgplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.dg.giang.dgplayer.example.PlayerManager;
import app.dg.giang.dgplayer.tutorial.MusicController;
import app.dg.giang.dgplayer.tutorial.MusicService;
import app.dg.giang.dgplayer.tutorial.Song;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicPlayerActivity extends AppCompatActivity/* implements MediaController.MediaPlayerControl*/ {
    // Update seek bar every second
    private static final long UPDATE_PROGRESS_INTERVAL = 1000;

    @BindView(R.id.image_view_album)
    ImageView imageViewAlbum;
    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view_artist)
    TextView textViewArtist;
    @BindView(R.id.text_view_progress)
    TextView textViewProgress;
    @BindView(R.id.text_view_duration)
    TextView textViewDuration;
    @BindView(R.id.seek_bar)
    SeekBar seekBarProgress;

    @BindView(R.id.button_play_mode_toggle)
    ImageView buttonPlayModeToggle;
    @BindView(R.id.button_play_toggle)
    ImageView buttonPlayToggle;
    @BindView(R.id.button_favorite_toggle)
    ImageView buttonFavoriteToggle;

    private PlayerManager playerManager;
    private boolean isPlay;


    private ArrayList<Song> songList;
    //service
    public MusicService musicSrv;
    private Intent playIntent;
    //controller
    private MusicController controller;
    //activity and playback pause flags
    private boolean paused = false, playbackPaused = false;

    //binding
    private boolean musicBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        playerManager = new PlayerManager(this);
        playerManager.startService();

        getSongList();
        buttonPlayToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                PlayerMessage playerMessage = new PlayerMessage();
//                playerMessage.setPath("https://tainhachay.biz/download-music/239579");
//                playerManager.play(playerMessage);


                songPicked(view);

                if(isPlay){
                    updatePlayToggle(false);

                }else{
                    updatePlayToggle(true);

                }


            }
        });


    }

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
        songList = new ArrayList<>();
        songList.add(new Song(0, "1", "1"));
        songList.add(new Song(1, "2", "2"));
        songList.add(new Song(2, "3", "3"));
        songList.add(new Song(3, "4", "4"));
        songList.add(new Song(4, "5", "5"));
    }


    public void updatePlayToggle(boolean isPlay) {
        buttonPlayToggle.setImageResource(isPlay ? R.drawable.ic_pause : R.drawable.ic_play);
        if (isPlay)
            this.isPlay = false;
        this.isPlay = true;

    }

    //user song select
    public void songPicked(View view){
//        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.setSong(0);
        musicSrv.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        //controller.show(0);
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


//    @Override
//    public boolean canPause() {
//        return true;
//    }
//
//    @Override
//    public boolean canSeekBackward() {
//        return true;
//    }
//
//    @Override
//    public boolean canSeekForward() {
//        return true;
//    }
//
//    @Override
//    public int getAudioSessionId() {
//        return 0;
//    }
//
//    @Override
//    public int getBufferPercentage() {
//        return 0;
//    }
//
//    @Override
//    public int getCurrentPosition() {
//        if(musicSrv!=null && musicBound && musicSrv.isPng())
//            return musicSrv.getPosn();
//        else return 0;
//    }
//
//    @Override
//    public int getDuration() {
//        if(musicSrv!=null && musicBound && musicSrv.isPng())
//            return musicSrv.getDur();
//        else return 0;
//    }
//
//    @Override
//    public boolean isPlaying() {
//        if(musicSrv!=null && musicBound)
//            return musicSrv.isPng();
//        return false;
//    }
//
//    @Override
//    public void pause() {
//        playbackPaused=true;
//        musicSrv.pausePlayer();
//    }
//
//    @Override
//    public void seekTo(int pos) {
//        musicSrv.seek(pos);
//    }
//
//    @Override
//    public void start() {
//        musicSrv.go();
//    }

    //set the controller up
    private void setController(){
        controller = new MusicController(this);
        //set previous and next button listeners
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        //set and show
//        controller.setMediaPlayer(this);
//        controller.setAnchorView(findViewById(R.id.song_list));
//        controller.setEnabled(true);
    }

    private void playNext(){
        musicSrv.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
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
//        stopService(playIntent);
//        musicSrv=null;
        super.onDestroy();
    }

}
