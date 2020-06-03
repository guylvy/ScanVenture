package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AfterStageActivity extends Activity {
    boolean result = false;
    Account acc;
    int stage_level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_stage);
        Bundle extras = getIntent().getExtras();
        acc = Funcs.getAccount(getApplicationContext());
        this.stage_level = extras.getInt("Level");
        ((TextView)findViewById(R.id.level_title)).setText("Level "+ this.stage_level);
        ((ImageView)findViewById(R.id.after_stage_success_image)).setImageResource(getResources().getIdentifier(extras.getString("Target"), "drawable", getPackageName()));
        if (extras.containsKey("Result")) {
            String result = extras.getString("Result");
            if (result.contains("Success")) {
                findViewById(R.id.EndTutorialButton).setVisibility(View.VISIBLE);
                this.result = true;
            }
            ((TextView)findViewById(R.id.level_task_text)).setText(result);
        }
    }

    public void ContinueFunc(View v) {
        if (this.result && this.stage_level == acc.getLevel())
            if (acc.getLevel() == 4)
                startActivity(new Intent(AfterStageActivity.this, CongratsActivity.class)); // change to congrats
            else
                acc.incLevel();
        else
            startActivity(new Intent(AfterStageActivity.this, LevelsActivity.class));
        finish();
    }
}