package com.example.sayandeep.quizquotient.Acitivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sayandeep.quizquotient.Helper.Constants;
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
    MaterialEditText edtPassword, edtUserName;
    Button signUp, signIn;
    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSignedIn()) {
            changeActivity();
        }
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);
        signIn = findViewById(R.id.btn_sign_in);
        signUp = findViewById(R.id.btn_sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtUserName.getText().toString(),
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
                            edtUserName.getText().clear();
                            edtPassword.getText().clear();
                            Constants.currentUser=login;
                            Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeActivity);
                            finish();


                        } else {
                            Message.makeToastMessage(getApplicationContext(),
                                    "Wrong Password.", "");
                            edtUserName.getText().clear();
                            edtPassword.getText().clear();
                        }
                    } else {
                        Message.makeToastMessage(getApplicationContext(),
                                "Please enter User Name", "");
                        edtPassword.getText().clear();
                    }
                } else {
                    Message.makeToastMessage(getApplicationContext(), "User Name is Incorrect", "");
                    edtUserName.getText().clear();
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

    private void signUp() {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivityForResult(signUpIntent, Constants.SIGNUP_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SIGNUP_CODE) {
            if (resultCode == RESULT_OK) {
                //Putting the status to the Shared Preferences.
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Constants.LOGIN_STATUS, true);
                editor.apply();
                changeActivity();
            } else {
                Message.makeToastMessage(getApplicationContext(),
                        "Please signup.",
                        "");
            }
        }
    }

    /**
     * This is the method to change the current activity to the HOME ACTIVITY.
     */
    private void changeActivity() {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    /**
     * This is the method to check the sign in status.
     *
     * @return: true if signed in, else false.
     */
    private boolean isSignedIn() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Constants.IS_LOGGED_IN = sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false);
        return Constants.IS_LOGGED_IN;
    }
}
