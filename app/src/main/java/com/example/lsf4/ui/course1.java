package com.example.lsf4.ui;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.MediaController;

import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lsf4.quizactivity;
import com.example.lsf4.R;
import com.example.lsf4.ui.gallery.GalleryFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class course1 extends AppCompatActivity {
    private static String json_url = "https://run.mocky.io/v3/da7e1fef-2e93-4f7d-ae98-36a969fc7992";
    private JSONArray dataArray;
    private int currentIndex = 0;
    private static final int REQUEST_CODE_QUIZ = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course1);
        new FetchJsonDataTask().execute(json_url);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void Activity1 (View v){
        Intent ac1 = new Intent(this, GalleryFragment.class);
        startActivity(ac1);
    }
    public void Activity2 (View v){
        Intent ac2 = new Intent(this, course2.class);
        startActivity(ac2);
    }
    private class FetchJsonDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String jsonResponse = "";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                jsonResponse = builder.toString();
                inputStream.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }
        @Override
        protected void onPostExecute(String jsonResponse) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                dataArray = jsonObject.getJSONArray("elements");
                handleNextItem();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleNextItem() {
        if (currentIndex >= dataArray.length()) {
            return;
        }

        try {
            JSONObject item = dataArray.getJSONObject(currentIndex);
            String type = item.getString("type");
            if ("video".equalsIgnoreCase(type)) {
                playVideo(item.getString("url"));
            } else {
                showQuizActivity(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void playVideo(String videoUrl) {
        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse(videoUrl);
        videoView.setVideoURI(videoUri);
        videoView.setOnCompletionListener(mp -> {
            currentIndex++;
            handleNextItem();
        });
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mediaController);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
    private void showQuizActivity(JSONObject quizData) {
        Intent intent = new Intent(this, quizactivity.class);
        intent.putExtra("quizData", quizData.toString());
        startActivityForResult(intent,REQUEST_CODE_QUIZ);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ && resultCode == RESULT_OK) {
            currentIndex++;
            handleNextItem();
        }
    }
}