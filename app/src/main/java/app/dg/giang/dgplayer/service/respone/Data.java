package app.dg.giang.dgplayer.service.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class Data {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("data")
    @Expose
    private List<Audio> playlist = null;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Audio> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Audio> playlist) {
        this.playlist = playlist;
    }

}
