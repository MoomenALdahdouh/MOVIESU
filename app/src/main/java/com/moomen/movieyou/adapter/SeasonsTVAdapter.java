package com.moomen.movieyou.adapter;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moomen.movieyou.R;
import com.moomen.movieyou.model.tv.DetailTVSeasons;
import com.moomen.movieyou.ui.OverviewTVEpisode;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeasonsTVAdapter extends RecyclerView.Adapter<SeasonsTVAdapter.ViewHolder> {

    public static final String ID_TV = "ID_TV";
    public static final String NUMBER_SEASON = "NUMBER_SEASON";
    public static final String NUMBER_EPISODE = "NUMBER_EPISODE";
    private Context context;
    private ArrayList<DetailTVSeasons> detailTVSeasonsArrayList;
    private View view;
    private String idTV;

    public SeasonsTVAdapter(Context context) {
        this.context = context;
    }

    public void setIdTV(String idTV) {
        this.idTV = idTV;
    }

    public void setDetailTVSeasonsArrayList(ArrayList<DetailTVSeasons> detailTVSeasonsArrayList) {
        this.detailTVSeasonsArrayList = detailTVSeasonsArrayList;
    }

    @NonNull
    @Override
    public SeasonsTVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.season_tv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsTVAdapter.ViewHolder holder, int position) {
        DetailTVSeasons detailTVSeasons = detailTVSeasonsArrayList.get(position);
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_season_tv_item_id);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
        try {
            String posterSeason = detailTVSeasons.getPoster_path();
            Picasso.get()
                    .load(posterSeason)
                    .into(holder.posterSeason, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("e", e.getMessage());
                        }
                    });
            holder.nameSeason.setText(detailTVSeasons.getName());
        } catch (NullPointerException e) {
            Log.d("e", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return detailTVSeasonsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterSeason;
        TextView nameSeason;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterSeason = itemView.findViewById(R.id.imageView_poster_season_tv_id);
            nameSeason = itemView.findViewById(R.id.textView_name_season_tv_id);
            cardView = itemView.findViewById(R.id.card_view_season_item_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        DetailTVSeasons detailTVSeasons = detailTVSeasonsArrayList.get(adapterPosition);
                        String numberSeason = String.valueOf(detailTVSeasons.getSeason_number());
                        String numberEpisode = String.valueOf(detailTVSeasons.getEpisode_count());
                        Intent intent = new Intent(context, OverviewTVEpisode.class);
                        intent.putExtra(ID_TV, idTV);
                        intent.putExtra(NUMBER_SEASON, numberSeason);
                        intent.putExtra(NUMBER_EPISODE, numberEpisode);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
