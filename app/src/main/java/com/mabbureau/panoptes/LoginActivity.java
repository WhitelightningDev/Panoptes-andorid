package com.mabbureau.panoptes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast; // Import Toast for error messages
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private Button loginButton; // Changed this to reflect the correct button
    private Button signUpButton; // This should point to the Sign Up functionality
    private Button forgotPasswordButton;

    // Simulated user credentials
    private static final String VALID_USERNAME = "DanDev";
    private static final String VALID_PASSWORD = "Daniel@95";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the content view for the login layout

        // Initialize UI elements
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton); // Initialize login button
        signUpButton = findViewById(R.id.signUpButton); // Initialize sign up button
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Set up button click listeners
        setUpListeners();
    }

    private void setUpListeners() {
        // Forgot Password button click
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleForgotPassword();
            }
        });

        // Sign Up button click
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp(); // Method to handle sign up
            }
        });

        // Login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin(); // Changed method name to handleLogin
            }
        });
    }

    private void handleLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Log for debugging
        Log.d("LoginActivity", "Attempting login with username: " + username + " and password: " + password);

        // Check for valid credentials
        if (isValidCredentials(username, password)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Redirect to MainActivity
            startActivity(intent);
            finish(); // Optional: Finish this activity to prevent going back to it
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show(); // Confirm login success
        } else {
            // Show an error message
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidCredentials(String username, String password) {
        // Check the provided username and password against predefined values
        return username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD);
    }

    private void handleForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent); // Start the Forgot Password activity
    }

    private void handleSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignInActivity.class); // Assuming SignInActivity is for sign up
        startActivity(intent); // Start the Sign Up activity
    }
}
