package alitavana.com.tripro.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.travijuu.numberpicker.library.NumberPicker;

import alitavana.com.tripro.R;

/**
 * Created by Ali Tavana on 01/06/2017.
 */

public class testActivity extends AppCompatActivity {
String LOG_TAG = "testtest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setMax(15);
        numberPicker.setMin(5);
        numberPicker.setValue(10);


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(LOG_TAG, "Place: " + place.getName());
                Log.i(LOG_TAG, "Place: " + place.getLatLng().latitude);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(LOG_TAG, "An error occurred: " + status);
            }
        });
        ImageView search_button = (ImageView)((LinearLayout)autocompleteFragment.getView()).getChildAt(0);
        search_button.setPadding(0,0,0,0);
        ImageView clear_button = (ImageView)((LinearLayout)autocompleteFragment.getView()).getChildAt(2);
        clear_button.setPadding(0,0,0,0);
        /*search_button.setVisibility(View.GONE);
        clear_button.setVisibility(View.GONE);
        clear_button.setImageDrawable(getResources().getDrawable(R.drawable.calendar_today));*/

        EditText searchbox = ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input));
        searchbox.setTextSize(19.0f);
        /*searchbox.setPadding(10,10,10,10);
        searchbox.setBackgroundResource(R.drawable.card_backgrond_no_radius);*/
        searchbox.setHint("نام مقصد یا هتل");

    }


}
