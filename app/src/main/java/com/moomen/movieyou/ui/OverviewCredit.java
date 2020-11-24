package com.moomen.movieyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.CreditAdapter;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.model.credits.OverviewCreditModel;
import com.moomen.movieyou.model.credits.OverviewCreditModelPerson;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.viewModel.MoviesViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class OverviewCredit extends AppCompatActivity {

    private MoviesViewModel moviesViewModel;
    private String idCredit;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    //Similar Movie
    //private MoviesRecycleAdapter creditMediaRecycleAdapter;
    private MoviesRecycleAdapter creditPersonRecycleAdapter;
    //private ArrayList<MoviesResults> creditMediaArrayList = new ArrayList<>();
    private ArrayList<MoviesResults> creditPersonArrayList = new ArrayList<>();
    //private RecyclerView creditMediaRecyclerView;
    private RecyclerView creditPersonRecyclerView;
    //Item Overview credit layout
    private ImageView posterCredit;
    private TextView nameCredit;
    private TextView departmentCredit;
    private TextView jobCredit;
    private TextView popularityCredit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_credit);

        /**1-Banner ad**/
        AdView adView = new AdView(this);
        adView = findViewById(R.id.adView_banner_credit);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        posterCredit = findViewById(R.id.imageView_poster_credit_id);
        nameCredit = findViewById(R.id.textView_name_credit_overview_id);
        departmentCredit = findViewById(R.id.textView_department_credit_overview_id);
        jobCredit = findViewById(R.id.textView_job_credit_overview_id);
        popularityCredit = findViewById(R.id.textView_popularity_credit_overview_id);
        progressBar = findViewById(R.id.progress_bar_poster_credit_overview_id);
        //creditMediaRecyclerView = findViewById(R.id.recyclerView_media_credit_overview_id);
        creditPersonRecyclerView = findViewById(R.id.recyclerView_person_credit_overview_id);
        Intent credit = getIntent();
        if (credit != null && credit.hasExtra(CreditAdapter.CREDIT_ID)) {
            idCredit = credit.getStringExtra(CreditAdapter.CREDIT_ID);
        }
        getCredit(idCredit);
    }

    //Get credit
    private void getCredit(String idCredit) {
        moviesViewModel.getCredit(idCredit);
        //creditMediaRecycleAdapter = new MoviesRecycleAdapter(this);
        creditPersonRecycleAdapter = new MoviesRecycleAdapter(this);
        moviesViewModel.creditMutableLiveData.observe(this, new Observer<OverviewCreditModel>() {
            @Override
            public void onChanged(OverviewCreditModel creditModel) {
                //MoviesResults moviesResults = creditModel.getMedia();
                OverviewCreditModelPerson overviewCreditModelPerson = creditModel.getPerson();
                //Load poster Image
                String popularity = String.valueOf(overviewCreditModelPerson.getPopularity()).substring(0, 4) + "%";
                String posterPath = "https://image.tmdb.org/t/p/w342/" + overviewCreditModelPerson.getProfile_path();
                Picasso.get()
                        .load(posterPath)
                        .into(posterCredit, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d("e", e.getMessage());
                            }
                        });
                nameCredit.setText(overviewCreditModelPerson.getName());
                departmentCredit.setText(overviewCreditModelPerson.getKnown_for_department());
                jobCredit.setText(creditModel.getJob());
                popularityCredit.setText(" " + popularity + " ");
                //creditMediaArrayList.add(moviesResults);
                creditPersonArrayList = new ArrayList<>(overviewCreditModelPerson.getKnown_for());
                //creditMediaRecycleAdapter.setList(creditMediaArrayList);
                creditPersonRecycleAdapter.setList(creditPersonArrayList);
                //creditMediaRecycleAdapter.notifyDataSetChanged();
                creditPersonRecycleAdapter.notifyDataSetChanged();
            }
        });
        //creditMediaRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        creditPersonRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //creditMediaRecyclerView.setAdapter(creditMediaRecycleAdapter);
        creditPersonRecyclerView.setAdapter(creditPersonRecycleAdapter);
    }
}
