package com.moomen.movieyou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.moomen.movieyou.R;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.model.video.VideoTrailerMovieResults;
import com.moomen.movieyou.ui.OverviewMovie;
import com.moomen.movieyou.ui.PlayVideoTrailerYouTube;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoTrailerViewPagerAdapter extends PagerAdapter {

    public static final String KEY_VIDEO = "KEY_VIDEO";
    public static final String TYPE_MEDIA = "TYPE_MEDIA";
    public static final String ID_MEDIA = "ID_MEDIA";
    private Context context;
    private ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList;
    private boolean isLiveFragment = false;
    private String mediaType = "movie";

    public VideoTrailerViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void setVideoTrailerMovieResultsArrayList(ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList) {
        this.videoTrailerMovieResultsArrayList = videoTrailerMovieResultsArrayList;
        notifyDataSetChanged();
    }

    public void setLiveFragment(boolean liveFragment) {
        isLiveFragment = liveFragment;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.video_item_pager, null);
        ImageView imageViewTrailer = view.findViewById(R.id.videoWebView);
        ImageView imageRated = view.findViewById(R.id.imageView13);
        ImageView poster = view.findViewById(R.id.imageView_poster_movie_trailer_id);
        TextView nameTrailer = view.findViewById(R.id.textView_name_trailer_id);
        LinearLayout linearLayoutWatchNow = view.findViewById(R.id.linearLayout_watch_now_trailer_id);
        Button buttonVisit = view.findViewById(R.id.button_visit_id);
        CardView cardView = view.findViewById(R.id.card_view_video_item_id);
        TextView rleaseDate = view.findViewById(R.id.date_release_trailer_id);
        TextView rated = view.findViewById(R.id.rate_trailer_id);
        VideoTrailerMovieResults currentMedia = videoTrailerMovieResultsArrayList.get(position);
        String name = "";
        String keyVideo;

        MoviesResults moviesResults = currentMedia.getMoviesResults();
        keyVideo = currentMedia.getKey();
        String url = "https://img.youtube.com/vi/" + keyVideo + "/0.jpg";
        //cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
        Picasso.get()
                .load(url)
                .into(imageViewTrailer);
        name = moviesResults.getTitle();
        if (name == null)
            name = moviesResults.getName();
        nameTrailer.setText(name + ": " + currentMedia.getName());
        String urlPoster = moviesResults.getPoster_path();
        Picasso.get()
                .load(urlPoster)
                .into(poster);
        String date = moviesResults.getRelease_date();
        if (date == null)
            date = moviesResults.getFirst_air_date();
        rleaseDate.setText(date);
        String rate = String.valueOf(moviesResults.getVote_average()).substring(0, 3);
        rated.setText(rate);


        setOnClickListener(nameTrailer, nameTrailer.getId(), position);
        setOnClickListener(linearLayoutWatchNow, 0, position);
        setOnClickListener(buttonVisit, buttonVisit.getId(), position);
        container.addView(view, 0);
        return view;
    }

    private void setOnClickListener(View view, int buttonVisitId, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoTrailerMovieResults videoTrailerMovieResults = videoTrailerMovieResultsArrayList.get(position);
                String keyVideo = videoTrailerMovieResults.getKey();
                String idMedia = String.valueOf(videoTrailerMovieResultsArrayList.get(position).getMoviesResults().getId());
                String title = videoTrailerMovieResultsArrayList.get(position).getMoviesResults().getTitle();
                if (title == null)
                    mediaType = "tv";
                Intent intent = new Intent(context, PlayVideoTrailerYouTube.class);
                //Check if click on visit media button go to overview movie activity else go to play video by defylt!
                if (view.getId() == buttonVisitId) {
                    intent = new Intent(context, OverviewMovie.class);
                    intent.putExtra(ID_MEDIA, idMedia);
                    intent.putExtra(TYPE_MEDIA, mediaType);
                } else
                    intent.putExtra(KEY_VIDEO, keyVideo);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        return videoTrailerMovieResultsArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
