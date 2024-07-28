package com.example.lsf4;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class quizactivity extends AppCompatActivity {
    private JSONObject quizData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quizactivity);
        String quizDataString = getIntent().getStringExtra("quizData");
        try {
            quizData = new JSONObject(quizDataString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        displayQuiz();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void displayQuiz() {
        TextView questionView = findViewById(R.id.textView);
       /* TextView answerView = findViewById(R.id.textView2);
        TextView answerView2 = findViewById(R.id.textView3);*/
        LinearLayout optionsLayout = findViewById(R.id.optionsLayout);
        try {
            questionView.setText(quizData.getString("Question"));
            String[] options = quizData.getString("Options").split(",");
            for (String option : options) {
                Button optionButton = new Button(this);
                optionButton.setText(option);
                optionButton.setOnClickListener(v -> handleOptionSelected(optionButton,option));
                optionsLayout.addView(optionButton);
            } }    catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleOptionSelected(Button select,String selectedOption) {
        try {
            String correctAnswer = quizData.getString("Answer");
            if (selectedOption.equals(correctAnswer)) {
                select.setBackgroundColor(Color.GREEN);
                setResult(RESULT_OK);
            } else {
                select.setBackgroundColor(Color.RED);
                setResult(RESULT_OK);  // You can set different result codes for correct/incorrect if needed
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        select.postDelayed(() -> finish(), 1000);
    }
}