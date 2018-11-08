package app.dg.giang.dgplayer.tutorial;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public interface MusicController {
    void onMusicPlay();

    void onMusicPause();

    void onMusicPlay(int index);

    int getDuration();

    int getCurrentPosition();

    void seekTo(int var1);

    boolean isPlaying();

    int getBufferPercentage();

    boolean canPause();

    boolean canSeekBackward();

    boolean canSeekForward();

    int getAudioSessionId();
}
