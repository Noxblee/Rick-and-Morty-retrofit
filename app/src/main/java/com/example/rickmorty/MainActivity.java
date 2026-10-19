package com.example.rickmorty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView nazwaTV, gatunekTV, statusTV;
    private int id = 0;
    private ImageView zdjecieView;
    private Button losujNastepneB;

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

        nazwaTV = findViewById(R.id.nameTV);
        gatunekTV = findViewById(R.id.speciesTV);
        statusTV = findViewById(R.id.statusTV);
        zdjecieView = findViewById(R.id.imageView);
        losujNastepneB = findViewById(R.id.nextB);


        losujPostac();
        losujNastepneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                losujPostac();
            }
        });

    }

    public void losujPostac() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Random random = new Random();

        id = random.nextInt(826) + 1;

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Osoba> call = jsonPlaceHolderApi.getRandomCharacter(id);

        call.enqueue(
                new Callback<Osoba>() {
                    @Override
                    public void onResponse(Call<Osoba> call, Response<Osoba> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Osoba os = response.body();
                        nazwaTV.setText(os.getId() + ". " + os.getName());
                        gatunekTV.setText(os.getSpecies() + ", " + os.getGender());
                        statusTV.setText(os.getStatus());
                        if(os.getGender().equals("Female")){
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.pink));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkPink));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkPink));
                        }
                        else if (os.getGender().equals("Male")){
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.blue));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkBlue));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkBlue));

                        } else if (os.getGender().equals("unknown")) {
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.green));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkGreen));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkGreen));

                        }
                        else {
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.gray));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkGray));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkGray));

                        }
                        if(os.getStatus().equals("Alive")){
                            statusTV.setTextColor(getResources().getColor(R.color.darkGreen));
                        }
                        else if (os.getStatus().equals("Dead")){
                            statusTV.setTextColor(getResources().getColor(R.color.darkRed));
                        }
                        else {
                            statusTV.setTextColor(getResources().getColor(R.color.darkGray));
                        }

                    }

                    @Override
                    public void onFailure(Call<Osoba> call, Throwable t) {

                    }
                }
        );
    }

}