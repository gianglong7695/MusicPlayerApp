package app.dg.giang.dgplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.dg.giang.dgplayer.adapter.PlayListAudioAdapter;
import app.dg.giang.dgplayer.player.PlayMode;
import app.dg.giang.dgplayer.service.BaseService;
import app.dg.giang.dgplayer.service.respone.Audio;
import app.dg.giang.dgplayer.service.respone.Data;
import app.dg.giang.dgplayer.service.respone.Datum;
import app.dg.giang.dgplayer.service.respone.HomeRespone;
import app.dg.giang.dgplayer.tutorial.MusicController;
import app.dg.giang.dgplayer.tutorial.MusicService;
import app.dg.giang.dgplayer.tutorial.OnClickListenerPosition;
import app.dg.giang.dgplayer.tutorial.Song;
import app.dg.giang.dgplayer.utils.Logs;
import app.dg.giang.dgplayer.utils.PreferenceUtil;
import app.dg.giang.dgplayer.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPlayerActivity extends AppCompatActivity implements MusicController, View.OnClickListener {
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
    @BindView(R.id.button_play_last)
    ImageView buttonPlayLast;
    @BindView(R.id.button_play_next)
    ImageView button_play_next;
    @BindView(R.id.button_play_toggle)
    ImageView buttonPlayToggle;
    @BindView(R.id.button_favorite_toggle)
    ImageView buttonFavoriteToggle;
    @BindView(R.id.rv_playlist)
    RecyclerView rv_playlist;

    private boolean isPlay;
    private ArrayList<Song> songList;
    //service
    public MusicService musicService;
    private Intent playIntent;
    //controller
    //activity and playback pause flags
    private boolean paused = false, playbackPaused = false;

    //binding
    private boolean musicBound = false;

    private PlayListAudioAdapter adapter;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();

//        getMusic(); //API
    }

    public void init() {
        rv_playlist.setLayoutManager(Utils.getLinearLayoutManager(this, 1));
        updatePlayMode(PreferenceUtil.getPlayMode(this));

        buttonPlayToggle.setOnClickListener(this);
        buttonPlayLast.setOnClickListener(this);
        button_play_next.setOnClickListener(this);
        buttonPlayModeToggle.setOnClickListener(this);
        buttonFavoriteToggle.setOnClickListener(this);



        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateProgressTextWithProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mProgressCallback);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekTo(getDuration(seekBar.getProgress()));
                if (musicService.isPlaying()) {
                    mHandler.removeCallbacks(mProgressCallback);
                    mHandler.post(mProgressCallback);
                }
            }
        });
    }


    public void onSongUpdated(Song song) {
        if (song != null) {
            buttonPlayToggle.setImageResource(R.drawable.ic_play);
            seekBarProgress.setProgress(0);
            updateProgressTextWithProgress(0);
            seekTo(0);
            mHandler.removeCallbacks(mProgressCallback);
            mHandler.post(mProgressCallback);
            return;
        }

//        // Step 1: Song name and artist
//        textViewName.setText(song.getDisplayName());
//        textViewArtist.setText(song.getArtist());
//        // Step 2: favorite
//        buttonFavoriteToggle.setImageResource(song.isFavorite() ? R.drawable.ic_favorite_yes : R.drawable.ic_favorite_no);
//        // Step 3: Duration
//        textViewDuration.setText(TimeUtils.formatDuration(song.getDuration()));
//        // Step 4: Keep these things updated
//        // - Album rotation
//        // - Progress(textViewProgress & seekBarProgress)
//        Bitmap bitmap = AlbumUtils.parseAlbum(song);
//        if (bitmap == null) {
//            imageViewAlbum.setImageResource(R.drawable.default_record_album);
//        } else {
//            imageViewAlbum.setImageBitmap(AlbumUtils.getCroppedBitmap(bitmap));
//        }
//        imageViewAlbum.pauseRotateAnimation();
//        mHandler.removeCallbacks(mProgressCallback);
//        if (mPlayer.isPlaying()) {
//            imageViewAlbum.startRotateAnimation();
//            mHandler.post(mProgressCallback);
//            buttonPlayToggle.setImageResource(R.drawable.ic_pause);
//        }
    }


    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {

            if (musicService.isPlaying()) {
                int progress = (int) (seekBarProgress.getMax() * ((float) musicService.getCurrentDuration() / (float) getCurrentSongDuration()));
                textViewProgress.setText(Utils.formatDuration(musicService.getCurrentDuration()));

                Logs.e(seekBarProgress.getMax() + " " + progress+"--------------");

                if (progress >= 0 && progress <= seekBarProgress.getMax()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBarProgress.setProgress(progress, true);
                    } else {
                        seekBarProgress.setProgress(progress);
                    }
                    mHandler.postDelayed(this, UPDATE_PROGRESS_INTERVAL);
                }
            }
        }
    };

    private int getCurrentSongDuration() {
        Song currentSong = musicService.getPlayingSong();
        int duration = 0;
        if (currentSong != null) {
            duration = currentSong.getDuration();
        }
        return duration;
    }

    private void updateProgressTextWithProgress(int progress) {
//        int targetDuration = getDuration();
//        textViewProgress.setText(TimeUtils.formatDuration(targetDuration));
    }


    public void initData() {
        adapter = new PlayListAudioAdapter(this, songList);
        rv_playlist.setAdapter(adapter);


//        adapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Song song = (Song) view.getTag();
////                play();
//
//            }
//        });


        adapter.setOnClickListenerPosition(new OnClickListenerPosition() {
            @Override
            public void onClickPosition(int pos) {
                playMusic(pos);
            }
        });
    }

    public void getMusic() {
        Call<HomeRespone> call = BaseService.getService().getMusic();

        call.enqueue(new Callback<HomeRespone>() {
            @Override
            public void onResponse(Call<HomeRespone> call, Response<HomeRespone> response) {
                if (response.isSuccessful()) {
                    List<Datum> listDatum = response.body().getResult().getData();
                    for (int i = 0; i < listDatum.size(); i++) {

                        if (listDatum.get(i).getCardType() == 4) {
                            Gson gson = new Gson();
                            String json = gson.toJson(listDatum.get(i));


                            Data data = gson.fromJson(json, Data.class);
                            setSongList(data);

                            musicService.setList(songList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeRespone> call, Throwable t) {
                Logs.e(t.toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }


    public void setSongList(Data data) {
        songList = new ArrayList<>();

        for (int i = 0; i < data.getPlaylist().size(); i++) {
            Audio audio = data.getPlaylist().get(i);
            songList.add(new Song(i, audio.getName(), audio.getLink()));
        }
    }


    public void updatePlayToggle(boolean isPlay) {
        buttonPlayToggle.setImageResource(isPlay ? R.drawable.ic_pause : R.drawable.ic_play);
        if (isPlay)
            this.isPlay = false;
        this.isPlay = true;

    }

    public void updateFavoriteToggle(boolean favorite) {
        buttonFavoriteToggle.setImageResource(favorite ? R.drawable.ic_favorite_yes : R.drawable.ic_favorite_no);
    }

    public void updatePlayMode(PlayMode playMode) {
        if (playMode == null) {
            playMode = PlayMode.getDefault();
        }
        switch (playMode) {
            case LIST:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_list);
                break;
            case LOOP:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_loop);
                break;
            case SHUFFLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_shuffle);
                break;
            case SINGLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_single);
                break;
        }
    }

    public void playMusic(int index) {
        musicService.setSongIndex(index);
        musicService.playSong();
        isPlay = true;
        onSongUpdated(musicService.getPlayingSong());


    }


    public void playMusic() {
        musicService.playSong();
        isPlay = true;
    }


    public void pauseMusic() {
        musicService.pausePlayer();
        isPlay = false;
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //get service
            musicService = binder.getService();
            //pass list

            musicService.setController(MusicPlayerActivity.this);
            setAudioList();
            musicService.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


    public void setAudioList() {
        songList = new ArrayList<>();
        String[] lstUrlMp3 = {
                "https://dl.dropboxusercontent.com/s/2zhi0tpnaf9pxkz/_7KQFxEyBmk.mp3",
                "https://dl.dropboxusercontent.com/s/cf408ih36uldrp4/_8o71Hlzonk.mp3",
                "https://dl.dropboxusercontent.com/s/kv88cvqn69mh2mt/_26FQfH8994.mp3",
                "https://dl.dropboxusercontent.com/s/4vprcuopxeuo5ab/_aGNa9N2q1c.mp3",
                "https://dl.dropboxusercontent.com/s/f9ywxhzqhf6va04/_BBvHRB5vQE.mp3",
                "https://dl.dropboxusercontent.com/s/2zhi0tpnaf9pxkz/_7KQFxEyBmk.mp3",
                "https://dl.dropboxusercontent.com/s/cf408ih36uldrp4/_8o71Hlzonk.mp3",
                "https://dl.dropboxusercontent.com/s/kv88cvqn69mh2mt/_26FQfH8994.mp3",
                "https://dl.dropboxusercontent.com/s/4vprcuopxeuo5ab/_aGNa9N2q1c.mp3",
                "https://dl.dropboxusercontent.com/s/f9ywxhzqhf6va04/_BBvHRB5vQE.mp3"
        };


        for (int i = 0; i < lstUrlMp3.length; i++) {
            songList.add(new Song(i, "Tên bài hát số " + (i + 1), lstUrlMp3[i]));

        }
//        songList.add(new Song(1, "Girls Like You - Maroon 5, Cardi B", "https://tainhachay.biz/download-music/190513"));
//        songList.add(new Song(2, "Darkside - Alan Walker, Au/Ra, Tomine Harket", "https://tainhachay.biz/download-music/221328"));
//        songList.add(new Song(3, "That Girl - Olly Murs", "https://tainhachay.biz/download-music/198063"));
//        songList.add(new Song(4, "That Girl - Olly Murs", "https://tainhachay.biz/download-music/231594"));
//        songList.add(new Song(5, "Burn Out - Martin Garrix, Justin Mylo, Dewain Whitmore", "https://tainhachay.biz/download-music/231594"));
//        songList.add(new Song(6, "Diamond Heart - Alan Walker, Sophia Somajo", "https://tainhachay.biz/download-music/234239"));

        initData();
    }


    //set the controller up
    private void setController() {
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
        //set and show
//        controller.setMediaPlayer(this);
//        controller.setAnchorView(findViewById(R.id.song_list));
//        controller.setEnabled(true);
    }

    private void playNext() {
        musicService.playNext();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
//        controller.show(0);
    }

    private void playPrev() {
        musicService.playPrev();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
//        controller.show(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused) {
            setController();
            paused = false;
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
//        musicService=null;
        super.onDestroy();
    }

    @Override
    public void onMusicPlay() {
//        musicService.playSong();

//        isPlay = true;
//        updatePlayToggle(!isPlay);


        // Update duration if not available
//        Song song = songList.get(index);
//        if (song.getDuration() != 0) {
//            textViewDuration.setText(Utils.formatDuration(song.getDuration()));
//        }

    }

    @Override
    public void onMusicPause() {
        playbackPaused = true;

//        isPlay = false;
//        updatePlayToggle(!isPlay);
    }

    @Override
    public void onMusicPlay(int index) {
//        isPlay = true;
//        updatePlayToggle(!isPlay);


        // Update duration if not available
        Song song = songList.get(index);
        if (song.getDuration() == 0) {
            song.setDuration(getDuration());
            adapter.notifyItemChanged(index);
            textViewDuration.setText(Utils.formatDuration(song.getDuration()));

        }else{
            textViewDuration.setText(Utils.formatDuration(song.getDuration()));
        }
    }

    @Override
    public int getDuration() {
        if (musicService != null && musicBound && musicService.isPlaying())
            return musicService.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicService != null && musicBound && musicService.isPlaying())
            return musicService.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
//        musicService.seek(pos);
//
//        Logs.e(pos + "");
    }

    @Override
    public boolean isPlaying() {
        if (musicService != null && musicBound)
            return musicService.isPlaying();
        return false;
    }

    @Override
    public void playPress() {
        // Update duration if not available
//        Song song = songList.get(index);
//        if (song.getDuration() == 0) {
//            textViewDuration.setText(Utils.formatDuration(song.getDuration()));
//        }
    }

    @Override
    public void pausePress() {

    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play_toggle:
                if (isPlay) {
                    pauseMusic();
                    updatePlayToggle(false);
                } else {
                    playMusic();
                    updatePlayToggle(true);
                }


                break;

            case R.id.button_play_next:
                playNext();
                break;

            case R.id.button_play_last:
                playPrev();
                break;

            case R.id.button_favorite_toggle:
//                updateFavoriteToggle(true);
                break;
            case R.id.button_play_mode_toggle:

                PlayMode current = PreferenceUtil.getPlayMode(this);
                PlayMode newMode = PlayMode.switchNextMode(current);
                PreferenceUtil.setPlayMode(this, newMode);
//                mPlayer.setPlayMode(newMode);
                updatePlayMode(newMode);

                break;
        }


    }
}
