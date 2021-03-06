package com.example.android.movieinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private SharedPreferences prefs;
    String sortOrder;

    private ImageAdapter movieAdapter;

    List<MovieParse> movies = new ArrayList<MovieParse>();
    // 이 부분이 sunshine project와 차이점
    // detail data가 다름

    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortOrder = prefs.getString(getString(R.string.sortBy_key),
                getString(R.string.sortBy_default_value));

        if(savedInstanceState != null){
            ArrayList<MovieParse> storedMovies = new ArrayList<MovieParse>();
            storedMovies = savedInstanceState.getParcelableArrayList("stored_movies");
            movies.clear();
            movies.addAll(storedMovies);
            // Parcel한 movies에 data entry 추가
            // Lis<MovieParse> movies와 ArrayList<MovieParse> 로 구분한 이유?
            //  이 방식으로 하지 않으면 일일이 .add()에 의해 entry별로 추가해야 하지 않을까?
        }

        FetchMovieData MovieData = new FetchMovieData();
        MovieData.execute(sortOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new ImageAdapter(
                getActivity(),
                R.layout.image_ids_array,
                R.id.Image_Ids_Array,
                new ArrayList<String>());


        GridView Grid_ImageView = (GridView) rootView.findViewById(R.id.gridView_id);
        Grid_ImageView.setAdapter(movieAdapter);

        Grid_ImageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieParse movieInfo = movies.get(position);
<<<<<<< HEAD
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieInfo);
                        // .putParcelableArrayListExtra()
=======
                // click된 position의 moviedata를 parse하여 movieinfo에 담아
                // 그것을 intent의 EXTRA_TEXT로 전달
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, movieInfo);
>>>>>>> b2b4531074a542285dcc9b017b7ef5345510fa4c
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // get sort order to see if it has recently changed
        String prefSortOrder = prefs.getString(getString(R.string.sortBy_key),
                getString(R.string.sortBy_default_value));

        FetchMovieData MovieData = new FetchMovieData();
        MovieData.execute(prefSortOrder);
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
*/
/*            String prefSortOrder = prefs.getString(getString(R.string.sortBy_key),
                    getString(R.string.sortBy_default_value));

            FetchMovieData MovieData = new FetchMovieData();
            MovieData.execute(prefSortOrder);*//*

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
사실 이 전체가 불필요한 중복 코딩작업임
setting에 대한 것은 이미 MainActivity에서 정의하였으므로 backGround 작업을 위한 fragment에서
이것을 다시 지정할 필요가 없는 것임 */

    public class FetchMovieData extends AsyncTask<String, Void, List<MovieParse>> {

        private final String LOG_TAG = FetchMovieData.class.getSimpleName();
        private final String API_KEY = "ff10e727a726c97c184d7ffd154b9cc0";
        private final String MOVIE_POSTER_BASE = "http://image.tmdb.org/t/p/";
        private final String MOVIE_POSTER_SIZE = "w185";

        @Override
        protected List<MovieParse> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                //URL url = new URL("http://api.themoviedb.org/3/discover/movie?api_key=1b724d42398a0285a8846e297ee0440f");

                // Create the request to OpenWeatherMap, and open the connection
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                // poster의 base_url과 다름
                // TMDb API 참조
                final String SORT_BY = "sort_by";
                final String KEY = "api_key";
                String sortBy = params[0];

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY, sortBy)
                        .appendQueryParameter(KEY, API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
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
                Log.v(LOG_TAG, "JasonString" + moviesJsonStr);
                //calling method to get data from JSON string
                Log.v(LOG_TAG, "JasonString" + moviesJsonStr);

                try {
                    //forecastJsonStr_1 = getweatherDataFromJson(forecastJsonStr, 7);
                    return getMoviesDataFromJson(moviesJsonStr);
                    //Log.v(LOG_TAG,""+forecastJsonStr_1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return null;
        }


        private String getYear(String date) {
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            final Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(df.parse(date));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            return Integer.toString(cal.get(Calendar.YEAR));
        }

        private List<MovieParse> getMoviesDataFromJson(String moviesJsonStr) throws JSONException {

            // Items to extract
            final String ARRAY_OF_MOVIES = "results";
            final String ORIGINAL_TITLE = "original_title";
            final String POSTER_PATH = "poster_path";
            final String OVERVIEW = "overview";
            final String VOTE_AVERAGE = "vote_average";
            final String RELEASE_DATE = "release_date";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(ARRAY_OF_MOVIES);
            int moviesLength = moviesArray.length();
            //List<MovieParse> movies = new ArrayList<MovieParse>();
            //String[] PosterArray = new String[100];

            for (int i = 0; i < moviesLength; ++i) {

                // for each movie in the JSON object create a new
                // movie object with all the required data
                JSONObject movie = moviesArray.getJSONObject(i);
                String title = movie.getString(ORIGINAL_TITLE);

                //PosterArray[i] = MOVIE_POSTER_BASE + MOVIE_POSTER_SIZE + movie.getString(POSTER_PATH);
                String poster = MOVIE_POSTER_BASE + MOVIE_POSTER_SIZE + movie.getString(POSTER_PATH);

                Log.v(LOG_TAG, "poster Array:" + poster);

                String overview = movie.getString(OVERVIEW);
                String voteAverage = movie.getString(VOTE_AVERAGE);
                String releaseDate = getYear(movie.getString(RELEASE_DATE));

                movies.add(new MovieParse(title, poster, overview, voteAverage, releaseDate));
                // List<movieParse> movies에 .add()를 통해 entry 값 부여
                // 반복은 data flattenig이 목적인 movieParse class에서 할 일이 아님

            }

            return movies;


        }

        @Override
        protected void onPostExecute(List<MovieParse> results) {
            //super.onPostExecute(movieParses);
            if (results != null) {

                 movieAdapter.clear();
                for(MovieParse movie : results){
                    movieAdapter.add(movie.getPoster());
<<<<<<< HEAD
                    // .getPoster()는 movieParse class 내에 정의
=======
                    // .getPoster()는 MovieParse.class에 정의
>>>>>>> b2b4531074a542285dcc9b017b7ef5345510fa4c
                }

            }
        }
    }

}
