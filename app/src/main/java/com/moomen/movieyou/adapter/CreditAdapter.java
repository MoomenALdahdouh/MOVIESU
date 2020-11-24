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
import com.moomen.movieyou.model.credits.CreditsModelCast;
import com.moomen.movieyou.ui.OverviewCredit;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {

    public static final String CREDIT_ID = "CREDIT_ID";
    private Context context;
    private ArrayList<CreditsModelCast> creditsModelArrayList;
    private View view;

    public CreditAdapter(Context context) {
        this.context = context;
    }

    public void setCreditsModelArrayList(ArrayList<CreditsModelCast> creditsModelArrayList) {
        this.creditsModelArrayList = creditsModelArrayList;
    }

    @NonNull
    @Override
    public CreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.credit_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.ViewHolder holder, int position) {
        CreditsModelCast creditsModel = creditsModelArrayList.get(position);
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_credit_id);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
        try {
            String profilePath = creditsModel.getProfile_path();
            String creditName = creditsModel.getName();
            String creditJob = creditsModel.getJob();
            String creditCharacter = creditsModel.getCharacter();
            Picasso.get()
                    .load(profilePath)
                    .into(holder.profileCredit, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("e", e.getMessage());
                        }
                    });
            holder.nameCredit.setText(creditName);
            if (creditJob == null)
                holder.characterOrJopCredit.setText(creditCharacter);
            else
                holder.characterOrJopCredit.setText(creditJob);
        } catch (NullPointerException e) {
            Log.d("e", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return creditsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileCredit;
        TextView nameCredit;
        TextView characterOrJopCredit;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCredit = itemView.findViewById(R.id.profile_image_credit_id);
            nameCredit = itemView.findViewById(R.id.textView_name_credit_id);
            characterOrJopCredit = itemView.findViewById(R.id.textView_character_credit_id);
            cardView = itemView.findViewById(R.id.card_view_credit_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        CreditsModelCast creditsModel = creditsModelArrayList.get(adapterPosition);
                        String creditId = String.valueOf(creditsModel.getCredit_id());
                        Intent intent = new Intent(context, OverviewCredit.class);
                        intent.putExtra(CREDIT_ID, creditId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
