package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

//import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Created by Jack on 6/1/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;
    public static final String LOG_TAG = "TEST_LOGS";
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading() Called");
        forceLoad();
    }
    @Override
    public List<Earthquake> loadInBackground() {

        if(mUrl == null)
            return null;

        List<Earthquake> result = QueryUtils.fetchEarthquakeData(mUrl);
        Log.i(LOG_TAG, "loadInBackground() Called");
        return result;
    }
}
