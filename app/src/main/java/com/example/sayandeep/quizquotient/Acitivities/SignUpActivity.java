package com.example.sayandeep.quizquotient.Acitivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;

import com.example.sayandeep.quizquotient.Helper.Message;
import com.example.sayandeep.quizquotient.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    private String CLASS_TAG = SignUpActivity.class.getSimpleName();
    MaterialEditText _userName, _password, _phoneNumber;
    AppCompatButton requestOTP, confirmOTP;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks stateChangedCallbacks;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private boolean isVerificationInProgress = false;
    private int TIME_OUT=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        _userName = findViewById(R.id.edtUserName);
        _password = findViewById(R.id.edtPassword);
        _phoneNumber = findViewById(R.id.edtPhone);
        requestOTP = findViewById(R.id.btn_request_otp);
        confirmOTP = findViewById(R.id.btn_confirm_otp);
        requestOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNotNull()) {
                    requestOTP.setText(getResources().getString(R.string.resendOTP));
                    sendRequestOTP();
                } else
                    Message.makeToastMessage(getApplicationContext(),
                            "Please fill in all the details first",
                            "");
            }
        });
        stateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Message.makeLogMessages(CLASS_TAG,"Login SUCCESS");
                isVerificationInProgress=false;
                signInWithPhone(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                isVerificationInProgress=false;
                Message.makeLogMessages(CLASS_TAG,"Login Failed.");
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Message.makeToastMessage(getApplicationContext(),
                            "Invalid Phone Number.",
                            "");
                    _phoneNumber.setError(getResources().getString(R.string.invalidPhone));
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            }
        };
    }

    private boolean isNotNull() {
        return TextUtils.isEmpty(_userName.getText().toString()) &&
                TextUtils.isEmpty(_password.getText().toString()) &&
                TextUtils.isEmpty(_phoneNumber.getText().toString());
    }

    private void sendRequestOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(_phoneNumber.getText().toString(),
                TIME_OUT,
                TimeUnit.SECONDS,
                this,
                stateChangedCallbacks);
        isVerificationInProgress=true;
    }
    private void signInWithPhone(PhoneAuthCredential phoneAuthCredential){

    }
}
