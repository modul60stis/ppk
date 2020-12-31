# Pemrograman Platform Khusus (PPK)

## Overview

Project ini ditujukan untuk belajar PPK biar UAS nya mantep. Nah disini project yang dibuat adalah project yang ngebahas beberapa hal yaitu
[X] Ngebuat Recycler View
[X] Ngambil data dari REST API pake RETROFIT 2
[X] Gabungin data dari REST API ke Recycler View
[ ] Simpen ke database local
[ ] Dan belum kepikiran mau dibuat apa lagi ...

Nah disini saya pake API dari Poke Api, REST API yang bisa diakses gratis isinya tentang Pokemon-Pokemon

![Bulbasour](https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png)

Yang dibutuhin apa aja nih???
- Android Studio (Jelas dong, krn disini kita pake Java, bukan React Native atau Flutter)
- Laptop/Komputer yang kuat
- Internet (Krn datanya ngambil dari internet ya jelas dong kita butuh internet, tapi hari gini siapa juga yang masih gak ada internet)

## Penjelasan Source Code Sedikit (Recycler View Dulu)

Yang pertama nih yaitu recycler view nya. Jadi di recycler view ini kita butuh yang namanya RecyclerView, Adapter, ViewHolder. Kalo diibaratin gini lah gambarnya

![RecyclerView](https://user.oc-static.com/upload/2018/05/15/15263964435078_15139293366547_RecyclerView.png)

Gambar dari [OpenClassrooms](https://openclassrooms.com/)

Adapter itu yang ngehubungin antara Data, ViewHolder, sama RecyclerView nya. Data tadi kan berupa List gitu kan ya, nah setiap record atau row itu dihandle sama ViewHolder terus tinggal ditampilin deh di RecyclerView. Nah sekarang kita masuk ke kodingannya.

Pertama kita buat dulu file class yang namanya `Pokemon.java` ini buat class untuk setiap objek Pokemon nya, (inget inget OOP). isinya gak banyak kok cuma name, construtor, sama setter and getter doang kyk gini.
```java
package com.ppk.pokeapi;

public class Pokemon {
    private String name;

    public Pokemon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

Nah sekarang coba buka file `PokemonAdapter.java`, disitu ada beberapa method sama 1 class. nah method method itu yang kita butuhkan. 
- `getItemCount` ini untuk ngambil panjang row dari datanya
- `ViewHolder` ini class buat ViewHolder nya
- `onBindViewHolder` ini untuk masukin data ke VieHolder tadi
- `onCreateViewHolder` ini buat nampilin view nya buat si ViewHolder, jadi file XML untuk setiap row diload disini

Nah untuk masukin datanya, coba liat method `generateDataList` di class `MainActivity`, disitu ada caranya gimana ngehubungin data, ke adapter, terus ke recyclerview nya.

## Penjelasan Lagi untuk Retrofit (REST API)

eh ini yang ini ntar dulu ya.
