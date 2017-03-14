package com.example.rent.filmbrowserapp.details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rent.filmbrowserapp.R;
import com.example.rent.filmbrowserapp.RetrofitProvider;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static com.example.rent.filmbrowserapp.R.id.poster;

@RequiresPresenter(DetailPresenter.class)
public class DetailActivity extends NucleusAppCompatActivity<DetailPresenter> {
    private static final String ID_KEY = "id_key";
    private Disposable subscribe;

    @BindView(R.id.detail_cast)
    TextView cast;

    @BindView(R.id.detail_director)
    TextView director;

    @BindView(R.id.detail_plot)
    TextView plot;

    @BindView(R.id.detail_rate)
    TextView rate;

    @BindView(R.id.detail_awards)
    TextView awards;

    @BindView(R.id.detail_runtime)
    TextView runtime;

    @BindView(R.id.title_and_year)
    TextView titleAndYear;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.poster)
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String imdbId = getIntent().getStringExtra(ID_KEY);

        ButterKnife.bind(this);

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        getPresenter().setRetrofit(retrofitProvider.provideRetrofit());

        subscribe = getPresenter().loadDetail(imdbId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private void success(MovieItem movieItem) {


        plot.setText(movieItem.getPlot() + "\n");
        rate.setText(movieItem.getImdbRating() + "/10" + "\n");
        awards.setText(movieItem.getAwards()+ "\n");
        director.setText(movieItem.getDirector() + "\n");
        cast.setText(movieItem.getActors());
        runtime.setText(movieItem.getRuntime());
        Glide.with(this).load(movieItem.getPoster()).into(poster);
        titleAndYear.setText(movieItem.getTitle() + " (" + movieItem.getYear() + ")");
        type.setText(movieItem.getType());


    }


    private void error(Throwable throwable) {

    }

    public static Intent createIntent(Context context, String imdID) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID_KEY, imdID);
        return intent;
    }
}
