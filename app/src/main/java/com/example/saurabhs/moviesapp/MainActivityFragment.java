package com.example.saurabhs.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ImageAdapter mImageAdapter = null;
    List<MovieDetailsVO> movieDetailsVO;

    private Integer[] mImageList = {
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar,
            R.drawable.interstellar
    };

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onStart(){
        super.onStart();
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute("xyz");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute("xyz");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        // adding grid view code
        GridView gridView = (GridView)rootView.findViewById(R.id.gridViewW);
        mImageAdapter = new ImageAdapter(rootView.getContext(),null,mImageList);
        gridView.setAdapter(mImageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("movie_details",movieDetailsVO.get(position));
                startActivity(intent);
            }
        });
        // adding grid view code
        return rootView;
    }

    public class ImageAdapter extends BaseAdapter{

        private Context mContext;
        private List<String> moviesImageUrl;
        private Integer[] imageResourceIdList;

        public ImageAdapter(Context context,List<String> mImageStringList,Integer[] mImageList){
            this.mContext = context;
            this.moviesImageUrl = mImageStringList;
            this.imageResourceIdList = mImageList;
        }
        @Override
        public int getCount() {
            if(null == moviesImageUrl || moviesImageUrl.isEmpty()) {
                return imageResourceIdList.length;
            }else{
                return moviesImageUrl.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void updateResults(List<String> changedUrls){
            this.moviesImageUrl = changedUrls;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomImageView imageView = new CustomImageView(mContext);
            /*imageView.setImageResource(mImageList[position]);*/
            if(null == moviesImageUrl || moviesImageUrl.isEmpty()) {
                Picasso.with(mContext).load(imageResourceIdList[position]).into(imageView);
            }else{
                Picasso.with(mContext).load(moviesImageUrl.get(position)).into(imageView);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // setting layout parameters
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(550,825);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.setMargins(0,0,0,0);
            imageView.setLayoutParams(layoutParams);
            // setting layout parameters
            imageView.setAdjustViewBounds(true);
            return imageView;
        }

    }

        public class FetchMoviesTask extends AsyncTask<String,Void,String[]> {


        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviesJsonStr = null;

            try{
                final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
                final String API_KEY = "api_key";
                Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon().appendQueryParameter(API_KEY,BuildConfig.THE_MOVIE_DB_API_KEY).build();

                URL  url = new URL(builtUri.toString());

                //create the Request to movies API
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();

            }catch (IOException e) {
                return null;
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }               }
            }

            try{
                String[] results = moviesDataFromJson(moviesJsonStr);
                return results;
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        private String[] moviesDataFromJson(String moviesJsonString) throws JSONException{

            String MOVIES_RESULTS = "results";
            String MOVIES_POSTER_PATH = "poster_path";
            String MOVIES_RELEASE_DATE = "release_date";
            String MOVIES_ID = "id";
            String MOVIES_TITLE= "original_title";
            String MOVIES_OVERVIEW = "overview";
            String MOVIE_RATING = "vote_average";
            String[] results = null;

            ArrayList<String> posterPath = new ArrayList<String>();

            JSONObject jsonObj = new JSONObject(moviesJsonString);
            JSONArray moviesArray = jsonObj.getJSONArray(MOVIES_RESULTS);

            if(null == movieDetailsVO){
                movieDetailsVO = new ArrayList<MovieDetailsVO>();
            }else{
                movieDetailsVO.clear();
            }

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject eachMovie = moviesArray.getJSONObject(i);
                String eacMoviePosterPath = "http://image.tmdb.org/t/p/w185//" + eachMovie.getString(MOVIES_POSTER_PATH);
                posterPath.add(eacMoviePosterPath);
                MovieDetailsVO muviDetail = new MovieDetailsVO(eachMovie.getString(MOVIES_POSTER_PATH), eachMovie.getString(MOVIES_RELEASE_DATE), eachMovie.getString(MOVIES_TITLE), eachMovie.getString(MOVIES_OVERVIEW), eachMovie.getInt(MOVIES_ID), eachMovie.getDouble(MOVIE_RATING));
                movieDetailsVO.add(muviDetail);
            }
            results = (String[])posterPath.toArray(new String[0]);

            return results;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mImageAdapter.updateResults(Arrays.asList(result));
            }
        }
    }

}
