package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class TutorialActivity extends Activity {
    private String correct = "apple";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }

    public void OpenCamera(android.view.View choice) {
        if (choice.getTag().toString().equals(correct)) {
            Intent i = new Intent(TutorialActivity.this, DetectorActivity.class);
            i.putExtra("Target", correct);
            startActivity(i);
        } else if (((ImageButton)choice).isClickable()) {
            ((ImageButton)choice).setAlpha(.1f);
            ((ImageButton)choice).setClickable(false);
        }
    }
}
