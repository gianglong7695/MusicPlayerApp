package app.dg.giang.auplayer;

public interface IPlayer {
    void loadMedia(int resourceId);
    void loadMedia(String url);
    void release();
    boolean isPlaying();
    void play();
    void reset();
    void pause();
    void initializeProgressCallback();
    void seekTo(int position);
}
