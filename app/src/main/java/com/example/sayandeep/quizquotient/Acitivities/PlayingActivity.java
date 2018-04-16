package com.example.sayandeep.quizquotient.Acitivities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sayandeep.quizquotient.Helper.Constants;

import com.example.sayandeep.quizquotient.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener{
    CountDownTimer mCountDown;
    int index=0,score=0,thisQuestion=0,totalQuestions,correctAnswer,progressValue=0;
   FirebaseDatabase database;
    DatabaseReference questions;
    ProgressBar progressBar;
    Button btnA,btnB,btnC,btnD;
    TextView question_text,txtScore,txtQuestionNum;
    ImageView question_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        database=FirebaseDatabase.getInstance();
       questions=database.getReference("Questions");
        progressBar=findViewById(R.id.progressBar);
        question_text=findViewById(R.id.question_text);
        txtQuestionNum=findViewById(R.id.txtQuestionNum);
        txtScore=findViewById(R.id.txtScore);
        question_image=findViewById(R.id.question_image);
        btnA=findViewById(R.id.btnAnswerA);
        btnB=findViewById(R.id.btnAnswerB);
        btnC=findViewById(R.id.btnAnswerC);
        btnD=findViewById(R.id.btnAnswerD);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }
    @Override
    public void onClick(View view)
    {
        mCountDown.cancel();
        if(index<totalQuestions)
        {
            Button clickedButton=(Button)view;
            if(clickedButton.getText().equals(Constants.questionsList.get(index).getCorrectAnswer()))
            {
                score+=10;
                correctAnswer++;
                showQuestion(++index);
            }
            else
            {
                Intent intent=new Intent(this, Done.class);
                Bundle dataSend=new Bundle();
                dataSend.putInt("Score",score);
                dataSend.putInt("Total",totalQuestions);
                dataSend.putInt("Correct",correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
            txtScore.setText(String.format("%d",score));
        }

    }

    private void showQuestion(int index) {
        if(index<totalQuestions)
        {
            thisQuestion++;
            txtQuestionNum.setText(String.format(Locale.getDefault(),"%d / %d",thisQuestion,totalQuestions) );
            progressBar.setProgress(0);
            progressValue=0;
            if(Constants.questionsList.get(index).getIsImageQuestion().equals("true"))
            {
                Picasso.with(getBaseContext()).load(Constants.questionsList.get(index).getQuestion()).into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            }
            else
            {
                question_text.setVisibility(View.VISIBLE);
                question_image.setVisibility(View.INVISIBLE);


            }
                btnA.setText(Constants.questionsList.get(index).getAnswerA());
                btnB.setText(Constants.questionsList.get(index).getAnswerB());
                btnC.setText(Constants.questionsList.get(index).getAnswerC());
                btnD.setText(Constants.questionsList.get(index).getAnswerD());
                mCountDown.start();

        }
        else
        {
            Intent intent=new Intent(this, Done.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("Score",score);
            dataSend.putInt("Total",totalQuestions);
            dataSend.putInt("Correct",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalQuestions=Constants.questionsList.size();
        mCountDown=new CountDownTimer(Constants.TIMEOUT,Constants.INTERVAL) {
            @Override
            public void onTick(long milliSec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
               mCountDown.cancel();
                showQuestion(++index);

            }
        };showQuestion(index);
    }
}
