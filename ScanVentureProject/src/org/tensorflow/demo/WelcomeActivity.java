package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeActivity extends Activity {
    Button start_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void OpenCamera(android.view.View start_button) {
        startActivity(new Intent(WelcomeActivity.this,TutorialActivity.class));
    }
}
