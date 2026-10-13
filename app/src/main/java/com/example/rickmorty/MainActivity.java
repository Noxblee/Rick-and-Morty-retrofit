package com.example.rickmorty;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/character/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Random random = new Random();
        int wylosowaneId = random.nextInt(826) + 1;

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Osoba>> call = jsonPlaceHolderApi.getOsoba(wylosowaneId);

        call.enqueue(
                new Callback<List<Osoba>>() {
                    @Override
                    public void onResponse(Call<List<Osoba>> call, Response<List<Osoba>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<Osoba>> call, Throwable t) {

                    }
                }
        );
    }
}