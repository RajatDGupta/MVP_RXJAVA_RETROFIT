package com.giri.mvp_retrofit_rxjava.api;

import com.giri.mvp_retrofit_rxjava.model.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsApi {

    @GET("everything?q=bitcoin&from=2019-07-03&sortBy=publishedAt&apiKey=5a21f9b5859b4b0fbd27a109d0f26912")
    Observable<NewsResponse> getNews();

}
