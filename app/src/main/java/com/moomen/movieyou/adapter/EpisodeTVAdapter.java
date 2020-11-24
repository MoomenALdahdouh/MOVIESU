package com.moomen.movieyou.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moomen.movieyou.R;
import com.moomen.movieyou.model.tv.DetailTVEpisode;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EpisodeTVAdapter extends RecyclerView.Adapter<EpisodeTVAdapter.ViewHolder> {
    private Context context;
    private View view;
    private ArrayList<DetailTVEpisode> detailTVEpisodeArrayList;

    public EpisodeTVAdapter(Context context) {
        this.context = context;
    }

    public void setDetailTVEpisodeArrayList(ArrayList<DetailTVEpisode> detailTVEpisodeArrayList) {
        this.detailTVEpisodeArrayList = detailTVEpisodeArrayList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EpisodeTVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.episode_tv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeTVAdapter.ViewHolder holder, int position) {
        DetailTVEpisode detailTVEpisode = detailTVEpisodeArrayList.get(position);
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_episode_tv_id);
        //holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));//TODO: Animation
        try {
            String posterEpisode = detailTVEpisode.getStill_path();
            Picasso.get()
                    .load(posterEpisode)
                    .into(holder.posterEpisode, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("e", e.getMessage());
                        }
                    });

            String voteAverage = String.valueOf(detailTVEpisode.getVote_average() * 10).substring(0, 2) + "%";
            String numberEpisode = "Episode " + detailTVEpisode.getEpisode_number();
            String date = detailTVEpisode.getAir_date();
            String overview = detailTVEpisode.getOverview();
            String name = detailTVEpisode.getName();
            holder.voteEpisode.setText(voteAverage);
            holder.numberEpisode.setText(numberEpisode);
            holder.dateEpisode.setText(date);
            holder.nameEpisode.setText(name);
            holder.overviewEpisode.setText(overview);
        } catch (NullPointerException e) {
            Log.d("e", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return detailTVEpisodeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterEpisode;
        TextView voteEpisode;
        TextView numberEpisode;
        TextView dateEpisode;
        TextView nameEpisode;
        TextView overviewEpisode;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterEpisode = itemView.findViewById(R.id.imageView_still_episode_tv_id);
            voteEpisode = itemView.findViewById(R.id.textView_vote_average_episode_id);
            numberEpisode = itemView.findViewById(R.id.textView_episode_number_id);
            dateEpisode = itemView.findViewById(R.id.textView_date_episode_id);
            nameEpisode = itemView.findViewById(R.id.textView_name_episode_id);
            overviewEpisode = itemView.findViewById(R.id.textView_overview_episode_id);
            cardView = itemView.findViewById(R.id.card_view_episode_item_id);
        }
    }
}
