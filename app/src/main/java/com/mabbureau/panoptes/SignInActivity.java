package com.mabbureau.panoptes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText nameInput, surnameInput, contactInput, emailInput, passwordInput;
    private MaterialButton signInButton, forgotPasswordButton;
    private static final String BASE_URL = "http://10.0.0.175:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Views
        nameInput = findViewById(R.id.NameInput);
        surnameInput = findViewById(R.id.SurnameInput);
        contactInput = findViewById(R.id.ContactInput);
        emailInput = findViewById(R.id.EmailInput);
        passwordInput = findViewById(R.id.PasswordInput);
        signInButton = findViewById(R.id.signInButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Set OnClickListener for Sign In Button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSignIn();
            }
        });

        // Set OnClickListener for Forgot Password Button
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgotPassword();
            }
        });
    }

    // Validate inputs and call the API
    private void validateAndSignIn() {
        String name = nameInput.getText().toString().trim();
        String surname = surnameInput.getText().toString().trim();
        String contact = contactInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInputs(name, surname, contact, email, password)) {
            // Create Retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            SignInRequest signInRequest = new SignInRequest(name, surname, contact, email, password);
            Call<SignupResponse> call = apiService.signUp(signInRequest); // Use signUp method

            call.enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Save token in SharedPreferences
                        String token = response.body().getUserId(); // Assuming token is userId; adjust as necessary
                        saveUserToken(token);

                        // Show success toast message
                        showToast("Sign Up successful!");

                        // Navigate to WizardIntroActivity
                        Intent intent = new Intent(SignInActivity.this, WizardIntroActivity.class);
                        startActivity(intent);
                        finish(); // Optionally close SignInActivity
                    } else {
                        handleErrorResponse(response);
                    }
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    showToast("Sign Up failed: " + t.getMessage());
                }
            });
        }
    }

    // Handle error responses
    private void handleErrorResponse(Response<SignupResponse> response) {
        String errorMessage;
        try {
            // Attempt to extract error message from the response body
            SignupResponse errorResponse = response.errorBody() != null
                    ? new Gson().fromJson(response.errorBody().string(), SignupResponse.class)
                    : null;

            if (errorResponse != null) {
                errorMessage = errorResponse.getMessage(); // Assuming your error response has a getMessage method
            } else {
                errorMessage = "Unknown error occurred. Please try again.";
            }

            // Check for specific error messages and handle accordingly
            if (response.code() == 400 && errorMessage.contains("already exists")) {
                // Redirect to login if user already exists
                showToast("User already exists. Redirecting to login...");
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class); // Replace LoginActivity with your login class
                startActivity(intent);
                finish(); // Optionally close SignInActivity
            } else {
                showToast("Sign Up failed: " + errorMessage);
            }
        } catch (Exception e) {
            showToast("Error parsing error response: " + e.getMessage());
        }
    }

    // Validate the inputs
    private boolean validateInputs(String name, String surname, String contact, String email, String password) {
        if (name.isEmpty() || surname.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast("Please fill all fields.");
            return false;
        }
        return true;
    }

    // Save user token to SharedPreferences
    private void saveUserToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply(); // Apply changes asynchronously
    }

    private void goToForgotPassword() {
        Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    // Helper function to show Toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
