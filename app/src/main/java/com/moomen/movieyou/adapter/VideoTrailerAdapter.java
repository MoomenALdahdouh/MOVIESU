package com.moomen.movieyou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moomen.movieyou.R;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.model.video.VideoTrailerMovieResults;
import com.moomen.movieyou.ui.OverviewMovie;
import com.moomen.movieyou.ui.PlayVideoTrailerYouTube;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoTrailerAdapter extends RecyclerView.Adapter<VideoTrailerAdapter.ViewHolder> {

    public static final String KEY_VIDEO = "KEY_VIDEO";
    public static final String TYPE_MEDIA = "TYPE_MEDIA";
    public static final String ID_MEDIA = "ID_MEDIA";
    private Context context;
    private ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList;
    private boolean isLiveFragment = false;
    private String mediaType = "movie";

    private boolean isFreeMovie = false;

    public VideoTrailerAdapter(Context context) {
        this.context = context;
    }

    public void setIsFreeMovie(boolean freeMovie) {
        isFreeMovie = freeMovie;
    }

    public void setVideoTrailerMovieResultsArrayList(ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList) {
        this.videoTrailerMovieResultsArrayList = videoTrailerMovieResultsArrayList;
        notifyDataSetChanged();
    }

    public void setLiveFragment(boolean liveFragment) {
        isLiveFragment = liveFragment;
    }

    @NonNull
    @Override
    public VideoTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull VideoTrailerAdapter.ViewHolder holder, int position) {
        VideoTrailerMovieResults currentMedia = videoTrailerMovieResultsArrayList.get(position);
        String name = "";
        String keyVideo;
        if (!isLiveFragment) {
            if (isFreeMovie && position == 0 || position == 1 || position == 2) {
                holder.buttonVisit.setText(R.string.new_movie);
                holder.buttonVisit.setBackgroundResource(R.drawable.full_corner_background_red);
                holder.buttonVisit.setTextColor(Color.WHITE);
                holder.buttonVisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else
                holder.buttonVisit.setVisibility(View.GONE);
            holder.poster.setVisibility(View.GONE);
            holder.rated.setVisibility(View.GONE);
            holder.rleaseDate.setVisibility(View.GONE);
            holder.imageRated.setVisibility(View.GONE);
            keyVideo = currentMedia.getKey();
            name = currentMedia.getName();
            String url = "https://img.youtube.com/vi/" + keyVideo + "/0.jpg";
            holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
            //holder.cardView.setBackgroundColor(R.color.black1);
            Picasso.get()
                    .load(url)
                    .into(holder.imageViewTrailer);
            holder.nameTrailer.setText(name);
        } else {
            MoviesResults moviesResults = currentMedia.getMoviesResults();
            keyVideo = currentMedia.getKey();
            String url = "https://img.youtube.com/vi/" + keyVideo + "/0.jpg";
            //holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
            Picasso.get()
                    .load(url)
                    .into(holder.imageViewTrailer);
            name = moviesResults.getTitle();
            if (name == null)
                name = moviesResults.getName();
            holder.nameTrailer.setText(name + ": " + currentMedia.getName());
            String urlPoster = moviesResults.getPoster_path();
            Picasso.get()
                    .load(urlPoster)
                    .into(holder.poster);
            //holder.poster.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scal));
            String date = moviesResults.getRelease_date();
            if (date == null)
                date = moviesResults.getFirst_air_date();
            holder.rleaseDate.setText(date);
            String rated = String.valueOf(moviesResults.getVote_average()).substring(0, 3);
            holder.rated.setText(rated);
        }
    }

    @Override
    public int getItemCount() {
        return videoTrailerMovieResultsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewTrailer;
        ImageView imageRated;
        ImageView poster;
        TextView nameTrailer;
        LinearLayout linearLayoutWatchNow;
        Button buttonVisit;
        CardView cardView;
        TextView rleaseDate;
        TextView rated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewTrailer = itemView.findViewById(R.id.videoWebView);
            nameTrailer = itemView.findViewById(R.id.textView_name_trailer_id);
            linearLayoutWatchNow = itemView.findViewById(R.id.linearLayout_watch_now_trailer_id);
            buttonVisit = itemView.findViewById(R.id.button_visit_id);
            cardView = itemView.findViewById(R.id.card_view_video_item_id);
            imageRated = itemView.findViewById(R.id.imageView13);
            rated = itemView.findViewById(R.id.rate_trailer_id);
            rleaseDate = itemView.findViewById(R.id.date_release_trailer_id);
            poster = itemView.findViewById(R.id.imageView_poster_movie_trailer_id);
            setOnClickListener(nameTrailer);
            setOnClickListener(linearLayoutWatchNow);
            setOnClickListener(buttonVisit);
        }

        private void setOnClickListener(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        VideoTrailerMovieResults videoTrailerMovieResults = videoTrailerMovieResultsArrayList.get(adapterPosition);
                        String keyVideo = videoTrailerMovieResults.getKey();
                        String idMedia = "";
                        String title = "";
                        if (isLiveFragment) {
                            idMedia = String.valueOf(videoTrailerMovieResultsArrayList.get(adapterPosition).getMoviesResults().getId());
                            title = videoTrailerMovieResultsArrayList.get(adapterPosition).getMoviesResults().getTitle();
                            if (title == null)
                                mediaType = "tv";
                        }
                        Intent intent = new Intent(context, PlayVideoTrailerYouTube.class);
                        //Check if click on visit media button go to overview movie activity else go to play video by defylt!
                        if (view.getId() == buttonVisit.getId()) {
                            intent = new Intent(context, OverviewMovie.class);
                            intent.putExtra(ID_MEDIA, idMedia);
                            intent.putExtra(TYPE_MEDIA, mediaType);
                        } else
                            intent.putExtra(KEY_VIDEO, keyVideo);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
