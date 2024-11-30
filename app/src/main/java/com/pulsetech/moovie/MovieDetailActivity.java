package com.pulsetech.moovie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView movieImageView = findViewById(R.id.movieImageView);
        TextView movieTitleTextView = findViewById(R.id.movieTitleTextView);
        TextView movieDescriptionTextView = findViewById(R.id.movieDescriptionTextView);
        TextView movieRatingTextView = findViewById(R.id.movieRatingTextView); // Oy ortalaması için TextView

        // Intent verilerini al
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        String vote = intent.getStringExtra("vote"); // Oy ortalaması

        // Verileri yerleştir
        movieTitleTextView.setText(title);
        movieDescriptionTextView.setText(description);
        movieRatingTextView.setText("Rating: " + vote + "/10"); // Oy ortalamasını göster
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + imageUrl).into(movieImageView);
    }
}
