package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelsActivity extends Activity {
    Account acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        final Account acc = Funcs.getAccount(getApplicationContext());
        ((TextView)findViewById(R.id.hi_text)).setText("Hi "+ acc.getUsername());
        if (acc.getLevel() >= 1)
            spawnImages(R.id.circle_1, R.id.level_1);
        if (acc.getLevel() >= 2)
            spawnImages(R.id.circle_2,R.id.level_2,R.id.dots_12);
        if (acc.getLevel() >= 3)
            spawnImages(R.id.circle_3,R.id.level_3,R.id.dots_23);
        if (acc.getLevel() >= 4)
            spawnImages(R.id.circle_4,R.id.level_4,R.id.dots_34);
        findViewById(R.id.setting_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });
    }

    private void spawnImages(int circleID, int textID) {
        findViewById(circleID).setVisibility(View.VISIBLE);
        findViewById(textID).setVisibility(View.VISIBLE);
    }

    public void clickLevel(View view) {
        ImageButton circle = (ImageButton)view;
        startActivity(new Intent(getApplicationContext(),LevelActivity.class).putExtra("user_data",acc));
    }

    private void spawnImages(int circleID, int textID, int dotsID) {
        spawnImages(circleID, textID);
        findViewById(dotsID).setVisibility(View.VISIBLE);
    }
}