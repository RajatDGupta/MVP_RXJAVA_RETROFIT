package com.giri.mvp_retrofit_rxjava.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.giri.mvp_retrofit_rxjava.R;
import com.giri.mvp_retrofit_rxjava.model.Article;
import com.giri.mvp_retrofit_rxjava.contract.NewsApiContract;
import com.giri.mvp_retrofit_rxjava.presenter.NewsPresenterImplementation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements NewsApiContract.NewsView {

    private static final String TAG =MainActivity.class.getSimpleName() ;
    private NewsPresenterImplementation newsPresenterImplementation;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder= ButterKnife.bind(this);
        newsPresenterImplementation=new NewsPresenterImplementation();
        newsPresenterImplementation.attached(this);
        newsPresenterImplementation.getNews();

    }

    @Override
    public void setLoadingIndicator(boolean b) {
        if(progressBar!=null)
        progressBar.setVisibility(b? View.VISIBLE:View.GONE);
    }

    @Override
    public void newsResponse(List<Article> articles) {
        if(articles!=null){
            Log.d(TAG,String.valueOf(articles.size()));
        }
    }

    @Override
    public void error(Throwable throwable) {
        Log.d(TAG,String.valueOf(throwable.getMessage()));
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenterImplementation.detached();
        newsPresenterImplementation=null;
        unbinder.unbind();
    }
}
