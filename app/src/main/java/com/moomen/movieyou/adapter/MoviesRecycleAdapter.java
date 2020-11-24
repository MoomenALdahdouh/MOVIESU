package com.moomen.movieyou.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.moomen.movieyou.R;
import com.moomen.movieyou.db.WatchListDB;
import com.moomen.movieyou.model.db.WatchListModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.ui.MainActivity;
import com.moomen.movieyou.ui.OverviewMovie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MoviesRecycleAdapter extends RecyclerView.Adapter<MoviesRecycleAdapter.ViewHolder> implements Filterable {
    public static final String ID_MOVIE = "ID_MOVIE";
    public static final String TYPE = "TYPE";
    private final static String APP_PNAME = "com.moomen.movieyou";
    private String type = "movie";
    private String idMovie = "2";
    private String idMedia = "2";
    private String mediaName = "asdfgdsa45451";
    private String mediaType = "movie";
    private ArrayList<MoviesResults> moviesArrayList = new ArrayList<>();
    private ArrayList<MoviesResults> moviesArrayListFull = new ArrayList<>();

    private Context context;
    private boolean changeWidthLayoutMovieItem = false;
    private View viewMovie;
    private int layoutId = R.layout.movie_item;
    private Intent intent;

    private ArrayList<WatchListModel> watchListModelWatchListArrayList;
    private WatchListDB watchListDB;
    private WatchListModel watchListModel;

    private boolean isWatchListFragment = false;
    private int positionAd;
    private boolean isHorizontalRecycle = false;
    private Filter moviesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MoviesResults> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0)
                filteredList.addAll((moviesArrayListFull));
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (MoviesResults someMovies : moviesArrayListFull)
                    if (someMovies.getName().toLowerCase().startsWith(filterPattern))
                        filteredList.add(someMovies);
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesArrayList.clear();
            moviesArrayList.addAll((ArrayList<MoviesResults>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public MoviesRecycleAdapter(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(new MainActivity.MyUncaughtExceptionHandler());
    }

    @NonNull
    @Override
    public MoviesRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewMovie = layoutInflater.inflate(layoutId, parent, false);
        if (!changeWidthLayoutMovieItem) {
            ConstraintLayout constraintLayout = viewMovie.findViewById(R.id.constraint_id);
            ViewGroup.LayoutParams layoutParams = constraintLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            constraintLayout.setLayoutParams(layoutParams);
        }
        return new ViewHolder(viewMovie);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesRecycleAdapter.ViewHolder holder, int position) {
        MoviesResults currentMovie = moviesArrayList.get(position);
        final ProgressBar progressBar = viewMovie.findViewById(R.id.progress_bar_movie_item_id);
        //Animation animation = AnimationUtils.loadAnimation(context, R.anim.shake);
        //holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));

        try {
            String posterPath = currentMovie.getPoster_path();
            ConstraintLayout constraintLayout = viewMovie.findViewById(R.id.constraint_id);
            ViewGroup.LayoutParams layoutParams = constraintLayout.getLayoutParams();
            int w = layoutParams.width;
            int h = layoutParams.height;
            constraintLayout.setLayoutParams(layoutParams);
            Picasso.get()
                    .load(posterPath)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    //.networkPolicy(NetworkPolicy.NO_CACHE)
                    //.resize(160,250)
                    .into(holder.posterMovie, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("e", e.getMessage());
                        }
                    });
            String titleMoviesOrTv = currentMovie.getTitle();
            String dateReleaseMoviesOrTv = currentMovie.getRelease_date();
            String mediaTypeToGetFromWatchList = "movie";
            holder.typeMedia.setText(R.string.movie_type);
            if (titleMoviesOrTv == null) {
                titleMoviesOrTv = currentMovie.getName();
                mediaTypeToGetFromWatchList = "tv";
                holder.typeMedia.setText(R.string.tv_type);
            }
            if (dateReleaseMoviesOrTv == null) {
                dateReleaseMoviesOrTv = currentMovie.getFirst_air_date();
                mediaTypeToGetFromWatchList = "tv";
            }
            holder.titleMovie.setText(titleMoviesOrTv);
            String vote = String.valueOf(currentMovie.getVote_average());
            if (vote.equals("10.0"))
                vote = "10";
            holder.rateMovie.setText(vote);
            holder.starImageRateMovie.setImageResource(R.drawable.ic_star_black_24dp);
            holder.releaseDateMovie.setText(dateReleaseMoviesOrTv);
            //Checked Watch list later
            //Get all movies from watch list and add all on array watchListModelWatchListArrayList
            watchListDB = new WatchListDB(context);
            String id = String.valueOf(currentMovie.getId());
            watchListModelWatchListArrayList = new ArrayList<>(watchListDB.getMovieFromWatchList(id, mediaTypeToGetFromWatchList));
            //Check if exist or not if exist set image checked else image not checked
            if (!watchListModelWatchListArrayList.isEmpty())
                holder.addMovieToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
            else
                holder.addMovieToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_24);

            //Gone image
            if (isWatchListFragment)
                holder.addMovieToWatchList.setVisibility(View.GONE);

            //Share current movie when click on image share
            String sharTitleMoviesOrTv = titleMoviesOrTv;
            holder.shareMovie.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @SuppressLint({"SetWorldReadable", "WrongConstant", "ShowToast"})
                @Override
                public void onClick(View v) {
                    Drawable drawable = holder.posterMovie.getDrawable(); //posterMovie: it's image view you will be sharedG
                    if (drawable instanceof BitmapDrawable) {
                        ((BitmapDrawable) drawable).getBitmap();
                    }
                    try {
                        File file = new File(context.getExternalCacheDir(), File.separator + sharTitleMoviesOrTv + ".jpg");
                        FileOutputStream fOut = new FileOutputStream(file);
                        int width = drawable.getIntrinsicWidth();
                        width = width > 0 ? width : 1;
                        int height = drawable.getIntrinsicHeight();
                        height = height > 0 ? height : 1;
                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawable.draw(canvas);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        file.setReadable(true, false);
                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        //intent image
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri photoURI = FileProvider.getUriForFile(context, com.moomen.movieyou.BuildConfig.APPLICATION_ID + ".provider", file);
                        intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                        //intent text
                        String appURL = "https://play.google.com/store/apps/details?id=" + com.moomen.movieyou.BuildConfig.APPLICATION_ID;
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_TEXT, appURL + "\n Name: " + sharTitleMoviesOrTv);
                        intent.setType("image/jpg");
                        //intent.setType("text/plain");
                        context.startActivity(Intent.createChooser(intent, "Share image via"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    holder.linearLayoutInclude.setVisibility(View.GONE);
                    Toast.makeText(context, R.string.share_movie, Toast.LENGTH_SHORT);

                }
            });
            //Share current movie when click on linear layout share
            holder.linearLayoutShareMovie.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @SuppressLint({"SetWorldReadable", "WrongConstant", "ShowToast"})
                @Override
                public void onClick(View v) {
                    Drawable drawable = holder.posterMovie.getDrawable(); //posterMovie is image view you will be shared
                    if (drawable instanceof BitmapDrawable) {
                        ((BitmapDrawable) drawable).getBitmap();
                    }
                    try {
                        File file = new File(context.getExternalCacheDir(), File.separator + sharTitleMoviesOrTv + ".jpg");
                        FileOutputStream fOut = new FileOutputStream(file);
                        int width = drawable.getIntrinsicWidth();
                        width = width > 0 ? width : 1;
                        int height = drawable.getIntrinsicHeight();
                        height = height > 0 ? height : 1;
                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawable.draw(canvas);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        file.setReadable(true, false);
                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        //intent image
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri photoURI = FileProvider.getUriForFile(context, com.moomen.movieyou.BuildConfig.APPLICATION_ID + ".provider", file);
                        intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                        //intent text
                        String appURL = "https://play.google.com/store/apps/details?id=" + com.moomen.movieyou.BuildConfig.APPLICATION_ID;
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, appURL + "\n Name: " + sharTitleMoviesOrTv);
                        intent.setType("image/jpg");
                        intent.setType("text/plain");
                        context.startActivity(Intent.createChooser(intent, "Share image via"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    holder.linearLayoutInclude.setVisibility(View.GONE);
                    Toast.makeText(context, R.string.share_movie, Toast.LENGTH_SHORT);
                }
            });
        } catch (NullPointerException e) {
            Log.d("e", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public void setList(ArrayList<MoviesResults> moviesArrayList) {
        this.moviesArrayList = moviesArrayList;
        moviesArrayListFull = new ArrayList<>(moviesArrayList);
        notifyDataSetChanged();
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setChangeWidthLayoutMovieItem(boolean changeWidthLayoutMovieItem) {
        this.changeWidthLayoutMovieItem = changeWidthLayoutMovieItem;
    }

    public void setPositionAd(int positionAd) {
        this.positionAd = positionAd;
    }

    public void setIsHorizontalRecycle(boolean horizontalRecycle) {
        isHorizontalRecycle = horizontalRecycle;
    }

    /**
     * Filter recycle view for search
     **/
    @Override
    public Filter getFilter() {
        return moviesFilter;
    }

    public void setWatchListFragment(boolean watchListFragment) {
        isWatchListFragment = watchListFragment;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeMedia;
        ImageView posterMovie;
        TextView titleMovie;
        TextView rateMovie;
        ImageView starImageRateMovie;
        TextView releaseDateMovie;
        CardView cardView;
        ImageView shareMovie;
        ImageView addMovieToWatchList;
        ImageView copyMovieName;
        ImageView setting;
        LinearLayout linearLayoutCopyName;
        LinearLayout linearLayoutAddMovieToWatch;
        LinearLayout linearLayoutShareMovie;
        LinearLayout linearLayoutInclude;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeMedia = itemView.findViewById(R.id.textView_media_type_id);
            posterMovie = itemView.findViewById(R.id.poster_movie_imageView_id);
            titleMovie = itemView.findViewById(R.id.movie_title_textView_id);
            rateMovie = itemView.findViewById(R.id.rate_movie_textView_id);
            starImageRateMovie = itemView.findViewById(R.id.imageView_rate_movie_imageView_id);
            releaseDateMovie = itemView.findViewById(R.id.release_data_movie_textView_id);
            cardView = itemView.findViewById(R.id.card_view_movie_item_id);
            shareMovie = itemView.findViewById(R.id.imageView_share_movie_id);
            addMovieToWatchList = itemView.findViewById(R.id.imageView_add_movie_to_watch_list_id);
            copyMovieName = itemView.findViewById(R.id.imageView_copy_name_movie_id);

            setting = itemView.findViewById(R.id.image_setting_watch_list_id);
            linearLayoutInclude = itemView.findViewById(R.id.include_dialog_setting_id);
            linearLayoutInclude.setVisibility(View.GONE);
            linearLayoutCopyName = itemView.findViewById(R.id.linearLayout_copy_name_movie_id);
            linearLayoutShareMovie = itemView.findViewById(R.id.linearLayout_share_movie_id);
            linearLayoutAddMovieToWatch = itemView.findViewById(R.id.linearLayout_add_movie_to_watch_list_id);
            //on clicked on an movie or tv
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition > 20 && !isHorizontalRecycle)
                        adapterPosition = adapterPosition - positionAd;
                    else if (adapterPosition > 10 && isHorizontalRecycle)
                        adapterPosition = adapterPosition - positionAd;
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        MoviesResults moviesResults = moviesArrayList.get(adapterPosition);
                        String title = moviesResults.getTitle();
                        String date = moviesResults.getRelease_date();
                        if (title == null && date == null)
                            type = "tv";
                        intent = new Intent(context, OverviewMovie.class);
                        intent.putExtra(TYPE, type);
                        idMovie = String.valueOf(moviesResults.getId());
                        intent.putExtra(ID_MOVIE, idMovie);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
            //Add watch list on click and check if exist
            addMovieToWatchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListDB = new WatchListDB(context);
                    watchListModel = new WatchListModel();
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        MoviesResults moviesResults = moviesArrayList.get(adapterPosition);
                        idMedia = String.valueOf(moviesResults.getId());
                        mediaName = moviesResults.getTitle();
                        mediaType = "movie";
                        if (mediaName == null) {
                            mediaName = moviesResults.getName();
                            mediaType = "tv";
                        }
                        watchListModelWatchListArrayList = new ArrayList<>(watchListDB.getMovieFromWatchList(idMedia, mediaType));
                        //Check if exist
                        if (!watchListModelWatchListArrayList.isEmpty()) {
                            String isCheck = watchListModelWatchListArrayList.get(0).getIsChecked();
                            //If exist check if true
                            if (isCheck.equals("true")) {
                                watchListDB.deleteMovieFromWatchList(mediaType + idMedia);
                                addMovieToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_24);
                                Toast.makeText(context, R.string.remove_from_list, Toast.LENGTH_SHORT).show();
                                if (isWatchListFragment)
                                    moviesArrayList.remove(adapterPosition);
                                notifyDataSetChanged();
                            }
                        } else {
                            int size = (watchListDB.getSize() / 10) + 1; //select page num for next item
                            watchListDB.addMovieTOWatchList(size, idMedia, "true", mediaName, mediaType);
                            Toast.makeText(context, R.string.add_to_watch_list, Toast.LENGTH_SHORT).show();
                            addMovieToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                        }
                    }
                    linearLayoutInclude.setVisibility(View.GONE);
                }
            });
            //Add watch list on click and check if exist
            linearLayoutAddMovieToWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListDB = new WatchListDB(context);
                    watchListModel = new WatchListModel();
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        MoviesResults moviesResults = moviesArrayList.get(adapterPosition);
                        idMedia = String.valueOf(moviesResults.getId());
                        mediaName = moviesResults.getTitle();
                        mediaType = "movie";
                        if (mediaName == null) {
                            mediaName = moviesResults.getName();
                            mediaType = "tv";
                        }
                        watchListModelWatchListArrayList = new ArrayList<>(watchListDB.getMovieFromWatchList(idMedia, mediaType));
                        //Check if exist
                        if (!watchListModelWatchListArrayList.isEmpty()) {
                            String isCheck = watchListModelWatchListArrayList.get(0).getIsChecked();
                            //If exist check if true
                            if (isCheck.equals("true")) {
                                watchListDB.deleteMovieFromWatchList(mediaType + idMedia);
                                addMovieToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_24);
                                Toast.makeText(context, R.string.remove_from_list, Toast.LENGTH_SHORT).show();
                                moviesArrayList.remove(adapterPosition);
                                notifyDataSetChanged();
                            }
                        } else {
                            int size = (watchListDB.getSize() / 10) + 1; //select page num for next item
                            watchListDB.addMovieTOWatchList(size, idMedia, "true", mediaName, mediaType);
                            Toast.makeText(context, R.string.add_to_watch_list, Toast.LENGTH_SHORT).show();
                            addMovieToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                        }
                    }
                    linearLayoutInclude.setVisibility(View.GONE);
                }
            });

            //On click copy image to copy movie name
            copyMovieName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("TextView", titleMovie.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    clipData.getDescription();
                    Toast.makeText(context, R.string.copy_movie_name, Toast.LENGTH_SHORT).show();
                    copyMovieName.setImageResource(R.drawable.ic_baseline_file_copy_24);
                    linearLayoutInclude.setVisibility(View.GONE);
                }
            });
            //On click copy liner to copy movie name
            linearLayoutCopyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("TextView", titleMovie.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    clipData.getDescription();
                    Toast.makeText(context, R.string.copy_movie_name, Toast.LENGTH_SHORT).show();
                    copyMovieName.setImageResource(R.drawable.ic_baseline_file_copy_24);
                    linearLayoutInclude.setVisibility(View.GONE);
                }
            });

            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linearLayoutInclude.getVisibility() == View.GONE)
                        linearLayoutInclude.setVisibility(View.VISIBLE);
                    else
                        linearLayoutInclude.setVisibility(View.GONE);
                }
            });
        }
    }
}
