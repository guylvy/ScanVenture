package org.tensorflow.demo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CongratsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);
    }

    public void StartOver(View view) {
        Funcs.getAccount(getApplicationContext()).setLevel(1);
    }
}
