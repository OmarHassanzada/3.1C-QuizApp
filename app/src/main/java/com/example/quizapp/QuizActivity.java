package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.w3c.dom.Text;
import java.util.Objects;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ProgressBar;
import javax.xml.transform.Result;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.widget.Toast;


public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    //=== Defining/instantiating all variables of the quiz ===//

    //These are the colours that will be used to define correct/incorrect answers
    private final int RED = 0xFFF44336;
    private final int Green = 0xFF4CAF50;
    private final int Grey = 0xFF2B2B2B;
    private final int DefaultColour = 0xFF673AB7;


    //Number of questions correct which is 0 in the beginning, number of question is 3, current question in the beginning is 1
    private int NoOfCorrectAns = 0; //will be used with the progress bar
    private final int QuestionCount = 3;
    private int CurrentQuestionNo = 1;

    //This bool is set to false by default unless the answer is true, will be used later on
    private boolean FinalAnswerValue = false;

    //Will use to check the answers
    private boolean AnswerSelected1, AnswerSelected2;


    String[] Questions = {
            "What is Spider-Mans Name?",
            "Is Spider-Man a Hero or Villain?",
            "Is Spider-Man part of Marvel or DC?"
    };

    String[][]  PossibleAnswers = {
            {"Peter Porker", "Peter Parker"},
            {"Hero", "Villain"},
            {"Marvel", "DC"}
    };

    //this will be used in the if statement to compare
    String [] ActualAnswer = {
            "Peter Parker",
            "Hero",
            "Marvel"
    };



    //Defining variables
    TextView Question;
    TextView Name;
    TextView QuestionCountString;
    ProgressBar QuestionProgressBar;
    Button AnswerButton1;
    Button AnswerButton2;
    Button Submit;

    //These strings used to determine answers in the final if statement
    private String SelectedAnswer;
    private String CorrectAnswer;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        String UserName = intent.getStringExtra("username");

        Question = findViewById(R.id.QuestionText);
        Name = findViewById(R.id.NameText);
        QuestionCountString = findViewById(R.id.QuestionProgressText);
        QuestionProgressBar = findViewById(R.id.ProgressBar);;
        AnswerButton1 = findViewById(R.id.button);
        AnswerButton2 = findViewById(R.id.button2);
        Submit = findViewById(R.id.SubmitButton);

        //Gets users name
        Name.setText(UserName);


        Question.setText(Questions[CurrentQuestionNo-1]);
        QuestionProgressBar.setProgress((CurrentQuestionNo));
        QuestionCountString.setText(CurrentQuestionNo + "/" + QuestionCount);
        AnswerButton1.setText(PossibleAnswers[CurrentQuestionNo-1][0]);
        AnswerButton2.setText(PossibleAnswers[CurrentQuestionNo-1][1]);
        CorrectAnswer = ActualAnswer[CurrentQuestionNo-1];


        AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(DefaultColour));
        AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(DefaultColour));

        //these two buttons functions listen to when they are clicked
        // and change the colours and set the boolean values to true/false
        AnswerButton1.setOnClickListener(v -> {
            SelectedAnswer = AnswerButton1.getText().toString();
            //resets colour of other buttons, and sets current button to the selected colour type
            AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(Grey));
            AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(DefaultColour));
            //sets boolean values correct for selecting this answer
            AnswerSelected1 = true;
            AnswerSelected2 = false;
        });

        AnswerButton2.setOnClickListener(v -> {
            SelectedAnswer = AnswerButton2.getText().toString();
            //resets colour of other buttons, and sets current button to the selected colour type
            AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(Grey));
            AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(DefaultColour));
            //sets boolean values correct for selecting this answer
            AnswerSelected1 = false;
            AnswerSelected2 = true;
        });

        //when the submit button is clicked the following methods will be used
        Submit.setOnClickListener(v -> {
            if(FinalAnswerValue){
                //this if-block only occurs at the end of the quiz
                if (CurrentQuestionNo == QuestionCount){
                    Intent ResultPage = new Intent(QuizActivity.this, ResultActivity.class);
                    ResultPage.putExtra("Score", NoOfCorrectAns);
                    ResultPage.putExtra("username", UserName);
                    startActivity(ResultPage);
                    finish();
                }
                //in this else-block it resets all values for the next questions
                else
                {
                    FinalAnswerValue = false;
                    AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(DefaultColour));
                    AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(DefaultColour));
                    AnswerSelected1 = false;
                    AnswerSelected2 = false;
                    SelectedAnswer = "";
                    Submit.setText("Submit Answer");
                    //begins changing values for next question
                    CurrentQuestionNo++;
                    QuestionProgressBar.setProgress(CurrentQuestionNo);
                    QuestionCountString.setText(CurrentQuestionNo + "/" + QuestionCount);
                    //takes the next value from each of the arrays of questions and answers
                    Question.setText(Questions[CurrentQuestionNo-1]);
                    QuestionProgressBar.setProgress((CurrentQuestionNo));
                    AnswerButton1.setText(PossibleAnswers[CurrentQuestionNo-1][0]);
                    AnswerButton2.setText(PossibleAnswers[CurrentQuestionNo-1][1]);
                    CorrectAnswer = ActualAnswer[CurrentQuestionNo-1];
                }
            }
            //this else exist in the case a value is not pressed and a warning toast is presented
            else if(!AnswerSelected1 && !AnswerSelected2){
                Toast.makeText(QuizActivity.this, "Select Answer please", Toast.LENGTH_SHORT).show();
            }
            //this else-if block is when the answer is correct
            else if (Objects.equals(SelectedAnswer, CorrectAnswer)){
                Toast.makeText(QuizActivity.this, "Congrats Answers correct", Toast.LENGTH_SHORT).show();
                FinalAnswerValue = true;
                Submit.setText("Next Question");
                //tracks amount of correct answers
                NoOfCorrectAns++;
                //sets correct answer to green
                if (AnswerSelected1){
                    AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(Green));
                }
                if (AnswerSelected2){
                    AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(Green));
                }
            }
            //and the final else is if the the wrong answer is selected
            else{
                Toast.makeText(QuizActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                FinalAnswerValue = true;
                Submit.setText("Next Question");
                //sets correct answer to green
                if (AnswerSelected1){
                    AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(RED));
                }
                if (AnswerSelected2){
                    AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(RED));
                }
                if (Objects.equals(CorrectAnswer, AnswerButton1.getText().toString())){
                    AnswerButton1.setBackgroundTintList(ColorStateList.valueOf(Green));
                }
                if (Objects.equals(CorrectAnswer, AnswerButton2.getText().toString())){
                    AnswerButton2.setBackgroundTintList(ColorStateList.valueOf(Green));
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}