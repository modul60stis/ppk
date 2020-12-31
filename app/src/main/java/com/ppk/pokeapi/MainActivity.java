package com.ppk.pokeapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonServices pokemonInterface = PokemonInstance.getInstance().create(PokemonServices.class);
        Call<PokemonList> call = pokemonInterface.getPokemonList();

        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                generateDataList(response.body().getPokemonList());
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                Log.e("[RES]", "Failed load data");
            }
        });
    }

    private void generateDataList(List<Pokemon> pokemonList){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        PokemonAdapter adapter = new PokemonAdapter(pokemonList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}