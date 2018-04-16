package com.example.sayandeep.quizquotient.Acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sayandeep.quizquotient.Helper.Constants;
import com.example.sayandeep.quizquotient.Objects.QuestionScore;
import com.example.sayandeep.quizquotient.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Done extends AppCompatActivity {
    TextView txtResultScore,getTxtResultQuestion;
    Button btnTryAgain;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference question_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        database=FirebaseDatabase.getInstance();
        question_score=database.getReference("Question_score");
        txtResultScore=findViewById(R.id.txtTotalScore);
        getTxtResultQuestion=findViewById(R.id.txtTotalQuestion);
        progressBar=findViewById(R.id.doneProgressBar);
        btnTryAgain=findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Done.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            int score=extra.getInt("Score");
            int totalQuestions=extra.getInt("Total");
            int correctAnswer=extra.getInt("Correct");
            txtResultScore.setText(String.format(Locale.getDefault(),"Score: %d ",score));
            getTxtResultQuestion.setText(String.format(Locale.getDefault(),"Passed : %d/%d",correctAnswer,totalQuestions));
            progressBar.setMax(totalQuestions);
            progressBar.setProgress(correctAnswer);
            question_score.child(String.format(Locale.getDefault(),"%s_%s", Constants.currentUser.getUserName(),Constants.categoryId))
                    .setValue(new QuestionScore(String.format(Locale.getDefault(),"%s_%s", Constants.currentUser.getUserName(),Constants.categoryId),Constants.currentUser.getUserName()
                    ,String.valueOf(score)));
        }
    }
}
