package com.life.ammar.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by ammar on 07/03/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private List<Movie> movieList;
    private Context context;
    private boolean isTablet = Main.isTablet();
    public MoviesAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_grid_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTablet) {
                    DetailsFragment detailsFragment = new DetailsFragment();
                    Bundle args = new Bundle();
                    args.putInt("idAsInt",
                            movieList.get(MainFragment.recyclerView.getChildAdapterPosition(itemView)).getId());
                    detailsFragment.setArguments(args);
                    MainFragment.Gtransaction.replace(R.id.container2, detailsFragment);
                    MainFragment.Gtransaction.commit();
                } else {
                    Intent intent = new Intent(context, Details.class);
                    intent.putExtra("idAsInt",
                            movieList.get(MainFragment.recyclerView.getChildAdapterPosition(itemView)).getId());
                    context.startActivity(intent);
                }
            }
        });
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(context).load(String.valueOf(movie.getUrl())).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        public ImageView posterImage;
        MovieHolder(View view) {
            super(view);
            posterImage = (ImageView) view.findViewById(R.id.movie_grid_image);
        }
    }
}
