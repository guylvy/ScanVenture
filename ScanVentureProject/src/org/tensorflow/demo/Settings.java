package org.tensorflow.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

public class Settings extends Activity {
    Button signout;
    Account user;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        currentUser = GoogleSignIn.getLastSignedInAccount(this);
        getAccountFromLogin(); //Updates the user Account type variable with Account data from FireBaseDB
        //user = new Account(currentUser.getDisplayName());
        TextView userName = findViewById(R.id.NameVal);
        userName.setText(user.getUsername());
        TextView lvl = findViewById(R.id.LevelVal);
        lvl.setText(String.valueOf(user.getLevel()));
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
        finish();
    }
    private void getAccountFromLogin(){
        if (getIntent().getExtras() != null){
            user = (Account)getIntent().getSerializableExtra("user_data");
        }
    }
}
