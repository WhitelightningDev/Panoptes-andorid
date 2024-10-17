package com.mabbureau.panoptes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {


    private TextInputEditText nameInput, surnameInput, contactInput, emailInput, passwordInput;
    private static final String BASE_URL = "http://10.0.0.175:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Check if the user is already signed in
        if (isUserSignedIn()) {
            navigateToWizardIntro();
            return; // Exit onCreate
        }

        MaterialButton Signup_next = findViewById(R.id.Signup_next);
        // Initialize Views
        nameInput = findViewById(R.id.NameInput);
        surnameInput = findViewById(R.id.SurnameInput);
        contactInput = findViewById(R.id.ContactInput);
        emailInput = findViewById(R.id.EmailInput);
        passwordInput = findViewById(R.id.PasswordInput);
        MaterialButton signInButton = findViewById(R.id.signInButton);
        MaterialButton forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        // Set OnClickListener for Sign In Button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSignIn();
            }
        });

        Signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WizardNext();
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

    private void WizardNext() {
        Intent intent = new Intent(SignInActivity.this, WizardIntroActivity.class);
        startActivity(intent);
    }

    // Check if the user is already signed in
    private boolean isUserSignedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        String token = sharedPreferences.getString("token", null);
        boolean isSignedIn = userId != null && token != null;

        // Debug log
        Log.d("SignInActivity", "isUserSignedIn: userId=" + userId + ", token=" + token + ", isSignedIn=" + isSignedIn);

        return isSignedIn;
    }

    // Navigate to WizardIntroActivity
    private void navigateToWizardIntro() {
        Intent intent = new Intent(SignInActivity.this, WizardIntroActivity.class);
        startActivity(intent);
        finish(); // Close this activity
    }

    // Validate inputs and call the API
    private void validateAndSignIn() {
        String name = Objects.requireNonNull(nameInput.getText()).toString().trim();
        String surname = Objects.requireNonNull(surnameInput.getText()).toString().trim();
        String contact = Objects.requireNonNull(contactInput.getText()).toString().trim();
        String email = Objects.requireNonNull(emailInput.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordInput.getText()).toString().trim();

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
                public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SignupResponse signupResponse = response.body(); // Get the response body
                        String userId = signupResponse.getUserId(); // Get the userId from the response

                        if (userId != null && !userId.isEmpty()) {
                            // Save user ID and token in SharedPreferences
                            saveUserId(userId);
                            saveUserToken(signupResponse.getToken()); // Save the token

                            // Show success toast message
                            showToast("Sign Up successful!");

                            // Navigate to WizardIntroActivity
                            navigateToWizardIntro();
                        } else {
                            showToast("Sign Up successful, but user ID is not available.");
                            navigateToWizardIntro();
                        }
                    } else {
                        handleErrorResponse(response);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SignupResponse> call, @NonNull Throwable t) {
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

    // Save user ID to SharedPreferences
    private void saveUserId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId); // Save userId
        editor.apply(); // Apply changes asynchronously

        // Debug log
        Log.d("SignInActivity", "saveUserId: userId=" + userId);
    }

    // Save user token to SharedPreferences
    private void saveUserToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply(); // Apply changes asynchronously

        // Debug log
        Log.d("SignInActivity", "saveUserToken: token=" + token);
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
