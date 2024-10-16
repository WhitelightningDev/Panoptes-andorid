package com.mabbureau.panoptes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class PopiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popia); // Link to your layout file



        TextView hyperlinkTextView = findViewById(R.id.hyperlinkTextView);
        hyperlinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open your desired webpage, for example:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mabbureau.com/pdf/Privacy%20Policy%20-%20MAB.pdf"));
                startActivity(browserIntent);
            }
        });
        MaterialButton materialButton = findViewById(R.id.Next_button);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start PopiaActivity
                Intent intent = new Intent(PopiaActivity.this, Step1Activity.class);
                startActivity(intent); // Start the PopiaActivity
            }
        });

    }

}
