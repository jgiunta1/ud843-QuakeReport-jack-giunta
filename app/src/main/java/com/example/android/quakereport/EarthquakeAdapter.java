package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Jack on 2/1/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();
    private static final String LOCATION_SEPARATOR = " of ";
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param earthquakes A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID earthquake_magnitude
        TextView earthquakeMagTextView = (TextView) listItemView.findViewById(R.id.earthquake_magnitude);
        // Get the magnitude number from the current Earthquake object and
        // set this text on the magnitude TextView
        double magnitude = currentEarthquake.getMagnitude();
        earthquakeMagTextView.setText(formatMagnitude(magnitude));
        GradientDrawable magCircle = (GradientDrawable) earthquakeMagTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magCircle.setColor(magnitudeColor);

        // Find the TextView in the list_item.xml layout with the ID earthquake_location
        TextView locPrimary = (TextView) listItemView.findViewById(R.id.earthquake_location_primary);
        TextView locSecondary = (TextView) listItemView.findViewById(R.id.earthquake_location_secondary);
        // Get the location from the current Earthquake object and
        // set this text on the location TextView
        String locFull = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;
        if(locFull.contains(LOCATION_SEPARATOR)){
            int splitIndex = locFull.indexOf(LOCATION_SEPARATOR);
            locPrimary.setText(locFull.substring(splitIndex + 4));
            locSecondary.setText(locFull.substring(0, splitIndex + 3));
        }else {
            locPrimary.setText(locFull);
            locSecondary.setText(R.string.near_the);
        }

        // Get Date Object From Earthquake Time
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView in the list_item.xml layout with the ID earthquake_date
        TextView earthquakeDateTextView = (TextView) listItemView.findViewById(R.id.earthquake_date);
        String formattedDate = formatDate(dateObject);
        earthquakeDateTextView.setText(formattedDate);

        // Find the TextView with ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.earthquake_time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magInt = (int) magnitude;
        int magnitudeColor;
        switch (magInt){
            case 0:
            case 1: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
            break;
            case 2: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
            break;
            case 3: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
            break;
            case 4: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
            break;
            case 5: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
            break;
            case 6: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
            break;
            case 7: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
            break;
            case 8: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
            break;
            case 9: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
            break;
            default: magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }

        return magnitudeColor;
    }
}
