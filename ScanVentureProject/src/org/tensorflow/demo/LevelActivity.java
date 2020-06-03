package org.tensorflow.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Vibrator;
import java.util.ArrayList;
import java.util.Random;

public class LevelActivity extends Activity {
    private String correct;
    private int level;
    private String userName;
    private String c1,c2,c3;
    private Account acc;
    private MediaPlayer successSoundMP;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Account acc = Funcs.getAccount(getApplicationContext());
        try {
            this.level = Integer.parseInt(getResources().getResourceName(getIntent().getExtras().getInt("circle_clicked")).split("_")[1]);
        } catch (Exception e) { // Probably because no circle clicked => Tutorial
            this.level = 0;
        }
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
        successSoundMP = MediaPlayer.create(getApplicationContext(),R.raw.success_sound);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void OpenCamera(android.view.View choice) {
        if (choice.getTag().toString().equals(correct)) {
            if (((String)Funcs.GetFromShredPrefs("Sound",false,getApplicationContext())) == "On")
                successSoundMP.start();
            Intent i = new Intent(LevelActivity.this, DetectorActivity.class);
            i.putExtra("Target", correct);
            i.putExtra("Level",this.level);
            startActivity(i);
            finish();
        } else if (((ImageButton)choice).isClickable()) {
            if (((String)Funcs.GetFromShredPrefs("Vibration",false,getApplicationContext())) == "On" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)); // Vibrate for 500 milliseconds
            ((ImageButton)choice).setAlpha(.1f);
            ((ImageButton)choice).setClickable(false);
        }
    }
}
