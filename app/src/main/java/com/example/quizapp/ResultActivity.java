package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.annotation.SuppressLint;


public class ResultActivity extends AppCompatActivity {

    TextView CongratsMessage;
    TextView ScoreValue;
    Button NewQuiz;
    Button Finish;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle information = getIntent().getExtras();
        String UsersName = information.getString("username");
        int playerScore = information.getInt("Score");

        CongratsMessage = findViewById(R.id.NameOutput);
        ScoreValue = findViewById(R.id.ScoreOutputText);
        NewQuiz = findViewById(R.id.NewQuizButton);
        Finish = findViewById(R.id.FinishButton);

        CongratsMessage.setText("Congratulations " + UsersName);
        ScoreValue.setText(playerScore + "/3");

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        NewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Restart = new Intent(ResultActivity.this, QuizActivity.class);
                Restart.putExtra("username", UsersName);
                startActivity(Restart);
            }
        });



    }
}