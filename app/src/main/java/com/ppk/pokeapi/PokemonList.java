package com.ppk.pokeapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonList {
    @SerializedName("results")
    private List<Pokemon> pokemonList;

    public PokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
}
