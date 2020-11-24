package com.moomen.movieyou.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.moomen.movieyou.R;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.ui.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SplashAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<MoviesResults> moviesResultsArrayList;

    private boolean isSplashActivity = false;

    public SplashAdapter(Context context) {
        this.context = context;
    }

    public void setSplashActivity(boolean splashActivity) {
        isSplashActivity = splashActivity;
    }

    public void setMoviesResultsArrayList(ArrayList<MoviesResults> moviesResultsArrayList) {
        this.moviesResultsArrayList = moviesResultsArrayList;
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.splash_item, null);

        ImageView posterView = view.findViewById(R.id.poster_item_splash_idt);
        TextView titleView = view.findViewById(R.id.title_item_splash_id);
        TextView genresView = view.findViewById(R.id.genres_item_splash_id);
        TextView dateReleaseView = view.findViewById(R.id.date_release_item_splash_id);
        TextView pgView = view.findViewById(R.id.pg_splash_item_id);
        TextView rateView = view.findViewById(R.id.rate_splash_item_id);
        TextView descriptionView = view.findViewById(R.id.description_item_splash_id);
        descriptionView.setVisibility(View.GONE);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayout);
        //Button buttonOpenAppView = view.findViewById(R.id.button);
        //buttonOpenAppView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_infinit));
        //Creat animator
        /*ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context,R.animator.animator_color);
        objectAnimator.setTarget(buttonOpenAppView);
        objectAnimator.start();*/
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_splash_item_id);
        MoviesResults moviesResults = moviesResultsArrayList.get(position);
        moviesResults.setSplshActivity(isSplashActivity);
        try {
            String posterPath = moviesResults.getPoster_path();
            String title = moviesResults.getTitle();
            String dateRelease = moviesResults.getRelease_date();
            String rate = String.valueOf(moviesResults.getVote_average());
            String description = moviesResults.getOverview();
            String genres = "";

            if (title == null)
                title = moviesResults.getName();
            titleView.setText(title);
            if (dateRelease == null)
                dateRelease = moviesResults.getFirst_air_date();
            dateReleaseView.setText(dateRelease);
            if (rate.equals("10.0"))
                rate = "10";
            rateView.setText(rate);
            descriptionView.setText(description);
            //old code to get genres without use retrofit
            /*ArrayList<Integer> detailMovieGenresArrayList = moviesResults.getGenre_ids();
            for (int i = 0; i < detailMovieGenresArrayList.size(); i++) {
                if (i != 0)
                    genres.append(", ").append(detailMovieGenresArrayList.get(i));
                else
                    genres = new StringBuilder(detailMovieGenresArrayList.get(i));
            }*/
            //get gnres with use retrofit
            /*ArrayList<DetailMovieGenres> detailMovieGenresArrayList = new ArrayList<>(moviesResults.getGenres());
            for (int i = 0; i < detailMovieGenresArrayList.size(); i++) {
                if (i != 0)
                    genres = genres + ", " + detailMovieGenresArrayList.get(i).getName();
                else
                    genres = detailMovieGenresArrayList.get(i).getName();
            }*/
            genresView.setText(genres);
            Picasso.get()
                    .load(posterPath)
                    .into(posterView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("e", e.getMessage());
                        }
                    });


            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (descriptionView.getVisibility() == View.GONE) {
                        descriptionView.setVisibility(View.VISIBLE);
                        descriptionView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                    } else
                        descriptionView.setVisibility(View.GONE);
                }
            });
            /**3-Rewarded ad**/
            MobileAds.initialize(context);
            RewardedAd rewardedAd = new RewardedAd(context, "ca-app-pub-3940256099942544/5224354917");
            rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback());
            /*buttonOpenAppView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rewardedAd.isLoaded())
                        displayAd(rewardedAd);
                    else {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            });*/
        } catch (NullPointerException e) {
            Log.d("e", e.getMessage());
        }
        container.addView(view, 0);
        return view;
    }

    @Override
    public int getCount() {
        return moviesResultsArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    /**
     * Rewarded ad display
     **/
    private void displayAd(RewardedAd rewardedAd) {
        rewardedAd.show(((Activity) context), new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                if (rewardedAd.isLoaded()) {

                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }

            @Override
            public void onRewardedAdClosed() {
                super.onRewardedAdClosed();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }


}
