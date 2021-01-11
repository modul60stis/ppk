# Pemrograman Platform Khusus (PPK)

## Penggunaan Retrofit

Buat temen-temen yang masih bingung gimana cara pake retrofit, mungkin ini sedikit membantu ya. Jadi kita pake retrofit untuk manggil API yang udah kita buat. Nah misal kita udah buat API dengan endpoint `/login`. Terus request datanya gini (disini saya misalkan ngebuat request nya menggunakan JSON, bukan form data).

```json
{
  "username": "hmmm",
  "password": "ini password"
}
```

terus response yang kita dapet kalo berhasil begini

```json
{
  "status": 200,
  "message": "login berhasil",
  "data": {
    "id": 1,
    "token": "ini misalnya token"
  }
}
```

atau kalo gagal begini

```json
{
  "status": 401,
  "message": "username tidak sesuai"
}
```

Sekarang buka android studio nya, yang pertama kali kita lakuin adalah nambahin si retrofit ke dependecy aplikasi kita. Buka `build.gradle` yang `(Module app)` tambahin di bagian dependecies terus di sync dulu biar ke download dependesinya.

```java
  implementation 'com.squareup.retrofit2:retrofit:2.9.0'
  implementation 'com.google.code.gson:gson:2.8.6'
  implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

Kalo udah, sekarang kita buat yang namanya Retrofit Instance, misalnya ini ku namain `RetrofitInstance.java` isinya kurleb begini

```java
package com.ppk.myapp.api.services; // ini sesuaikan dengan nama package nya

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthInstance {
    private static Retrofit retrofit;
    private static  String BASE_URL = "http://192.168.43.137/backend/";

    public static Retrofit getInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
```

Kalo masih inget design pattern singleton, ya ini singleton dipake disini. Untuk variavel `BASE_URL` sesuaikan dengan base url api yang kalian pake. oiya kalo misalnya nanti gak konek mungkin api nya cuma jalan di local. Kalo mau kebaca coba jalankan pake Apache biar bisa kebaca pake IP nya, jangan lupa juga matiin sementara firewall yang private networknya.

Kalo sudah, tadi kan kita buat ada 1 request sama 2 response ya. Itu semua kita buat POJO nya, POJO tu apa? POJO tu Plain Old Java Object, ya objek java yang sederhanalah bisa dibilang. Disini kita mau buat 3 POJO berarti, POJO yang dibuat disesuaikan dengan request atau response nya. Sekarang aku buat POJO kalo login berhasil, misal kunamain `LoginSuccess.java`, isinya kurleb begini

```java
package com.ppk.myapp.api.response; // ini sesuaikan dengan nama package nya

import com.google.gson.annotations.SerializedName;

public class LoginSuccess {
    @SerializedName("status")
    private int id;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    private static class Data {
        @SerializedName("id")
        private int id;
        @SerializedName("token")
        private String token;
    }
    
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return data.id;
    }

    public String getToken() {
        return data.token;
    }
}
```

Nah semua tipe data sama atribut di JSON response nya kita sesuaikan dengan POJO nya ya. Itu yang pake @ apaan? itu namanya annotation, bisa docari cari lagi apa aja kegunaannya kalo mau tau lebih lanjut. Sekarang kita buat POJO kalo login gagal. Disini saya namail `LoginFailed.java`, isinya begini.

```java
package com.ppk.myapp.api.response; // ini sesuaikan dengan nama package nya

import com.google.gson.annotations.SerializedName;

