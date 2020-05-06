package org.tensorflow.demo;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        Account user = new Account(0, "UGEV", 1);
        if (user.getLevel() >= 1)
            spawnImages(R.id.circle_1, R.id.level_1);
        if (user.getLevel() >= 2)
            spawnImages(R.id.circle_2,R.id.level_2,R.id.dots_12);
        if (user.getLevel() >= 3)
            spawnImages(R.id.circle_3,R.id.level_3,R.id.dots_23);
        if (user.getLevel() >= 4)
            spawnImages(R.id.circle_4,R.id.level_4);
    }

    private void spawnImages(int circleID, int textID) {
        findViewById(circleID).setVisibility(View.VISIBLE);
        findViewById(textID).setVisibility(View.VISIBLE);
    }

    private void spawnImages(int circleID, int textID, int dotsID) {
        spawnImages(circleID, textID);
        findViewById(dotsID).setVisibility(View.VISIBLE);
    }
}
