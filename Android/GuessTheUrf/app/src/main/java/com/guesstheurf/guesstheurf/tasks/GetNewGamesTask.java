package com.guesstheurf.guesstheurf.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guesstheurf.guesstheurf.models.riot.Game;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetNewGamesTask extends AsyncTask<Void, Void, List<Game>> {
    private Context context;

    public GetNewGamesTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Game> doInBackground(Void... params) {
        try {
            HttpResponse response = Request.Get("http://guesstheurf.azurewebsites.net/api/games")
                    .execute()
                    .returnResponse();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Game.class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Game> games) {
        if (games == null) {
            Toast.makeText(context, "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();
        }
    }
}
