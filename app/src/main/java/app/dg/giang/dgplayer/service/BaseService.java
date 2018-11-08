package app.dg.giang.dgplayer.service;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class BaseService {
    public static IApiService getService() {
        return getRetrofit("").create(IApiService.class);
    }

    public static IApiService getService(String yourDomain) {
        return getRetrofit(yourDomain).create(IApiService.class);
    }


    private static Retrofit getRetrofit(String domain) {
        System.setProperty("http.keepAlive", "false");
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit;

        if (domain != null && !TextUtils.isEmpty(domain)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(domain)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.SERVER_DOMAIN)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
