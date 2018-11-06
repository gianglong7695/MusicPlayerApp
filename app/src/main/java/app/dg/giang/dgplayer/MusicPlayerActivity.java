package app.dg.giang.dgplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import app.dg.giang.dgplayer.models.Song;
import app.dg.giang.dgplayer.player.IPlayerCallback;
import app.dg.giang.dgplayer.player.PlayMode;
import app.dg.giang.dgplayer.player.PlayService;
import app.dg.giang.dgplayer.ui.music.MusicPlayerContract;
import app.dg.giang.dgplayer.ui.music.MusicPlayerPresenter;
import app.dg.giang.dgplayer.utils.Logs;
import butterknife.BindView;

public class MusicPlayerActivity extends AppCompatActivity implements MusicPlayerContract.View {
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


    private PlayService playService;
    private IPlayerCallback mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        getSupportActionBar().hide();


//        playService = new PlayService();


        new MusicPlayerPresenter(this, null).subscribe();

        Song mSong = new Song();
        mSong.setPath("https://vnno-zn-5-tf-mp3-s1-zmp3.zadn.vn/c9ce5f658c21657f3c30/5337160813560919543?authen=exp=1541516785~acl=/c9ce5f658c21657f3c30/*~hmac=ae49ad39bd4fe69c7e161a581193c048&filename=.mp3");
//
////        listAudio.add(new AudioObject("https://vnno-zn-5-tf-mp3-s1-zmp3.zadn.vn/c9ce5f658c21657f3c30/5337160813560919543?authen=exp=1541516785~acl=/c9ce5f658c21657f3c30/*~hmac=ae49ad39bd4fe69c7e161a581193c048&filename=.mp3", "Càng Níu Giữ Càng Dễ Mất - Mr Siro"));
////        listAudio.add(new AudioObject("https://vnno-vn-6-tf-mp3-s1-zmp3.zadn.vn/8eb46f3db87951270868/6366243486279198955?authen=exp=1541514557~acl=/8eb46f3db87951270868/*~hmac=4190bdb351ad6827852c6948bbfa6a83&filename=.mp3", "Chạm Đáy Nỗi Đau - ERIK"));
////        listAudio.add(new AudioObject("https://vnno-vn-6-tf-mp3-s1-zmp3.zadn.vn/8661cfed19a9f0f7a9b8/2673128015199042442?authen=exp=1541509149~acl=/8661cfed19a9f0f7a9b8/*~hmac=f492f80deb1e0684a00ad88dd6f44c30&filename=.mp3", "Cô Gái M52 - HuyR , Tùng Viu"));
////        listAudio.add(new AudioObject("https://vnno-zn-5-tf-mp3-s1-zmp3.zadn.vn/603a21fff7bb1ee547aa/6745492230489640910?authen=exp=1541515951~acl=/603a21fff7bb1ee547aa/*~hmac=de82ec51f20c39aae47aa42398ec2fd3&filename=.mp3", "Mình Cưới Nhau Đi - Huỳnh James , Pjnboys"));
////        listAudio.add(new AudioObject("https://vnno-vn-5-tf-mp3-s1-zmp3.zadn.vn/5ee8eed13895d1cb8884/4035341797591617350?authen=exp=1541524350~acl=/5ee8eed13895d1cb8884/*~hmac=06c0de8662be86ade4c6f88a7735d86a&filename=.mp3", "Quan Trọng Là Thần Thái - OnlyC , Karik"));
//
//
        mPlayer.play(mSong);

    }


    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void onPlaybackServiceBound(PlayService service) {
        mPlayer = playService;
    }

    @Override
    public void onPlaybackServiceUnbound() {

    }

    @Override
    public void onSongSetAsFavorite(@NonNull Song song) {

    }

    @Override
    public void onSongUpdated(@Nullable Song song) {

    }

    @Override
    public void updatePlayMode(PlayMode playMode) {

    }

    @Override
    public void updatePlayToggle(boolean play) {

    }

    @Override
    public void updateFavoriteToggle(boolean favorite) {

    }

    @Override
    public void setPresenter(MusicPlayerContract.Presenter presenter) {

    }
}
