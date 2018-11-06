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


        listAudio.add(new AudioObject("https://mp3.zing.vn/xhr/media/download-source?type=audio&code=kmxHyknsJFFiFszTkyFnZntLpzZBpNECh&sig=41f41019e3881b338bdee4922f1dc012", "Vô Tình - Xesi , Hoaprox"));
        listAudio.add(new AudioObject("https://mp3.zing.vn/xhr/media/download-source?type=audio&code=LmcmtLmaJbdmXBmtktFGLHykpzkBgHWLp&sig=9f4375527424a766025fd6fce32f696f", "Càng Níu Giữ Càng Dễ Mất - Mr Siro"));
        listAudio.add(new AudioObject("https://mp3.zing.vn/xhr/media/download-source?type=audio&code=knxnyLHscbczQVAyktbnkHtkpzZdXHWNn&sig=7c240afc74bf1adfb14da14e2dff42e4", "Chấp Nhận - Hòa Minzy"));
        listAudio.add(new AudioObject("https://mp3.zing.vn/xhr/media/download-source?type=audio&code=LmxHyZHaxbssQlhtLTbmZHTZpzkBhmWRg&sig=718e99a0831de4f6d74440ad62ee9073", "Em Không Thể - Tiên Tiên , Touliver"));
        listAudio.add(new AudioObject("https://mp3.zing.vn/xhr/media/download-source?type=audio&code=LHcmyZHNJbdiNzstLTbmZnykpzZBCmCbF&sig=9c92daf472ea2e46426bd66b2921e6aa", "Hongkong1 (Official Version) - Nguyễn Trọng Tài , San Ji , Double X"));
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
