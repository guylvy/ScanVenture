package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class TutorialActivity extends Activity {
    private String correct = "apple";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        if (getIntent().getExtras().containsKey("user_data")) {
            TextView tutorial_text = findViewById(R.id.result_text);
            tutorial_text.setText(tutorial_text.getText() + ((Account)(getIntent().getSerializableExtra("user_data"))).getUsername() +"?");
        }
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
