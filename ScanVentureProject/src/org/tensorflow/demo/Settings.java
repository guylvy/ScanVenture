package org.tensorflow.demo;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends Activity implements View.OnClickListener , Switch.OnCheckedChangeListener {
    Button signout;
    Account user;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount currentUser;
    AudioManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("330523070311-u7t0je4j07jdam1toivmupidd6vsredt.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        currentUser = GoogleSignIn.getLastSignedInAccount(this);
        getAccountFromLogin(); //Updates the user Account type variable with Account data from FireBaseDB
        //user = new Account(currentUser.getDisplayName());
        WriteAccountDetails();
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

    }
    @Override
    public void onBackPressed(){
        this.finish();
    }
    private void WriteAccountDetails() {
        TextView userName = findViewById(R.id.NameVal);
        TextView lvl = findViewById(R.id.LevelVal);
        TextView authName = findViewById(R.id.AuthNameVal);
        authName.setText(user.getId());
        userName.setText(user.getUsername());
        lvl.setText(String.valueOf(user.getLevel()));
        authName.setVisibility(View.VISIBLE);
        lvl.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
    }
    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Sign out completed!
                    }
                });
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finishAffinity(); //finish() finishes only the current activity while finishAffinity() closes all parent activities.
    }
    private void getAccountFromLogin(){
        if (getIntent().getExtras() != null){
            user = (Account)getIntent().getSerializableExtra("user_data");
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.aboutUsBtn:
                startActivity(new Intent(getApplicationContext(),About.class));
                break;
            case R.id.faqButton:
                startActivity(new Intent(getApplicationContext(),FreqQuestions.class));
                break;
            }

        }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.SoundSwitch:
                if (isChecked) { unmuteApp();}
                else {muteApp();}
                break;
            case R.id.VibrationSwitch:
                if (isChecked) { setVibrationOn();}
                else {setVibrationOff();}
                break;
        }
    }

    private void unmuteApp() {//Needed to be checked somehow
        am.setStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE,0);
    }

    private void muteApp() {//Needed to be checked somehow
        am.setStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
    }

    private void setVibrationOn(){
       //TODO: Implement device vibration enable
    }

    private void setVibrationOff(){
        //TODO: Implement device vibration disable
    }

}
