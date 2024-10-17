package com.mabbureau.panoptes;

import android.content.SharedPreferences; // Add this import
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast; // Import Toast for showing messages
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView surnameTextView;
    private TextView emailTextView;
    private TextView contactTextView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize TextViews
        nameTextView = findViewById(R.id.name);
        surnameTextView = findViewById(R.id.surname);
        emailTextView = findViewById(R.id.email);
        contactTextView = findViewById(R.id.contact);

        // Initialize ApiService
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class); // Assuming you have a Retrofit instance

        // Fetch user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null); // Retrieve the user ID

//        if (userId != null) {
//            // Fetch user data from backend using the userId
//            fetchUserData(userId);
//        } else {
//            // Handle the case where userId is null
//            showToast("User ID not found. Please log in again.");
//            // Optionally, you could navigate back to the login screen here
//        }
    }

//    private void fetchUserData(String userId) {
//        Call<UserResponse> call = apiService.getUserById(userId);
//
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    User user = response.body().getUser(); // Access the user object
//                    if (user != null) {
//                        nameTextView.setText(user.getName());
//                        surnameTextView.setText(user.getSurname());
//                        emailTextView.setText(user.getEmail());
//                        contactTextView.setText(user.getContact());
//                    } else {
//                        showToast("User data is null. Please try again.");
//                    }
//                } else {
//                    // Handle the error response
//                    String errorMessage = response.message(); // Get the error message
//                    if (response.errorBody() != null) {
//                        showToast("Error: " + errorMessage); // Show the error message
//                    } else {
//                        showToast("Failed to retrieve user data. Please try again.");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                // Handle failure (e.g., show a Toast or Log)
//                showToast("Error: " + t.getMessage()); // Show the error message
//            }
//        });
//    }

    // Helper function to show Toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
