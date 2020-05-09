package org.tensorflow.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AfterStageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_stage);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("Result")) {
            ((TextView)findViewById(R.id.result_text)).setText(getIntent().getStringExtra("Result"));
        }
    }
}