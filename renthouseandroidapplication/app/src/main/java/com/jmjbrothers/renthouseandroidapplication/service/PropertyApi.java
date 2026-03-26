package com.jmjbrothers.renthouseandroidapplication.service;

import com.jmjbrothers.renthouseandroidapplication.model.GetPostedProperty;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PropertyApi {
    @GET("/all/posted/properties")
    Call<List<GetPostedProperty>> getAllPostedProperties();
}
