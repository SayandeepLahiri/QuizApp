package com.example.sayandeep.quizquotient.Acitivities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;

import com.example.sayandeep.quizquotient.Helper.Message;
import com.example.sayandeep.quizquotient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    private String CLASS_TAG = SignUpActivity.class.getSimpleName();
    MaterialEditText _userName, _password, _phoneNumber, _otp;
    AppCompatButton requestOTP, confirmOTP;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks stateChangedCallbacks;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private boolean isVerificationInProgress = false;
    private int TIME_OUT = 60;
    private String mVerificationId;
    private boolean isResend=false;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        _userName = findViewById(R.id.edtUserName);
        _password = findViewById(R.id.edtPassword);
        _phoneNumber = findViewById(R.id.edtPhone);
        _otp = findViewById(R.id.edtOTP);
        requestOTP = findViewById(R.id.btn_request_otp);
        confirmOTP = findViewById(R.id.btn_confirm_otp);
        requestOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNotNull(_userName, _password, _phoneNumber)) {
                    if(!isResend) {
                        requestOTP.setText(getResources().getString(R.string.resendOTP));
                        sendRequestOTP();
                    }
                    else{
                        resendRequestOTP(forceResendingToken);
                    }
                } else
                    Message.makeToastMessage(getApplicationContext(),
                            "Please fill in all the details first",
                            "");
            }
        });
        confirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNotNull(_otp)) {
                    verifyPhoneWithCode(mVerificationId, _otp.getText().toString());
                }
            }
        });
        stateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Message.makeLogMessages(CLASS_TAG, "Login SUCCESS");
                isVerificationInProgress = false;
                signInWithPhone(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                isVerificationInProgress = false;
                Message.makeLogMessages(CLASS_TAG, "Login Failed.");
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Message.makeToastMessage(getApplicationContext(),
                            "Invalid Phone Number.",
                            "");
                    _phoneNumber.setError(getResources().getString(R.string.invalidPhone));
                }
            }

            @Override
            public void onCodeSent(String verificationID, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId = verificationID;
                Message.makeToastMessage(getApplicationContext(),
                        "OTP is send to your device.",
                        "");
            }
        };
    }

    /**
     * The method to check if the value of the edit(s) is not NULL.
     *
     * @param editTexts: The Edit Text you want to check.
     * @return: Return false if NOT NULL, else return TRUE.
     */
    private boolean isNotNull(MaterialEditText... editTexts) {
        for (MaterialEditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString()))
                return true;
        }
        return false;
    }

    /**
     * This is the method to send the code to the user.
     */
    private void sendRequestOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(_phoneNumber.getText().toString(),
                TIME_OUT,
                TimeUnit.SECONDS,
                this,
                stateChangedCallbacks);
        isVerificationInProgress = true;
    }

    /**
     * This is the method to check the signin and the code.
     *
     * @param phoneAuthCredential: The credential entered by the user.
     */
    private void signInWithPhone(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //TODO:SIGN IN Complete.
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                _otp.setError(getResources().getString(R.string.invalidOTP));
                            }
                        }
                    }
                });
    }

    /**
     * The method to verify the user entered OTP.
     *
     * @param verificationID: The verification ID for this app.
     * @param code:           The code entered by the user.
     */
    private void verifyPhoneWithCode(String verificationID, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithPhone(credential);
    }

    /**
     * Call this method to resend the Token.
     *
     * @param forceResendingToken: The Token for Resending.
     */
    private void resendRequestOTP(PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(_phoneNumber.getText().toString(),
                TIME_OUT,
                TimeUnit.SECONDS,
                this,
                stateChangedCallbacks, forceResendingToken);
    }
}
