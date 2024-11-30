package com.pulsetech.moovie;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {
    @GET("trending/all/day?api_key=b3d848f00235d20a3ff523f2155c207c")

    Call<MoviesClass> getPopularMovies(@Query("b3d848f00235d20a3ff523f2155c207c") String apiKey, @Query("page") int page);


}
