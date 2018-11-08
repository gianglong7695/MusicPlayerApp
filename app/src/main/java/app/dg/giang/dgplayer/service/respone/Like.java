package app.dg.giang.dgplayer.service.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class Like {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("type")
    @Expose
    private List<Integer> type = null;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
