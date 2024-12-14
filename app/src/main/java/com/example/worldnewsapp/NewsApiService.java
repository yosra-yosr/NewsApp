package com.example.worldnewsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("category") String category,
            @Query("apiKey") String apiKey // La clé API passe ici
    );
}


