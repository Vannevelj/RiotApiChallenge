package com.guesstheurf.guesstheurf.activities.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.guesstheurf.guesstheurf.R;
import com.guesstheurf.guesstheurf.activities.adapters.ChampionAdapter;
import com.guesstheurf.guesstheurf.models.Session;
import com.guesstheurf.guesstheurf.models.riot.Game;
import com.guesstheurf.guesstheurf.models.riot.Participant;

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

        ListView leftTeamList = (ListView) layout.findViewById(R.id.leftTeam);
        ArrayAdapter<Participant> leftTeamAdapter = new ChampionAdapter(this.getActivity(), R.layout.champion_info, leftTeam);
        leftTeamList.setAdapter(leftTeamAdapter);

//        ListView rightTeamList = (ListView) getActivity().findViewById(R.id.rightTeam);
//        ArrayAdapter<Participant> rightTeamAdapter = new ChampionAdapter(this.getActivity(), R.layout.champion_info, rightTeam);
//        rightTeamList.setAdapter(rightTeamAdapter);

        return layout;
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

}
