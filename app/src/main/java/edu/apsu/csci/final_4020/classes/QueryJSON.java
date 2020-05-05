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
    public interface JSONCallBack {
        void onResponse(Question q);
    }

    private JSONCallBack callback;
    private static final String API_URL = "https://jservice.io/api/random";
    int pos;
    Context context;

    public QueryJSON(Context context){
        this.context = context;
    }

    public void setCallback(JSONCallBack callback) {
        this.callback = callback;
    }


    @Override
    protected Question doInBackground(Void... voids) {
        Question question = new Question();
        try {
            // Establish the connection
            URL url = new URL(API_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            Log.i("RESPONSE", API_URL);

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }

            jsonData.deleteCharAt(0).deleteCharAt(jsonData.length() - 1);
            JSONObject reader = new JSONObject(jsonData.toString());
            question.setID(reader.getInt("id"));
            question.setQuestion(reader.getString("question"));
            question.setAnswer(reader.getString("answer"));
            question.setDifficulty(reader.getInt("value"));

            //TODO: Fix populating Category from JSON response

            JSONArray items = reader.getJSONArray("category");
            Log.i("RESPONSE", items.toString());
            String category;
            for(int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                category = item.getString("title");
                Log.i("RESPONSE", category);
                question.setCategory(category);
            }

            connection.disconnect();
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return question;
    }

    @Override
    protected void onPostExecute(Question question) {
        callback.onResponse(question);
    }
}
