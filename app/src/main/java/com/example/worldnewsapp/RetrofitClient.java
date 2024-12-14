package com.example.worldnewsapp;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://newsapi.org/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Ajout d'un interceptor pour les en-têtes
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();
                            Request newRequest = originalRequest.newBuilder()
                                    .addHeader("Authorization", "Bearer VOTRE_API_KEY") // Clé API dans l'en-tête
                                    .addHeader("User-Agent", "AndroidApp")
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
