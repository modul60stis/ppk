package com.ppk.pokeapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private ArrayList<Pokemon> pokemonsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new PokemonAdapter(pokemonsArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    void addData(){
        pokemonsArrayList = new ArrayList<>();
        pokemonsArrayList.add(new Pokemon("charmeleon"));
        pokemonsArrayList.add(new Pokemon("charizard"));
        pokemonsArrayList.add(new Pokemon("squirtle"));
        pokemonsArrayList.add(new Pokemon("wartortle"));

    }
}