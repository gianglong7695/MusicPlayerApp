package app.dg.giang.dgplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import app.dg.giang.dgplayer.example.PlayerHolder;
import app.dg.giang.dgplayer.example.PlayerManager;
import app.dg.giang.dgplayer.example.PlayerMessage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicPlayerActivity extends AppCompatActivity  {
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

    private PlayerManager manager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        manager = new PlayerManager(this);
        manager.startService();



        buttonPlayToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerMessage playerMessage = new PlayerMessage();
                playerMessage.setPath("https://tainhachay.biz/download-music/239579");
                manager.play(playerMessage);
                
            }
        });

    }

//    @OnClick(R.id.button_play_toggle)
//    public void playButton(){
//        PlayerMessage playerMessage = new PlayerMessage();
//        playerMessage.setPath("https://tainhachay.biz/download-music/239579");
//        manager.play(playerMessage);
//    }

}
