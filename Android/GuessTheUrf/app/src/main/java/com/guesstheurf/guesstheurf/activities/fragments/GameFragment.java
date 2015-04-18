package com.guesstheurf.guesstheurf.activities.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.guesstheurf.guesstheurf.R;
import com.guesstheurf.guesstheurf.activities.adapters.ChampionAdapter;
import com.guesstheurf.guesstheurf.models.Answer;
import com.guesstheurf.guesstheurf.models.Session;
import com.guesstheurf.guesstheurf.models.riot.Game;
import com.guesstheurf.guesstheurf.models.riot.Participant;
import com.guesstheurf.guesstheurf.tasks.SendScoreTask;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends android.support.v4.app.Fragment {
    private OnFragmentInteractionListener mListener;
    private Game currentGame;

    public static GameFragment newInstance(Game game) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putParcelable("currentGame", game);
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentGame = getArguments().getParcelable("currentGame");
        }

        // Get new game
        currentGame = Session.INSTANCE.getNewGame(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_game, container, false);

        List<Participant> leftTeam = new ArrayList<>();
        List<Participant> rightTeam = new ArrayList<>();
        for (Participant participant : currentGame.getParticipants()) {
            if (participant.getTeamId() == 100) {
                leftTeam.add(participant);
            } else {
                rightTeam.add(participant);
            }
        }

        addButtonListeners(layout);

        ListView leftTeamList = (ListView) layout.findViewById(R.id.leftTeam);
        ChampionAdapter leftTeamAdapter = new ChampionAdapter(getActivity(), R.layout.champion_info, leftTeam);
        leftTeamList.setAdapter(leftTeamAdapter);
        setListViewHeightBasedOnChildren(leftTeamList);

        ListView rightTeamList = (ListView) layout.findViewById(R.id.rightTeam);
        ChampionAdapter rightTeamAdapter = new ChampionAdapter(getActivity(), R.layout.champion_info, rightTeam);
        rightTeamList.setAdapter(rightTeamAdapter);
        setListViewHeightBasedOnChildren(rightTeamList);

        return layout;
    }

    private void addButtonListeners(View view) {
        Button leftWins = (Button) view.findViewById(R.id.leftTeamWinsButton);
        leftWins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWin(100);
            }
        });

        Button rightWins = (Button) view.findViewById(R.id.rightTeamWinsButton);
        rightWins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWin(200);
            }
        });
    }

    private void sendWin(int teamId) {
        int winningTeamId = currentGame.getTeams().get(0).isWinner() ? currentGame.getTeams().get(0).getTeamId() : currentGame.getTeams().get(1).getTeamId();
        if (teamId == winningTeamId) {
            Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "You suck!", Toast.LENGTH_SHORT).show();
        }

        Answer answer = new Answer(currentGame.getMatchId(), teamId);
        SendScoreTask task = new SendScoreTask();
        task.execute(answer);

        //Populate with new game
        getActivity().findViewById(R.id.gameFragment).invalidate();
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.gameFragment, new GameFragment());
        transaction.commit();
    }

    /*  Method for Setting the Height of the ListView dynamically.
        Hack to fix the issue of not showing all the items of the ListView
        when placed inside a ScrollView.
        http://stackoverflow.com/a/19311197/1864167
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void finalize() {
        Log.d("GAMEFRAGMENT", "finalized object with hashcode " + hashCode());
    }
}
