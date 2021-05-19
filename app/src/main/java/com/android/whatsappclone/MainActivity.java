package com.android.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText edtPhoneNumber,edtCode;
    private Button btnSend;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        userIsLoggIn();

        edtPhoneNumber = findViewById(R.id.phoneNumber);
        edtCode = findViewById(R.id.code);
        btnSend = findViewById(R.id.send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mVerificationId !=null){
                    verifiyPhoneNumberWithCode();
                }else{
                    startPhoneNumberVerification();
                }
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthredential(phoneAuthCredential);
                Log.e(MainActivity.class.getSimpleName(),phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) { }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                mVerificationId = verificationId;
                btnSend.setText("Verify Code");
            }
        };

    }

    private void verifiyPhoneNumberWithCode(){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, edtCode.getText().toString());
        signInWithPhoneAuthredential(credential);
    }

    private void signInWithPhoneAuthredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    userIsLoggIn();
                }
            }
        });
    }

    private void userIsLoggIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
            finish();
            return;
        }
    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                edtPhoneNumber.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
        );
    }
}