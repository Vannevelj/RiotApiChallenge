package com.guesstheurf.guesstheurf.activities;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.guesstheurf.guesstheurf.R;
import com.guesstheurf.guesstheurf.activities.adapters.MyPagerAdapter;
import com.guesstheurf.guesstheurf.activities.fragments.GameFragment;
import com.guesstheurf.guesstheurf.activities.fragments.HighscoreFragment;

public class MainActivity extends FragmentActivity implements HighscoreFragment.OnFragmentInteractionListener, GameFragment.OnFragmentInteractionListener {
    MyPagerAdapter myPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
