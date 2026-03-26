package com.jmjbrothers.renthouseandroidapplication.service;

import com.jmjbrothers.renthouseandroidapplication.model.LoginRequest;
import com.jmjbrothers.renthouseandroidapplication.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

