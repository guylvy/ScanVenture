package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AfterStageActivity extends Activity {
    boolean result = false;
    Account acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_stage);
        Bundle extras = getIntent().getExtras();
        acc = Funcs.getAccount(getApplicationContext());
        ((ImageView)findViewById(R.id.after_stage_success_image)).setImageResource(getResources().getIdentifier(getString(getResources().getIdentifier("level_choices_" + acc.getLevel(), "string", getPackageName())).split(" ")[0], "drawable", getPackageName()));
        if (extras.containsKey("Result")) {
            String result = getIntent().getStringExtra("Result");
            if (result.contains("Success")) {
                findViewById(R.id.EndTutorialButton).setVisibility(View.VISIBLE);
                this.result = true;
            }
            ((TextView)findViewById(R.id.level_task_text)).setText(result);
        }
    }

    public void ContinueFunc(View v) {
        if (this.result)
            acc.incLevel();
        finish();
    }
}