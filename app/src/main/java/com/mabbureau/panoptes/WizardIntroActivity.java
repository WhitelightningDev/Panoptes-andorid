package com.mabbureau.panoptes;

import android.content.Intent; // Import Intent class
import android.os.Bundle;
import android.view.View; // Import View class
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class WizardIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_intro); // Link to your layout file

        MaterialButton nextButton = findViewById(R.id.materialButton); // Find the button by ID
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start PopiaActivity
                Intent intent = new Intent(WizardIntroActivity.this, PopiaActivity.class);
                startActivity(intent); // Start the PopiaActivity
            }
        });
    }
}
