package com.moomen.movieyou.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.moomen.movieyou.R;
import com.moomen.movieyou.db.RateDB;

public class AppRater {
    private final static String APP_PNAME = "com.moomen.movieyou";

    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;

    public static void app_launched(Context mContext) {

        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {

        //Deffin dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View filterDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_rate_app, null);
        builder.setView(filterDialogView);
        AlertDialog alertDialog = builder.create();
        RateDB rateDB = new RateDB(mContext);
        Button rateButton = filterDialogView.findViewById(R.id.rate_app_now_id);
        rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                rateDB.writeReatedStatuse("true", "", "");
                alertDialog.dismiss();
            }
        });
        Button remindLaterButton = filterDialogView.findViewById(R.id.remind_me_later_id);
        remindLaterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rateDB.writeReatedStatuse("", "true", "");
                alertDialog.dismiss();
            }
        });
        Button noThanksButton = filterDialogView.findViewById(R.id.no_thanks_id);
        noThanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDB.writeReatedStatuse("", "", "true");
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
