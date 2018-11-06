package app.dg.giang.dgplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.dg.giang.auplayer.IPlayer;
import app.dg.giang.auplayer.PlayerHolder;
import app.dg.giang.auplayer.PlayerListener;

public class MainActivity extends AppCompatActivity implements PlayerListener {
    private TextView tvUrl;
    private Button btUrl, btSource, btStop;
    private IPlayer iPlayer;
    public static final int MEDIA_RES_ID = R.raw.thang_dien;

    private List<AudioObject> listAudio;
    private Random random;

    private PlayerHolder playerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUrl = findViewById(R.id.tvUrl);
        btUrl = findViewById(R.id.btUrl);
        btStop = findViewById(R.id.btStop);
        btSource = findViewById(R.id.btSource);

        random = new Random();

        initData();
        playerHolder = new PlayerHolder(this);
        playerHolder.setPlayerListener(this);
        iPlayer = playerHolder;


        btUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer.isPlaying()) {
                    iPlayer.release();
                }
                AudioObject audioObject = listAudio.get(random.nextInt(listAudio.size() - 1));
                iPlayer.loadMedia(audioObject.getUrl());
                iPlayer.play();

                tvUrl.setText(audioObject.getName());
            }
        });


        btSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer.isPlaying()) {
                    iPlayer.release();
                }
                iPlayer.loadMedia(MEDIA_RES_ID);
                iPlayer.play();

                tvUrl.setText("Thằng Điên");
            }
        });


        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer.isPlaying()) {
                    iPlayer.pause();
                } else {
                    iPlayer.play();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    public void initData() {
        listAudio = new ArrayList<>();


        listAudio.add(new AudioObject("https://vnno-zn-5-tf-mp3-s1-zmp3.zadn.vn/c9ce5f658c21657f3c30/5337160813560919543?authen=exp=1541516785~acl=/c9ce5f658c21657f3c30/*~hmac=ae49ad39bd4fe69c7e161a581193c048&filename=.mp3", "Càng Níu Giữ Càng Dễ Mất - Mr Siro"));
        listAudio.add(new AudioObject("https://vnno-vn-6-tf-mp3-s1-zmp3.zadn.vn/8eb46f3db87951270868/6366243486279198955?authen=exp=1541514557~acl=/8eb46f3db87951270868/*~hmac=4190bdb351ad6827852c6948bbfa6a83&filename=.mp3", "Chạm Đáy Nỗi Đau - ERIK"));
        listAudio.add(new AudioObject("https://vnno-vn-6-tf-mp3-s1-zmp3.zadn.vn/8661cfed19a9f0f7a9b8/2673128015199042442?authen=exp=1541509149~acl=/8661cfed19a9f0f7a9b8/*~hmac=f492f80deb1e0684a00ad88dd6f44c30&filename=.mp3", "Cô Gái M52 - HuyR , Tùng Viu"));
        listAudio.add(new AudioObject("https://vnno-zn-5-tf-mp3-s1-zmp3.zadn.vn/603a21fff7bb1ee547aa/6745492230489640910?authen=exp=1541515951~acl=/603a21fff7bb1ee547aa/*~hmac=de82ec51f20c39aae47aa42398ec2fd3&filename=.mp3", "Mình Cưới Nhau Đi - Huỳnh James , Pjnboys"));
        listAudio.add(new AudioObject("https://vnno-vn-5-tf-mp3-s1-zmp3.zadn.vn/5ee8eed13895d1cb8884/4035341797591617350?authen=exp=1541524350~acl=/5ee8eed13895d1cb8884/*~hmac=06c0de8662be86ade4c6f88a7735d86a&filename=.mp3", "Quan Trọng Là Thần Thái - OnlyC , Karik"));

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {
        btStop.setText("Play");

    }

    @Override
    public void play() {
        btStop.setText("Pause");
    }
}
