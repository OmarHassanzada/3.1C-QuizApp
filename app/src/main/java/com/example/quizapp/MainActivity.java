package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;



public class MainActivity extends AppCompatActivity {

    EditText Name;
    Button StartButton;
    CheckBox NameCheck;
    SharedPreferences sharedPreferences;
    String USER_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Using findViewById for all the widgets that will be necessary in the xml.
        StartButton = findViewById(R.id.StartButton);
        Name = findViewById(R.id.PersonName);
        NameCheck = findViewById(R.id.checkBox);
        sharedPreferences = getSharedPreferences("com.example.quizapp", MODE_PRIVATE);
        checkSharedPreferences();

    }

    public void checkSharedPreferences() {
        String CheckIfName = sharedPreferences.getString(USER_NAME, "");
        Name.setText(CheckIfName);
    }

    public void StartQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("username", Name.getText().toString());
        startActivity(intent);

        if(NameCheck.isChecked())
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_NAME, Name.getText().toString());
            editor.apply();
        }
        else
        {
            sharedPreferences.edit().putString(USER_NAME, "").apply();
        }
    }
}