package com.pulsetech.moovie;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MoviesHolder {

    public static class Movie {
        private String title;
        private String description;
        private String imageUrl;
        private String vote;

        public Movie(String title, String description, String imageUrl, String vote) {
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
            this.vote = vote;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getVote() {
            return vote;
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView movieImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            movieImageView = itemView.findViewById(R.id.imageViewMovie);
        }

        public void bind(Movie movie) {
            titleTextView.setText(movie.getTitle());
            descriptionTextView.setText(movie.getDescription());

            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getImageUrl();
            Log.d("ImageURL", "Loading image from URL: " + imageUrl);

            Glide.with(movieImageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.dummy)
                    .into(movieImageView);
        }
    }

    static class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private final List<Movie> movieList;

        public MovieAdapter(List<Movie> movieList) {
            this.movieList = movieList;
        }

        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
            Movie movie = movieList.get(position);
            holder.bind(movie);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                    intent.putExtra("title", movie.getTitle());
                    intent.putExtra("description", movie.getDescription());
                    intent.putExtra("imageUrl", movie.getImageUrl());
                    intent.putExtra("vote", movie.getVote()); // Oy ortalamasını aktar
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }
}
