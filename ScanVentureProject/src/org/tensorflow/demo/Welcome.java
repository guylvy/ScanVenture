package org.tensorflow.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Welcome extends Activity {
    Account user;
    ImageButton settingsbtn;
    TextView userNameInHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        user = (Account)getIntent().getSerializableExtra("user_data");
        userNameInHeader = findViewById(R.id.userNameInWelcome);
        userNameInHeader.setText(user.getUsername());
        settingsbtn = findViewById(R.id.settingsBtn);
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettings = new Intent(getApplicationContext(),Settings.class).putExtra("user_data",user);
                startActivity(goToSettings);
            }
        });


    }
}
