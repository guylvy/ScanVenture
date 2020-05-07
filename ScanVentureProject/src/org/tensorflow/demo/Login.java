package org.tensorflow.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.os.Message;
import android.util.DebugUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;

public class Login extends Activity{

    private static final int RC_SIGN_IN = 1;
    SignInButton signInButton;
    private static final int SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
    Account userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        //checkIfLoggedIn();
        //Set dimensions of Google's sign in button - Standard
        signInButton = findViewById(R.id.googleLoginBtn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        //Google api required clases to perform login
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
    }
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount user = GoogleSignIn.getLastSignedInAccount(this);
        if(user!=null){
            getUserDataFromDB(user);
            Intent intent = new Intent(getApplicationContext(),Settings.class);
            intent.putExtra("user_data",userData);
            startActivity(intent);
        }
    }

    private void getUserDataFromDB(final GoogleSignInAccount currentUser) {
        DatabaseReference usersRef = rootref.child("users");
        final String email = currentUser.getEmail();
        final String emailName = email.substring(0, email.indexOf('@'));
        usersRef.child(emailName).setValue(emailName);
        usersRef.child(emailName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData = new Account(emailName,dataSnapshot.child("name").getValue().toString(),(int)dataSnapshot.child("level").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Google SignIn made!", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Failed to sign in!", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            writeNewAccountToFireBase(acct);
                            Intent intent = new Intent(getApplicationContext(),Settings.class);
                            intent.putExtra("user_data",userData);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    private void updateUI(FirebaseUser user){
        if (user == null){
            //TODO: sign in failed handle this!
        }
        else {
            //Account loggedInUser = new Account();
            Intent goTo = new Intent(Login.this,Settings.class);
            goTo.putExtra("user_data",userData);
            startActivity(goTo);
            finish();
        }
    }

    private void writeNewAccountToFireBase(GoogleSignInAccount user) {
        //Create accounts section in DB and get a ref to it
        DatabaseReference usersRef = rootref.child("users");
        //Write user data as an Account class object
        final String email = user.getEmail();
        final String emailName = email.substring(0, email.indexOf('@'));
        usersRef.child(emailName).child("name").setValue(user.getDisplayName());
        usersRef.child(emailName).child("level").setValue(0);
        userData = new Account(user.getEmail(),user.getDisplayName(),0); //only for a new user
    }

    private void checkIfLoggedIn() {
        FirebaseAuth.AuthStateListener mAuthListener;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(Login.this, Settings.class);
                    //getUserDataFromDB(user);
                    intent.putExtra("user_data",userData);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}
