package com.example.sayandeep.quizquotient.Acitivities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sayandeep.quizquotient.Helper.HashMaker;
import com.example.sayandeep.quizquotient.R;
import com.example.sayandeep.quizquotient.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edtnewPassword, edtnewUsername, edtemailPhone, edtPassword, edtuserName;
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
                signUpDialog();
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
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            edtuserName.setText(null);
                            edtPassword.setText(null);
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            edtuserName.setText(null);
                            edtPassword.setText(null);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter your User Name", Toast.LENGTH_SHORT).show();

                        edtPassword.setText(null);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User Name is Incorrect ", Toast.LENGTH_SHORT).show();
                    edtuserName.setText(null);
                    edtPassword.setText(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void signUpDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Fill up the details completely");
        LayoutInflater inflater = this.getLayoutInflater();
        View signUp_layout = inflater.inflate(R.layout.signup_activity, null);

        edtemailPhone = signUp_layout.findViewById(R.id.edtPhone);
        edtnewUsername = signUp_layout.findViewById(R.id.edtNewUserName);
        edtnewPassword = signUp_layout.findViewById(R.id.edtNewPassword);
        alertDialog.setView(signUp_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(edtnewUsername.getText().toString(),
                        //Making the hash and then adding the password.
                        HashMaker.makeHash(edtnewPassword.getText().toString()),
                        edtemailPhone.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {


                            if (dataSnapshot.child(user.getUserName()).exists())
                                Toast.makeText(MainActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();
                            else {
                                users.child(user.getUserName()).setValue(user);
                                Toast.makeText(MainActivity.this, "User creation successful", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }
}
