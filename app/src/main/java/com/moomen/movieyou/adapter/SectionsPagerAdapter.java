package com.moomen.movieyou.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moomen.movieyou.R;
import com.moomen.movieyou.ui.fragment.AnimeFragment;
import com.moomen.movieyou.ui.fragment.HomeFragment;
import com.moomen.movieyou.ui.fragment.LiveFragment;
import com.moomen.movieyou.ui.fragment.MoviesFragment;
import com.moomen.movieyou.ui.fragment.MusicFragment;
import com.moomen.movieyou.ui.fragment.StremTvFragment;
import com.moomen.movieyou.ui.fragment.WatchListFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.home,
            R.string.live,
            R.string.movies,
            R.string.streaming_and_tv,
            R.string.animation,
            R.string.watch_list,
            R.string.music};

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    //TODO: Active comment switch in the next version
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new LiveFragment();
                break;
            case 2:
                fragment = new MoviesFragment();
                break;
            case 3:
                fragment = new StremTvFragment();
                break;
            case 4:
                fragment = new AnimeFragment();
                break;
            case 5:
                fragment = new WatchListFragment();
                break;
            case 6:
                fragment = new MusicFragment();
                break;


        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 6 total pages.
        return 7;
    }
}