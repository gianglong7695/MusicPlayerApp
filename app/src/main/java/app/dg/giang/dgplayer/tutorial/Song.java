package app.dg.giang.dgplayer.tutorial;

/**
 * Created by Giang Long on 11/7/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class Song {
    private long id;
    private String title;
    private String patch;

    public Song(long id, String title, String patch) {
        this.id = id;
        this.title = title;
        this.patch = patch;
    }

    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPatch() {
        return patch;
    }
}
