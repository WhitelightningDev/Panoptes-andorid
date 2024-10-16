package com.mabbureau.panoptes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Step1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1); // Link to your layout file

        MaterialButton nextButton = findViewById(R.id.Next_button);
        MaterialButton skipButton = findViewById(R.id.skip_button);

        skipButton.setOnClickListener(v -> {
            // Create an Intent to start PopiaActivity
            Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
            startActivity(intent); // Start the Step2Activity
        });
        nextButton.setOnClickListener(v -> {
            // Create an Intent to start PopiaActivity
            Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
            startActivity(intent); // Start the Step2Activity
        });
        }
}
