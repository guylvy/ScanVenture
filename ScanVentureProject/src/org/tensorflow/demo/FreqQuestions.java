package org.tensorflow.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FreqQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_questions);
    }
    @Override
    public void onBackPressed(){
        this.finish();
    }
}
