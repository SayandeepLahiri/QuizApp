package com.example.sayandeep.quizquotient.Acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sayandeep.quizquotient.Helper.Constants;
import com.example.sayandeep.quizquotient.Objects.Questions;
import com.example.sayandeep.quizquotient.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class StartActivity extends AppCompatActivity {
    Button btnPlay;
    FirebaseDatabase database;
    DatabaseReference questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnPlay=findViewById(R.id.btnPlay);
        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");
        loadQuestion(Constants.categoryId);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,PlayingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(String categoryId) {
        if(Constants.questionsList.size()>0)
            Constants.questionsList.clear();
        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Questions ques=postSnapshot.getValue(Questions.class);
                            Constants.questionsList.add(ques);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Collections.shuffle(Constants.questionsList);
    }

}
