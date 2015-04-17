package com.guesstheurf.guesstheurf.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guesstheurf.guesstheurf.R;
import com.guesstheurf.guesstheurf.models.Highscore;

import java.util.List;

public class HighscoreAdapter extends ArrayAdapter<Highscore> {
    private Activity activity;
    private List<Highscore> highscores;

    public HighscoreAdapter(Context context, int resource, List<Highscore> objects) {
        super(context, resource, objects);
        this.activity = (Activity) context;
        this.highscores = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Highscore current = highscores.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.highscore_entry, null);

        TextView username = (TextView) rowView.findViewById(R.id.highscore_username);
        username.setText(current.getUsername());

        TextView score = (TextView) rowView.findViewById(R.id.highscore_score);
        score.setText(String.valueOf(current.getScore()));

        return rowView;
    }
}
