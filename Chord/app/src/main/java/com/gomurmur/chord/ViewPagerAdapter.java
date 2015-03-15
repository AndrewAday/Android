package com.gomurmur.chord;

/**
 * Created by andrewaday on 3/2/15.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    // Tab Titles
    private String tabtitles[] = new String[] { "Spotify", "Pandora", "SoundCloud" };
    Context context;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                SpotifyFragment spotify = new SpotifyFragment();
                return spotify;

            // Open FragmentTab2.java
            case 1:
                Pandora pandora = new Pandora();
                return pandora;

            // Open FragmentTab3.java
            case 2:
                SoundCloud soundcloud = new SoundCloud();
                return soundcloud;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
