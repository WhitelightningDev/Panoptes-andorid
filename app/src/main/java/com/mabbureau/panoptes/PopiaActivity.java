package com.mabbureau.panoptes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class PopiaActivity extends AppCompatActivity {

    private CheckBox checkbox1, checkbox2;
    private MaterialButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popia); // Link to your layout file
        checkbox1 = findViewById(R.id.checkbox_terms);
        checkbox2 = findViewById(R.id.checkbox_popi);
        nextButton = findViewById(R.id.Next_button);

        // Initially disable the button
        nextButton.setEnabled(false);

        // Check for checkbox clicks
        View.OnClickListener checkboxClickListener = v -> {
            // Enable the button only if both checkboxes are checked
            nextButton.setEnabled(checkbox1.isChecked() && checkbox2.isChecked());
        };

        // Set the listener for both checkboxes
        checkbox1.setOnClickListener(checkboxClickListener);
        checkbox2.setOnClickListener(checkboxClickListener);

        // Set the listener for the hyperlink
        TextView hyperlinkTextView = findViewById(R.id.hyperlinkTextView);
        hyperlinkTextView.setOnClickListener(v -> {
            // Open your desired webpage, for example:
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mabbureau.com/pdf/Privacy%20Policy%20-%20MAB.pdf"));
            startActivity(browserIntent);
        });

        // Set the listener for the Next button
        nextButton.setOnClickListener(v -> {
            // Check if both checkboxes are checked
            if (checkbox1.isChecked() && checkbox2.isChecked()) {
                // Proceed to the next activity
                Intent intent = new Intent(PopiaActivity.this, Step1Activity.class);
                startActivity(intent);
            } else {
                // Show a Toast message if checkboxes are not checked
                Toast.makeText(PopiaActivity.this, "Please agree to the Terms and Privacy Policy to continue.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
