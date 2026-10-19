package com.example.rickmorty;

import com.google.gson.annotations.SerializedName;

public class Osoba {

    private int id;
    private String name;
    private String species;
    private String gender;
    private String status;
    private String image;

    public String getImage() {
        return image;
    }
    public String getStatus() {
        return status;
    }
    public String getGender() {
        return gender;
    }

    public String getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Osoba{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
