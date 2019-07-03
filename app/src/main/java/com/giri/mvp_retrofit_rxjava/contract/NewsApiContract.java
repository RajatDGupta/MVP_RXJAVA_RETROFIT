package com.giri.mvp_retrofit_rxjava.contract;

import android.content.Context;

import com.giri.mvp_retrofit_rxjava.model.Article;
import com.giri.mvp_retrofit_rxjava.model.NewsResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public interface NewsApiContract {

    interface Presenter {
      void attached(NewsView  newsView);
      void getNews();
      Observable<NewsResponse> getNewsObservable();
      DisposableObserver<NewsResponse> getNewsObserver();
      void detached();
    }
    interface NewsView {
        void setLoadingIndicator(boolean b);
        void newsResponse(List<Article> articles);
        void error(Throwable throwable);
        Context getContext();
    }
}
