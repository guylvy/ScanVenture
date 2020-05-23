package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LevelActivity extends Activity {
    private String correct;
    private int level;
    private String userName;
    private String c1,c2,c3;
    private Account acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Account acc = Funcs.getAccount(getApplicationContext());
        this.level = (int) acc.getLevel();
        this.userName = acc.getUsername();
        TextView level_text = findViewById(R.id.level_task_text);
        TextView level_title = findViewById(R.id.level_title);
        level_text.setText(getString(getResources().getIdentifier("level_task_" + this.level, "string", getPackageName())) + " " + userName + "?");
        if (this.level == 0)
            level_title.setText("Tutorial");
        else
            level_title.setText("Level " + this.level);
        ArrayList<String> choices = new ArrayList<>();
        for (String choice : getString(getResources().getIdentifier("level_choices_" + this.level, "string", getPackageName())).split(" "))
            choices.add(choice);
        ImageButton[] level_choices = {(ImageButton) findViewById(R.id.level_choice1), (ImageButton) findViewById(R.id.after_stage_success_image), (ImageButton) findViewById(R.id.level_choice3)};
        this.correct = choices.get(0);
        for (ImageButton level_choice : level_choices) {
            int rnd = new Random().nextInt(choices.size());
            String rnd_choice = choices.get(rnd);
            level_choice.setImageResource(getResources().getIdentifier(rnd_choice, "drawable", getPackageName()));
            level_choice.setTag(rnd_choice);
            choices.remove(rnd);
        }
    }

    public void OpenCamera(android.view.View choice) {
        if (choice.getTag().toString().equals(correct)) {
            Intent i = new Intent(LevelActivity.this, DetectorActivity.class);
            i.putExtra("Target", correct);
            startActivity(i);
        } else if (((ImageButton)choice).isClickable()) {
            ((ImageButton)choice).setAlpha(.1f);
            ((ImageButton)choice).setClickable(false);
        }
    }
}
