package com.mabbureau.panoptes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private Button signUpButton;
    private Button forgotPasswordButton;

    // Base URL for your API
    private static final String BASE_URL = "http://10.0.0.175:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Set up button click listeners
        setUpListeners();
    }

    private void setUpListeners() {
        // Forgot Password button click
        forgotPasswordButton.setOnClickListener(v -> handleForgotPassword());

        // Sign Up button click
        signUpButton.setOnClickListener(v -> handleSignUp());

        // Login button click
        loginButton.setOnClickListener(v -> validateAndLogin());
    }

    private void validateAndLogin() {
        String email = Objects.requireNonNull(emailInput.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordInput.getText()).toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields.");
            return;
        }

        // Log for debugging
        Log.d("LoginActivity", "Attempting login with email: " + email + " and password: " + password);

        // Make API call for login
        performLogin(email, password);
    }

    private void performLogin(String email, String password) {
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        Log.d("LoginActivity", "Sending login request with: " + loginRequest.toString());

        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LoginActivity", "Response Code: " + response.code());
                Log.d("LoginActivity", "Response Message: " + response.message());
                if (response.isSuccessful() && response.body() != null) {
                    // Save token and navigate to next activity
                    String token = response.body().getToken();
                    saveUserToken(token);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    showToast("Login Successful");
                } else {
                    Log.e("LoginActivity", "Error Body: " + response.errorBody());
                    showToast("Login failed: " + (response.body() != null ? response.body().getMessage() : "Invalid credentials"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showToast("Login failed: " + t.getMessage());
            }
        });
    }

    private void saveUserToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply(); // Save changes
    }

    private void handleForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void handleSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    // Helper function to show Toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
