package com.guesstheurf.guesstheurf.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guesstheurf.guesstheurf.models.AccessToken;
import com.guesstheurf.guesstheurf.models.LoginInfo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class LoginTask extends AsyncTask<LoginInfo, Void, AccessToken> {
    private Context context;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected AccessToken doInBackground(LoginInfo... params) {
        LoginInfo loginInfo = params[0];
        try {
            HttpResponse response = Request.Post("http://guesstheurf.azurewebsites.net/token")
                    .bodyForm(Form.form().add("username", loginInfo.getUsername())
                            .add("password", loginInfo.getPassword())
                            .add("grant_type", "password")
                            .add("client_id", "default_web")
                            .build())
                    .execute()
                    .returnResponse();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                return new ObjectMapper().readValue(content, AccessToken.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AccessToken accessToken) {
        if (accessToken == null) {
            Toast.makeText(context, "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Happy to see you again, " + accessToken.getUsername(), Toast.LENGTH_LONG).show();
        }
    }
}
