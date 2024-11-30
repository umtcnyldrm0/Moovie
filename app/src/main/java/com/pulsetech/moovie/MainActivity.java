package com.pulsetech.moovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private MovieAPI movieApi;
    private String baseUrl = "https://api.themoviedb.org/3/";

    private String apiKey = "b3d848f00235d20a3ff523f2155c207c";
    private Call<MoviesClass> moviesCall;
    int page = 1;
    private MoviesHolder.MovieAdapter movieAdapter;
    private List<MoviesHolder.Movie> movieList;
    private MoviesClass moviesClass;
    private Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecyclerView);
        nextPage = findViewById(R.id.buttonNextPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        movieAdapter = new MoviesHolder.MovieAdapter(movieList);
        recyclerView.setAdapter(movieAdapter);

        setRetrofitSettings();

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = page + 1;
                moviesCall = movieApi.getPopularMovies(apiKey, page);
                moviesCall.enqueue(new Callback<MoviesClass>() {
                    @Override
                    public void onResponse(Call<MoviesClass> call, Response<MoviesClass> response) {
                        if (response.isSuccessful()) {
                            MoviesClass moviesResponse = response.body();
                            if (moviesResponse != null) {
                                movieList.clear();
                                moviesClass = response.body();
                                for (MoviesClass.Movie movie : moviesClass.getMovies()) {
                                    // Null kontrolü yaparak yalnızca başlığı null olmayan filmleri ekle
                                    if (movie.getTitle() != null && !movie.getTitle().isEmpty() && movie.getDescription() != null && movie.getImageUrl() != null) {
                                        movieList.add(new MoviesHolder.Movie(movie.getTitle(), movie.getDescription(), movie.getImageUrl(), movie.getVote()));
                                    } else {
                                        Log.d("NullData", "Başlığı null olan bir film atlandı: " + movie.toString());
                                    }
                                }
                                movieAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesClass> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
            }
        });
    }

    private void setRetrofitSettings() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("Content-Type", "application/json; charset=UTF-8")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi = retrofit.create(MovieAPI.class);
        moviesCall = movieApi.getPopularMovies(apiKey, page);

        moviesCall.enqueue(new Callback<MoviesClass>() {
            @Override
            public void onResponse(Call<MoviesClass> call, Response<MoviesClass> response) {
                if (response.isSuccessful()) {
                    moviesClass = response.body();
                    if (moviesClass != null && moviesClass.getMovies() != null) {
                        movieList.clear();
                        Log.d("API_RESPONSE", "Veri başarılı şekilde geldi: " + moviesClass.getMovies().size());
                        for (MoviesClass.Movie movie : moviesClass.getMovies()) {
                            // Null kontrolü yaparak yalnızca başlığı null olmayan filmleri ekle
                            if (movie.getTitle() != null && !movie.getTitle().isEmpty() && movie.getDescription() != null && movie.getImageUrl() != null) {
                                movieList.add(new MoviesHolder.Movie(movie.getTitle(), movie.getDescription(), movie.getImageUrl(), movie.getVote()));
                            } else {
                                Log.d("NullData", "Başlığı null olan bir film atlandı: " + movie.toString());
                            }
                        }
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("API_RESPONSE", "Veri null veya boş");
                    }
                } else {
                    Log.d("API_RESPONSE", "API Çağrısı başarısız oldu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MoviesClass> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
}
