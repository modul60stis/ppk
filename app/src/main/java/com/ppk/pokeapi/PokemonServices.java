package com.ppk.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonServices {
    @GET("pokemon")
    Call<PokemonList> getPokemonList();
}
