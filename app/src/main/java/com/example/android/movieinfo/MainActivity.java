package com.example.android.movieinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainActivityFragment mainActivityFragment = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mainActivityFragment)
                    .commit();
/*
        }else{
           MainActivityFragment mainActivityFragment = (MainActivityFragment) FragmentManager.getFragment(
                    savedInstanceState, "fragmentContent");
*/
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));

            //String prefSortOrder = prefs.getString(getString(R.string.sortBy_key),
              //      getString(R.string.sortBy_default_value));

            //MainActivityFragment.FetchMovieData MovieData = new MainActivityFragment.FetchMovieData();
            //MovieData.execute(prefSortOrder);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
