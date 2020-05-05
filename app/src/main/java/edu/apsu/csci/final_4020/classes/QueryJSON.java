package edu.apsu.csci.final_4020.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class QueryJSON extends AsyncTask<Void,Void,Question> {

    //Interface to implement a callback to get data from thread to MainUIThread
    public interface JSONCallBack {
        void onResponse(Question q);
    }

    private JSONCallBack callback;
    private static final String API_URL = "https://jservice.io/api/random";

    int pos;
    Context context;

    public QueryJSON(Context context, String difficulty){
        this.context = context;
    }

    public void setCallback(JSONCallBack callback) {
        this.callback = callback;
    }

    @Override
    protected Question doInBackground(Void... voids) {
        Question question;

        try {
            // Establish the connection
            URL url = new URL(API_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            Log.i("RESPONSE", API_URL);

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            // Build the raw string of the returned JSON
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }

            // Removes the [ ] on the front and back of the raw string
            jsonData.deleteCharAt(0).deleteCharAt(jsonData.length() - 1);
            // Build the raw string into a JSONObject that we can work with
            JSONObject reader = new JSONObject(jsonData.toString());

            // Category info is a nested JSONObject within the main JSONObject
            JSONObject categories = reader.getJSONObject("category");

            // Build our Question object to return back
            question = new Question(reader.getInt("value"), reader.getString("question"), categories.getString("title"),
                    reader.getString("answer"), reader.getInt("id"));

            connection.disconnect();
            return question;
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    // Call our callback function after the thread is done executing
    @Override
    protected void onPostExecute(Question question) {
        callback.onResponse(question);
    }
}
