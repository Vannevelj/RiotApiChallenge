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

import com.guesstheurf.guesstheurf.R;
import com.guesstheurf.guesstheurf.activities.adapters.HighscoreAdapter;
import com.guesstheurf.guesstheurf.models.Highscore;
import com.guesstheurf.guesstheurf.models.HighscoreRequestParameters;
import com.guesstheurf.guesstheurf.models.Session;
import com.guesstheurf.guesstheurf.tasks.GetHighscoresTask;

import java.util.List;

public class HighscoreFragment extends android.support.v4.app.Fragment {
    private HighscoreAdapter highscoreAdapter;
    private ListView highscoreList;
    private OnFragmentInteractionListener mListener;


    public static HighscoreFragment newInstance() {
        return new HighscoreFragment();
    }

    public HighscoreFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        updateHighscores();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_highscore, container, false);
        addButtonListeners(layout);

        highscoreList = (ListView) layout.findViewById(R.id.highscore_list);
        highscoreAdapter = new HighscoreAdapter(getActivity(), R.layout.highscore_entry, Session.INSTANCE.getHighscores());
        highscoreList.setAdapter(highscoreAdapter);
        setListViewHeightBasedOnChildren(highscoreList);

        return layout;
    }

    //TODO: extract to helper method?
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

    private void addButtonListeners(View view) {
        Button refreshButton = (Button) view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHighscores();
            }
        });
    }

    private void updateHighscores() {
        Log.d("HIGHSCOREFRAGMENT", "Updating highscores");
        HighscoreRequestParameters parameters = new HighscoreRequestParameters(1, 10);
        GetHighscoresTask task = new GetHighscoresTask();
        task.execute(parameters);
        try {
            List<Highscore> highscores = task.get(); // TODO: ASYNCHONRINICINIKRAZF?A PLEASE
            Session.INSTANCE.setHighscores(highscores);
            setListViewHeightBasedOnChildren(highscoreList);

            getActivity().findViewById(R.id.highscoreFragment).invalidate();
            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.highscoreFragment, new HighscoreFragment());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Log.d("HIGHSCOREFRAGMENT", "finalized object with hashcode " + hashCode());
    }
}