public class LoginFailed{
    @SerializedName("status")
    private int id;
    @SerializedName("message")
    private String message;
    
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
```

Ini karena atribut di JSON nya cuma `status` sama `message` ya berarti kita buat itu aja. Sekarang kita buat yang untuk request nya. Untuk request kita buat aja begini

```java
package com.ppk.myapp.api.request; // ini sesuaikan dengan nama package nya

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
```

Kita buat constructor disitu buar mudah kalo mau inisialiasinya. Kok gak pake annotation lagi? disini kita gak pake annotation buat di requestnya. Sekarang kita udah buat 3 POJO class. Selanjutnya kita buat Interface untuk manggil API yang udah kita buat. Buat file namanya `UserInterface.java` isinya kurleb begini

```java
package com.ppk.myapp.api; // ini sesuaikan dengan nama package nya

import com.ppk.myapp.api.request.LoginRequest; // ini sesuaikan dengan nama package nya
import com.ppk.myapp.api.response.LoginSuccess; // ini sesuaikan dengan nama package nya

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserInterface {

    @POST("login")
    Call<LoginSuccess> login(@Body LoginRequest loginRequest);
}
```

disini ada method login, tipe nya `Call<LoginSuccess>`  terus parametr didalemnya `LoginRequest`. disitu ada annotation `@Body` jadi nanti otomatis diubah jadi JSON si `loginRequest` nya. Kalo udah dibuat semua sekarang kita tinggal manggilnya. nah disini misalnya saya buat ya di `LoginActivity.java` XML nya kurang lebih begini.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".LoginActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="32dp"
        android:layout_marginTop="32dp"
        android:layout_marginEnd="32dp"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingBottom="36dp">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@string/sign_in"
                android:textAlignment="center"
                android:textSize="18sp"
                android:textStyle="bold" />
        </LinearLayout>

        <TextView
            android:id="@+id/txt_login_error"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingTop="20dp"
            android:paddingBottom="20dp"
            android:text="@string/text_error"
            android:textColor="#C62424"
            android:textStyle="italic"
            android:visibility="invisible" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingTop="10dp">

            <TextView
                android:id="@+id/label_username"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:labelFor="@id/txt_register_username"
                android:text="@string/username" />

            <EditText
                android:id="@+id/txt_login_username"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:ems="10"
                android:importantForAutofill="no"
                android:inputType="textPersonName" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingTop="10dp">

            <TextView
                android:id="@+id/label_password"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:labelFor="@id/txt_register_password"
                android:text="@string/password" />

            <EditText
                android:id="@+id/txt_login_password"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:ems="10"
                android:importantForAutofill="no"
                android:inputType="textPassword" />
        </LinearLayout>

        <Button
            android:id="@+id/btn_login"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/sign_in" />
    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

Terus buka `LoginActivity.java` terus di `onCreate` aku buat gini. Untuk lebih jelas penjelasan code nya baca aja komennya.

```java
public class LoginActivity extends AppCompatActivity {
    // disini aku inisialiasi dulu EdiText, Button, sama TextView nya.
    private EditText register_username;
    private EditText register_password;
    private Button btn_login;
    private TextView txt_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Konekin variabel yang udah di inisialiasi tadi ke View nya pake findViewById
        register_username = findViewById(R.id.txt_login_username);
        register_password = findViewById(R.id.txt_login_password);
        btn_login = findViewById(R.id.btn_login);
        txt_error = findViewById(R.id.txt_login_error);

        // Kan kalo di klik tombol login, dia ngirim data kan
        // disini saya tambahin clckListerner nya buat si btn_login
        // Disis ku buat jadi 1 aja, kalo kalian mau dibuat function baru biar lebih enak boleh banget
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ini kita buat object  LoginRequest baru pake constructor,
                // isinya dari data di EditText yang udah kita buat tadi
                LoginRequest loginRequest = new LoginRequest(register_username.getText().toString(), register_password.getText().toString());

                // Sekarang kita buat object buat UserInterface, biar kita nanti bisa manggil atau call api yang udah kita buat
                UserInterface userInterface = AuthInstance.getInstance().create(UserInterface.class);
                Call<LoginSuccess> call = userInterface.login(loginRequest);

                // ini kita jalainin deh request nya
                // Jadi, waktu manggil kan bisa aja kita manggil tapi gak nyampe,
                // misal karena jaringan, atau gak konek ke server, dll itu masuknya di onFailure.
                // Kalo misalnya berhasil ya masuk di onResponse
                call.enqueue(new Callback<LoginSuccess>() {
                    @Override
                    public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                        
                        // Disini aku ngecek lagi apakah response nya success apa gagal,
                        // success atau gagal ini ditentuin pake HTTP response code, yang kayak 200, 401, 404, dll
                        // yang termasuk sukses itu keluarga 200, kayak 200, 201, dll. sisanya gak masuk success (kalo gak salah)
                        if(response.isSuccessful()){

                            // untuk ngambil isi dari JSON nya kita bisa pake response.body()
                            // jadi response.body() itu hasilnya object LoginSuccess
                            // terus data didalemnya tinggal panggil pake getter nya.
                            // Oiya disini tempat kalo mau ngasih logic, misal kalo login berhasil diarahin kemana
                            Log.d("[REST_API]", String.valueOf(response.body().getToken()));
                            Log.d("[REST_API]", String.valueOf(response.body().getId()));
                            finish();
                        } else {

                            // terus gimana kalo response nya itu response code nya 400 BAD REQUEST misalnya
                            // dia masuk kesini, disni kita mau ngambil response kalo misalnya dia gagal login dsb.
                            // disini agak berbeda sedikit cara ngambil response kalo failed dari yang success diatas.
                            Gson gson = new GsonBuilder().create();
                            LoginFailed loginResponse;
                            try {
                                loginResponse = gson.fromJson(response.errorBody().string(), LoginFailed.class);

                                txt_error.setText(loginResponse.getMessage());
                                txt_error.setVisibility(View.VISIBLE);
                                Log.d("[REST_API]", loginResponse.getMessage());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
```
