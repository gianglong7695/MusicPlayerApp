package app.dg.giang.dgplayer.service;

import app.dg.giang.dgplayer.service.respone.HomeRespone;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public interface IApiService {
    @GET(ApiConstants.URL_GET_MUSIC)
    Call<HomeRespone> getMusic();
}
