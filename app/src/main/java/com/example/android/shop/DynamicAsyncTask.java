package com.example.android.shop;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.android.shop.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class DynamicAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    IParser iParser;
    List<Questions> questionsList = new ArrayList<>();
    ProgressDialog progressDialog= null;


    public DynamicAsyncTask(Context context, IParser iParser) {
        this.iParser= iParser;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (iParser!=null)
        {
            iParser.onPreExecute();
        }
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(" http://159.65.93.198:3001/api/random_questions_list/1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            bufferedReader.close();
            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("questions");

            JSONObject objectQuestions;
            JSONArray jsonAnswerArray;

            for (int i = 0; i < jsonArray.length(); i++) {
                objectQuestions = jsonArray.getJSONObject(i);
                Questions questions = new Questions();
                questions.setQuestionDetail(objectQuestions.getString("detail"));
                questions.setQuestionImage(objectQuestions.getString("image"));

                jsonAnswerArray = objectQuestions.getJSONArray("answers");

                List<Questions.Answers> answersList = new ArrayList<>();
                for (int j = 0; j < jsonAnswerArray.length(); j++) {
                    JSONObject answerObject = jsonAnswerArray.getJSONObject(j);

                    Questions.Answers answersModel = new Questions.Answers();

                    answersModel.setAnswer(answerObject.getString("answer"));
                    answersModel.setCorrectAnswer(answerObject.getString("correct"));
                    answersModel.setAnsweredImage(answerObject.getString("image"));
                    Log.i("Correct: ", String.valueOf(answerObject.getString("correct")));
                    answersList.add(answersModel);
                }
                questions.setAnswersList(answersList);

                questionsList.add(questions);


            }
            if (iParser!=null)
            {
                iParser.onPostExecute(questionsList);
            }
            else{
                Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
