package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Start extends Activity {
    Button start_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void OpenCamera(android.view.View start_button) {
        startActivity(new Intent(Start.this,DetectorActivity.class));
    }
}
