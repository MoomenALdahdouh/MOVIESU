package com.moomen.movieyou.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.moomen.movieyou.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPagerAdapter extends PagerAdapter {

    //Auto Image Slider with ViewPager
    private final Handler handler = new Handler();
    private ArrayList<String> slidPagerArrayList;
    private Context context;
    private AlertDialog alertDialog;
    /*private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;*/
    private ImageView imageViewFull;
    //
    private OnItemClickListener mOnItemClickListener;

    public ViewPagerAdapter(Context context, ArrayList<String> slidPagerArrayList) {
        this.context = context;
        this.slidPagerArrayList = slidPagerArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View slidLayout = inflater.inflate(R.layout.view_pager_item_not_designed, null);
        String currentPage = slidPagerArrayList.get(position);
        ImageView imageMovie = slidLayout.findViewById(R.id.image_slid_pager_id);
        final ProgressBar progressBar = slidLayout.findViewById(R.id.progress_bar_image_movie_id);
        Picasso.get()
                .load(currentPage)
                .into(imageMovie, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("e", e.getMessage());
                    }
                });
        imageMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.setOnItemClickListener(container, position);
            }
        });
        container.addView(slidLayout);
        return slidLayout;
    }

    //Auto Image Slider with ViewPager
    public void setTimer(final ViewPager myPager, int time) {
        final Runnable Update = new Runnable() {
            int NUM_PAGES = slidPagerArrayList.size() - 1;
            int currentPage = 0;

            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, time * 1000);
    }

    @Override
    public int getCount() {
        return slidPagerArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void showpopup(String currentPage) {
        //Deffin dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_view_image, null);
        builder.setView(dialogView);
        alertDialog = builder.create();
        ProgressBar progressBar = dialogView.findViewById(R.id.progress_bar_image_dialog);
        imageViewFull = dialogView.findViewById(R.id.imageView_full_id);
        Picasso.get()
                .load(currentPage)
                .into(imageViewFull, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("e", e.getMessage());
                    }
                });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(ViewGroup view, int position);
    }
    /*///
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            imageViewFull.setScaleX(scaleFactor);
            imageViewFull.setScaleY(scaleFactor);
            return true;
        }
    }*/
}

