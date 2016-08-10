package com.example.saurabhs.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View detailView = inflater.inflate(R.layout.fragment_detail, container, false);
        MovieDetailsVO mDetails = null;
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra("movie_details")){
            mDetails = (MovieDetailsVO)intent.getSerializableExtra("movie_details");
        }
        //movie name
        TextView movieName = (TextView)detailView.findViewById(R.id.movieNameView);
        movieName.setText(mDetails.getMovieTitle());
        //movie name

        //movie poster
        ImageView moviePoster = (ImageView)detailView.findViewById(R.id.posterView);
        final String POSTER_URL = "http://image.tmdb.org/t/p/w92/" + mDetails.getMoviePosterpath();
        Picasso.with(detailView.getContext()).load(POSTER_URL).into(moviePoster);
        //movie poster

        //release year
        TextView yearText = (TextView)detailView.findViewById(R.id.yearView);
        String releaseDate = mDetails.getMovieReleaseDate();
        String releaseYear = releaseDate.substring(0,4);
        yearText.setText(releaseYear);
        //release year

        //rating
        TextView ratingText = (TextView)detailView.findViewById(R.id.ratingView);
        String rating = mDetails.getMovieRating() + "";
        ratingText.setText(rating + "/10");
        //rating

        //overview
        TextView overView = (TextView)detailView.findViewById(R.id.textView);
        overView.setText(mDetails.getMovieOverview());
        //overview
        return detailView;
    }
}
