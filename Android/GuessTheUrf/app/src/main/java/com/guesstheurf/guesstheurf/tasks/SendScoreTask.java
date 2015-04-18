package com.guesstheurf.guesstheurf.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.guesstheurf.guesstheurf.models.Answer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class SendScoreTask extends AsyncTask<Answer, Void, Void> {
    @Override
    protected Void doInBackground(Answer... params) {
        Answer answer = params[0];
        Log.d("SENDSCORETASK", String.format("MatchId:%n\tWinningTeamId:%n", answer.getMatchId(), answer.getWinningTeamId()));

        try {
            HttpResponse response = Request.Post("http://guesstheurf.azurewebsites.net/api/games")
                    .bodyForm(Form.form()
                            .add("matchid", answer.getMatchId())
                            .add("winningteamid", String.valueOf(answer.getWinningTeamId()))
                            .build())
                    .execute()
                    .returnResponse();

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                // Something went wrong -- Don't bother informing user
                // User will be at the next answer anyway
                // Assume user is at fault
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
