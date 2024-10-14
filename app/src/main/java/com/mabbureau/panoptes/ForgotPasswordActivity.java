package com.mabbureau.panoptes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText emailInput;
    private Button sendResetLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password); // Set the content view for the forgot password layout

        // Initialize UI elements
        emailInput = findViewById(R.id.emailInput);
        sendResetLinkButton = findViewById(R.id.sendResetLinkButton);

        // Set up button click listener
        sendResetLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendResetLink();
            }
        });
    }

    private void handleSendResetLink() {
        String email = emailInput.getText().toString().trim();

        // Basic validation
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would typically call your backend service to send a password reset email
        // For demonstration, let's simulate a success message
        Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();

        // Optionally, you can redirect the user back to the LoginActivity
        finish(); // Close this activity and return to the previous one
    }
}
