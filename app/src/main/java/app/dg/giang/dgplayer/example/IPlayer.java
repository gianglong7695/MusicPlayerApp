package app.dg.giang.dgplayer.example;

public interface IPlayer {
    void updateTime(long curent, long duration);

    void updateState(int state);
}
