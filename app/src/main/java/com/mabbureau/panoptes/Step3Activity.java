package com.mabbureau.panoptes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Step3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3); // Link to your layout file

        MaterialButton skipButton = findViewById(R.id.skip_button);
        MaterialButton nextButton = findViewById(R.id.Next_button);

        nextButton.setOnClickListener(v -> {
            // Create an Intent to start PopiaActivity
            Intent intent = new Intent(Step3Activity.this, Step4Activity.class);
            startActivity(intent); // Start the Step4Activity
        });

        skipButton.setOnClickListener(v -> {
            // Create an Intent to start PopiaActivity
            Intent intent = new Intent(Step3Activity.this, Step4Activity.class);
            startActivity(intent); // Start the Step4Activity
        });
    }
}
