package com.example.sayandeep.quizquotient.Acitivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sayandeep.quizquotient.Helper.HashMaker;
import com.example.sayandeep.quizquotient.Helper.Message;
import com.example.sayandeep.quizquotient.R;
import com.example.sayandeep.quizquotient.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtnewPassword, edtnewUsername, edtPassword, edtuserName, edtPhone;
    Button signUp, signIn;
    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtPassword = findViewById(R.id.edtPassword);
        edtuserName = findViewById(R.id.edtUserName);
        signIn = findViewById(R.id.btn_sign_in);
        signUp = findViewById(R.id.btn_sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtuserName.getText().toString(),
                        HashMaker.makeHash(edtPassword.getText().toString()));
            }
        });
    }

    private void signIn(final String userN, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userN).exists()) {
                    if (!userN.isEmpty()) {
                        User login = dataSnapshot.child(userN).getValue(User.class);
                        if (login.getPassword().equals(pwd)) {
                            edtuserName.getText().clear();
                            edtPassword.getText().clear();
                            Intent homeActivity=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(homeActivity);
                            finish();


                        } else {
                            Message.makeToastMessage(getApplicationContext(),
                                    "Wrong Password.", "");
                            edtuserName.getText().clear();
                            edtPassword.getText().clear();
                        }
                    } else {
                        Message.makeToastMessage(getApplicationContext(),
                                "Please enter User Name", "");
                        edtPassword.getText().clear();
                    }
                } else {
                    Message.makeToastMessage(getApplicationContext(), "User Name is Incorrect", "");
                    edtuserName.getText().clear();
                    edtPassword.getText().clear();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Message.makeToastMessage(getApplicationContext(),
                        "Ops, Something went wrong.", "");
            }
        });

    }
    private void signUp(){

    }


}
