package com.example.rickmorty;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("{id}")
    public Call<List<Osoba>> getOsoba(@Path("id") int id);
}
