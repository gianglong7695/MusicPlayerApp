package app.dg.giang.dgplayer.example;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class PlayerMessage {
    public String path;
    public boolean isClear;
    public int index;
    public IPlayer callback;
    public long progress;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public IPlayer getCallback() {
        return callback;
    }

    public void setCallback(IPlayer callback) {
        this.callback = callback;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
