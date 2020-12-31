package com.ppk.pokeapi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataList;

    public PokemonAdapter(ArrayList<Pokemon> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("[APP]", getAdapterPosition()+" Clicked");
                }
            });
            name = itemView.findViewById(R.id.txt_poke_name);
        }
    }
}
