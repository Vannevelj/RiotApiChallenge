package com.guesstheurf.guesstheurf.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.guesstheurf.guesstheurf.R;
import com.guesstheurf.guesstheurf.models.riot.Participant;

import java.util.List;

public class ChampionAdapter extends ArrayAdapter<Participant> {
    private Activity activity;
    private List<Participant> players;

    public ChampionAdapter(Context context, int resource, List<Participant> objects) {
        super(context, resource, objects);
        this.activity = (Activity) context;
        this.players = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Participant current = players.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.champion_info, null);

        ImageView championImage = (ImageView) rowView.findViewById(R.id.championIcon);
        championImage.setImageResource(activity.getResources().getIdentifier("champion_" + current.getChampionId(), "drawable", activity.getPackageName()));

        ImageView firstSummonerSpell = (ImageView) rowView.findViewById(R.id.firstSummonerSpell);
        firstSummonerSpell.setImageResource(activity.getResources().getIdentifier("summonerspell_" + current.getSpell1Id(), "drawable", activity.getPackageName()));

        ImageView secondSummonerSpell = (ImageView) rowView.findViewById(R.id.secondSummonerSpell);
        secondSummonerSpell.setImageResource(activity.getResources().getIdentifier("summonerspell_" + current.getSpell2Id(), "drawable", activity.getPackageName()));

        return rowView;
    }
}
