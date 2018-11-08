package app.dg.giang.dgplayer.service.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("card_type")
    @Expose
    private Integer cardType;
    @SerializedName("data")
    @Expose
    private Object data = null;
    @SerializedName("related")
    @Expose
    private List<List<Related>> related = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("card_info")
    @Expose
    private CardInfo cardInfo;
    @SerializedName("like")
    @Expose
    private Like like;
    @SerializedName("comment")
    @Expose
    private Comment comment;
    @SerializedName("shares")
    @Expose
    private List<Share> shares = null;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("news")
    @Expose
    private List<News> news = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<List<Related>> getRelated() {
        return related;
    }

    public void setRelated(List<List<Related>> related) {
        this.related = related;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
