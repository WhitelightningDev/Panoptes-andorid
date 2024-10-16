package com.mabbureau.panoptes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/users/login") // Endpoint for logging in
    Call<LoginResponse> login(@Body LoginRequest loginRequest); // Method for logging in

    @POST("api/users/signup") // Endpoint for signing up
    Call<SignupResponse> signUp(@Body SignInRequest signUpRequest); // Method for signing up

    @POST("api/users/check") // New endpoint for checking if a user exists
    Call<UserCheckResponse> checkUserExists(@Body UserCheckRequest userCheckRequest); // Method for checking if a user exists
}
