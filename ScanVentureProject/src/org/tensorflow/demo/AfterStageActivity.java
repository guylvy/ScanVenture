package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class AfterStageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_stage);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("Result")) {
            String result = getIntent().getStringExtra("Result");
            if (result.contains("Success")){
                findViewById(R.id.EndTutorialButton).setVisibility(View.VISIBLE);

            }

            ((TextView)findViewById(R.id.result_text)).setText(result);
        }
    }

    public void ContinueFunc(View v) {
        startActivity(new Intent(getApplicationContext(),LevelsActivity.class).putExtra("user_data",new Account("0","Stab",1)));
        finish();
    }
}