/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = "TEST_LOGS";
    //EarthquakeActivity.class.getName()
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthquakeAdapter mAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link TextView} and {@link ProgressBar} in the layout
        emptyView = (TextView) findViewById(R.id.empty_state_TextView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_list);
        earthquakeListView.setEmptyView(emptyView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

/*        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, USGS_REQUEST_URL);*/

        // Check for network connectivity
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            // Get instance of LoaderManager and start the loader
            Log.i(LOG_TAG, "initLoader() Called");
            getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }else{
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        // Creating a new EarthquakeLoader with this URL
        Log.i(LOG_TAG, "onCreateLoader() Called");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        // Set empty state text to display "No earthquakes found."
        emptyView.setText(R.string.empty_state_message);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            mAdapter.addAll(data);
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(EarthquakeActivity.this, "Error Populating List", Toast.LENGTH_LONG).show();
        }
        Log.i(LOG_TAG, "onLoadFinished() Called");
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        Log.i(LOG_TAG, "onLoaderReset() Called");
    }
/*

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        */
/**
 * This method runs on a background thread and performs the network request.
 * We should not update the UI from a background thread, so we return a list of
 * {@link Earthquake}s as the result.
 *//*

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        */
/**
 * This method runs on the main UI thread after the background work has been
 * completed. This method receives as input, the return value from the doInBackground()
 * method. First we clear out the adapter, to get rid of earthquake data from a previous
 * query to USGS. Then we update the adapter with the new list of earthquakes,
 * which will trigger the ListView to re-populate its list items.
 *//*

        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            } else {
                Toast.makeText(EarthquakeActivity.this, "Error Populating List", Toast.LENGTH_LONG).show();
            }

        }

    }
*/


}
