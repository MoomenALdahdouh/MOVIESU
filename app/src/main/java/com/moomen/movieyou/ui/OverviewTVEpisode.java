package com.moomen.movieyou.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.EpisodeTVAdapter;
import com.moomen.movieyou.adapter.SeasonsTVAdapter;
import com.moomen.movieyou.model.tv.DetailTVEpisode;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;

public class OverviewTVEpisode extends AppCompatActivity {

    ArrayList<DetailTVEpisode> episodeArrayList = new ArrayList<>();
    //For Episode TV
    private MoviesViewModel moviesViewModel;
    private EpisodeTVAdapter episodeTVAdapter;
    private ArrayList<DetailTVEpisode> episodeArrayList1 = new ArrayList<>();
    private RecyclerView episodeTVRecyclerView;
    private String idTV = "80986";
    private String numberEpisode = "1";
    private String numberSeason = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_tv_episode);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        Intent tv = getIntent();
        if (tv != null
                && tv.hasExtra(SeasonsTVAdapter.ID_TV)
                && tv.hasExtra(SeasonsTVAdapter.NUMBER_SEASON)
                && tv.hasExtra(SeasonsTVAdapter.NUMBER_EPISODE)) {
            idTV = tv.getStringExtra(SeasonsTVAdapter.ID_TV);
            numberSeason = tv.getStringExtra(SeasonsTVAdapter.NUMBER_SEASON);
            numberEpisode = tv.getStringExtra(SeasonsTVAdapter.NUMBER_EPISODE);
        }
        int numEpisode = Integer.parseInt(numberEpisode);
        for (int i = 1; i <= numEpisode; i++) {
            getEpisodeTV(idTV, numberSeason, i + "");
        }
        episodeTVRecyclerView = findViewById(R.id.recyclerView_episode_tv_id);
        episodeTVAdapter = new EpisodeTVAdapter(this);
        episodeTVAdapter.setDetailTVEpisodeArrayList(episodeArrayList1);
        episodeTVRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        episodeTVRecyclerView.setAdapter(episodeTVAdapter);
        episodeTVRecyclerView.setHasFixedSize(true);
    }

    //Get episode TV
    private void getEpisodeTV(String idTV, String numberSeason, String numberEpisode) {
        moviesViewModel.getEpisodeTV(idTV, numberSeason, numberEpisode);
        moviesViewModel.episodeTVMutableLiveData.observe(this, new Observer<DetailTVEpisode>() {
            @Override
            public void onChanged(DetailTVEpisode detailTVEpisode) {
                episodeArrayList.clear();
                episodeArrayList.add(detailTVEpisode);
                if (!episodeArrayList1.contains(episodeArrayList.get(0)))
                    episodeArrayList1.addAll(episodeArrayList);
                episodeTVAdapter.notifyDataSetChanged();
            }
        });
    }

}
