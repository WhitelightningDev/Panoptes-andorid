package com.mabbureau.panoptes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/users/login") // Endpoint for logging in
    Call<LoginResponse> login(@Body LoginRequest loginRequest); // Method for logging in

    @POST("api/users/signup") // Endpoint for signing up
    Call<SignupResponse> signUp(@Body SignInRequest signUpRequest); // Method for signing up

//    @GET("api/users/getById/{userId}") // Updated Endpoint for getting user details
//    Call<UserResponse> getUserById(@Path("userId") String userId); // Method for fetching user details
}
