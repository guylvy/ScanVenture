package org.tensorflow.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.DebugUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

import java.io.Console;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;

public class Login extends Activity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 1;
    SignInButton signInButton;
    private static final int SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private String tkn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set dimensions of Google's sign in button - Standard
        signInButton = findViewById(R.id.googleLoginBtn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        //Google api required classes to perform login
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        //set onClick Listener for the button
        findViewById(R.id.googleLoginBtn).setOnClickListener(this);
    }
    @Override
    protected void onStart() {//check if a user has already signed in to ScanVenture with Google.
        super.onStart();
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) { //user has already logged in with google
            startActivity(new Intent(Login.this,Start.class));
            //TODO : Additional actions to perform
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.googleLoginBtn:
                signIn();
                break;
            //Todo : Add additional onClick functionality of other buttons if needed
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI with account details

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Oops :( , something went wrong!", "signInResult:failed code=" + e.getStatusCode());

        }
    }
    public void loginWithGoogle(android.view.View start_button){

    }
}
