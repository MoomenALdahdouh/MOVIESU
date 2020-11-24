package com.moomen.movieyou.ad;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.NativeExpressAdView;

public class UnifiedNativeAdHolder extends RecyclerView.ViewHolder {

    private NativeExpressAdView nativeExpressAdView;

    public UnifiedNativeAdHolder(NativeExpressAdView nativeExpressAdView) {
        super(nativeExpressAdView);
        this.nativeExpressAdView = nativeExpressAdView;
    }
}
