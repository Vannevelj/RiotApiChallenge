package com.guesstheurf.guesstheurf.activities.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.guesstheurf.guesstheurf.activities.fragments.GameFragment;
import com.guesstheurf.guesstheurf.activities.fragments.HighscoreFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private GameFragment gameFragment = GameFragment.newInstance();
    private HighscoreFragment highscoreFragment = HighscoreFragment.newInstance();

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return gameFragment;
        } else if (position == 1) {
            return highscoreFragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Game";
        } else if (position == 1) {
            return "Highscores";
        } else {
            return null;
        }
    }
}
