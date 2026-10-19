package com.example.rickmorty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView nazwaTV, gatunekTV, statusTV;
    private int tempId = 0;
    private ImageView zdjecieView;
    private Button losujNastepneB, szukajB, opisB;
    private EditText idET, opisET;
    private boolean czyOpis = false;
    private String tempOpis = "";
    private Map<Integer, String> opisyMap = new HashMap<>();

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
        szukajB = findViewById(R.id.szukajB);
        idET = findViewById(R.id.idET);
        opisB = findViewById(R.id.opisB);
        opisET = findViewById(R.id.opisET);

        Random random = new Random();
        tempId = random.nextInt(826) + 1;

        utworzPostac(tempId);

        losujNastepneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempId = random.nextInt(826) + 1;
                utworzPostac(tempId);
            }
        });

        szukajB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = idET.getText().toString().trim();
                if (!input.isEmpty()) {
                    try {
                        tempId = Integer.parseInt(input);
                        if (tempId > 0 && tempId <= 826) {
                            utworzPostac(tempId);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Wprowadź ID w zakresie 1-826", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Niepoprawny format ID", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Wprowadź ID", Toast.LENGTH_SHORT).show();
                }
                idET.setText("");
            }
        });
        opisB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!czyOpis){
                            opisB.setText("Zapisz opis");
                            opisB.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                            opisB.setTextColor(getResources().getColor(R.color.green));
                            opisET.setVisibility(View.VISIBLE);
                            if(opisyMap.containsKey(tempId)){
                                opisET.setText(opisyMap.get(tempId));
                            }
                            else {
                                opisET.setText("");

                            }
                            czyOpis = true;
                        }
                        else {
                            tempOpis = opisET.getText().toString();
                            if(opisyMap.containsKey(tempId)){
                                opisyMap.remove(tempId);
                            }
                            if(!tempOpis.isEmpty()) {
                                opisyMap.put(tempId, tempOpis);
                            }

                            czyOpis = false;
                            opisET.setVisibility(View.INVISIBLE);
                            opisET.setText("");
                            utworzPostac(tempId);
                        }
                    }
                }
        );

    }

    public void utworzPostac(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
                        tempId = os.getId();
                        nazwaTV.setText(os.getId() + ". " + os.getName());
                        gatunekTV.setText(os.getSpecies() + ", " + os.getGender());
                        statusTV.setText("Status: " + os.getStatus());

                        if (opisyMap.containsKey(tempId)) {
                            opisB.setText(opisyMap.get(tempId));
                        }
                        else {
                            opisB.setText("Dodaj opis");
                        }

                        if(os.getGender().equals("Female")){
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.pink));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkPink));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkPink));
                            losujNastepneB.setTextColor(getResources().getColor(R.color.pink));
                            losujNastepneB.setBackgroundColor(getResources().getColor(R.color.darkPink));
                            szukajB.setTextColor(getResources().getColor(R.color.pink));
                            szukajB.setBackgroundColor(getResources().getColor(R.color.darkPink));
                            idET.setTextColor(getResources().getColor(R.color.darkPink));
                            idET.setHintTextColor(getResources().getColor(R.color.middlePink));
                            opisET.setTextColor(getResources().getColor(R.color.darkPink));
                            opisET.setHintTextColor(getResources().getColor(R.color.middlePink));
                            opisB.setTextColor(getResources().getColor(R.color.darkPink));
                            opisB.setBackgroundColor(getResources().getColor(R.color.pink));


                        }
                        else if (os.getGender().equals("Male")){
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.blue));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkBlue));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkBlue));
                            losujNastepneB.setTextColor(getResources().getColor(R.color.blue));
                            losujNastepneB.setBackgroundColor(getResources().getColor(R.color.darkBlue));
                            szukajB.setTextColor(getResources().getColor(R.color.blue));
                            szukajB.setBackgroundColor(getResources().getColor(R.color.darkBlue));
                            idET.setTextColor(getResources().getColor(R.color.darkBlue));
                            idET.setHintTextColor(getResources().getColor(R.color.middleBlue));
                            opisET.setTextColor(getResources().getColor(R.color.darkBlue));
                            opisET.setHintTextColor(getResources().getColor(R.color.middleBlue));
                            opisB.setTextColor(getResources().getColor(R.color.darkBlue));
                            opisB.setBackgroundColor(getResources().getColor(R.color.blue));



                        } else if (os.getGender().equals("unknown")) {
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.green));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkGreen));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkGreen));
                            losujNastepneB.setTextColor(getResources().getColor(R.color.green));
                            losujNastepneB.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                            szukajB.setTextColor(getResources().getColor(R.color.green));
                            szukajB.setBackgroundColor(getResources().getColor(R.color.darkGreen));
                            idET.setTextColor(getResources().getColor(R.color.darkGreen));
                            idET.setHintTextColor(getResources().getColor(R.color.middleGreen));
                            opisET.setTextColor(getResources().getColor(R.color.darkGreen));
                            opisET.setHintTextColor(getResources().getColor(R.color.middleGreen));
                            opisB.setTextColor(getResources().getColor(R.color.darkGreen));
                            opisB.setBackgroundColor(getResources().getColor(R.color.green));


                        }
                        else {
                            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.gray));
                            nazwaTV.setTextColor(getResources().getColor(R.color.darkGray));
                            gatunekTV.setTextColor(getResources().getColor(R.color.darkGray));
                            losujNastepneB.setTextColor(getResources().getColor(R.color.gray));
                            losujNastepneB.setBackgroundColor(getResources().getColor(R.color.darkGray));
                            szukajB.setTextColor(getResources().getColor(R.color.gray));
                            szukajB.setBackgroundColor(getResources().getColor(R.color.darkGray));
                            idET.setTextColor(getResources().getColor(R.color.darkGray));
                            idET.setHintTextColor(getResources().getColor(R.color.middleGray));
                            opisET.setTextColor(getResources().getColor(R.color.darkGray));
                            opisET.setHintTextColor(getResources().getColor(R.color.middleGray));
                            opisB.setTextColor(getResources().getColor(R.color.darkGray));
                            opisB.setBackgroundColor(getResources().getColor(R.color.gray));

                        }
                        if(os.getStatus().equals("Alive")){
                            statusTV.setTextColor(getResources().getColor(R.color.darkGreen));
                        }
                        else if (os.getStatus().equals("Dead")){
                            statusTV.setTextColor(getResources().getColor(R.color.darkRed));
                        }
                        else {
                            statusTV.setTextColor(getResources().getColor(R.color.darkYellow));
                        }

                    }

                    @Override
                    public void onFailure(Call<Osoba> call, Throwable t) {

                    }
                }
        );
    }

}