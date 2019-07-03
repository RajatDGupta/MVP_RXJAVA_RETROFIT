package com.giri.mvp_retrofit_rxjava.presenter;

import com.giri.mvp_retrofit_rxjava.api.NewsApi;
import com.giri.mvp_retrofit_rxjava.contract.NewsApiContract;
import com.giri.mvp_retrofit_rxjava.model.NewsResponse;
import com.giri.mvp_retrofit_rxjava.network.ApiClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenterImplementation implements NewsApiContract.Presenter{

    private NewsApiContract.NewsView newsView;

    @Override
    public void attached(NewsApiContract.NewsView newsView) {
        this.newsView=newsView;
    }

    @Override
    public void getNews() {
        if(newsView!=null){
          newsView.setLoadingIndicator(true);
          getNewsObservable().subscribeWith(getNewsObserver());
        }
    }

    @Override
    public Observable<NewsResponse> getNewsObservable() {
        if(newsView!=null){
        return ApiClient.
                getClient(newsView.getContext()).
                create(NewsApi.class).getNews().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }else {
            return null;
        }
    }

    @Override
    public DisposableObserver<NewsResponse> getNewsObserver() {
        return new DisposableObserver<NewsResponse>() {
            @Override
            public void onNext(NewsResponse newsResponse) {
                if(newsView!=null && newsResponse!=null && newsResponse.getArticles()!=null)
                newsView.newsResponse(newsResponse.getArticles());
            }

            @Override
            public void onError(Throwable e) {
                if(newsView!=null){
                 newsView.error(e);
                 newsView.setLoadingIndicator(false);
                }

            }

            @Override
            public void onComplete() {
                if(newsView!=null){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    newsView.setLoadingIndicator(false);
                }
            }
        };
    }

    @Override
    public void detached() {
        this.newsView=null;
    }
}
