package com.guesstheurf.guesstheurf.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guesstheurf.guesstheurf.models.Highscore;
import com.guesstheurf.guesstheurf.models.HighscoreRequestParameters;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetHighscoresTask extends AsyncTask<HighscoreRequestParameters, Void, List<Highscore>> {
    @Override
    protected List<Highscore> doInBackground(HighscoreRequestParameters... params) {
        HighscoreRequestParameters parameters = params[0];
        String url = String.format("http://guesstheurf.azurewebsites.net/api/games/highscores?page=%d&pagesize=%d", parameters.getPage(), parameters.getPageSize());

        try {
            HttpResponse response = Request.Get(url)
                    .execute()
                    .returnResponse();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Highscore.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
