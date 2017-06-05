package alitavana.com.tripro.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import alitavana.com.tripro.AliPersianCalendar;
import alitavana.com.tripro.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.Adault;
import static alitavana.com.tripro.activity.MainActivity.Children;
import static alitavana.com.tripro.activity.MainActivity.DateEnter;
import static alitavana.com.tripro.activity.MainActivity.DateExit;
import static alitavana.com.tripro.activity.MainActivity.MabdaCity;
import static alitavana.com.tripro.activity.MainActivity.MaghsadCity;

/**
 * Created by Ali Tavana on 21/04/2017.
 */

public class FlightsActivity extends AppCompatActivity {
    LinearLayout flight_mabda_linear, flight_maghsad_lnear, flight_day_linear;
    TextView flight_mabda, flight_mabda_airport, flight_mabda_airport_location,
            flight_maghsad, flight_maghsad_airport, flight_maghsad_airport_location, flight_way_type, flight_day, flight_passenger_numbers;
    Button flight_search_btn;
    AliPersianCalendar dateVorood, dateKhorooj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getComponents();
        setClicks();
    }

    private void getComponents() {
        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_header);
        toolbar_header.setText("پروازها");

        flight_mabda_linear = (LinearLayout) findViewById(R.id.flight_mabda_linear);
        flight_maghsad_lnear = (LinearLayout) findViewById(R.id.flight_maghsad_lnear);
        flight_day_linear = (LinearLayout) findViewById(R.id.flight_day_linear);

        flight_mabda = (TextView) findViewById(R.id.flight_mabda);
        flight_mabda_airport = (TextView) findViewById(R.id.flight_mabda_airport);
        flight_mabda_airport_location = (TextView) findViewById(R.id.flight_mabda_airport_location);
        flight_maghsad = (TextView) findViewById(R.id.flight_maghsad);
        flight_maghsad_airport = (TextView) findViewById(R.id.flight_maghsad_airport);
        flight_maghsad_airport_location = (TextView) findViewById(R.id.flight_maghsad_airport_location);
        flight_way_type = (TextView) findViewById(R.id.flight_way_type);
        flight_day = (TextView) findViewById(R.id.flight_day);
        flight_passenger_numbers = (TextView) findViewById(R.id.flight_passenger_numbers);

        flight_search_btn= (Button) findViewById(R.id.flight_search_btn);

    }

    private void setClicks() {
        flight_mabda_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightsActivity.this, SearchCityActivity.class);
                intent.putExtra("isMaghsad", false); // yani mabda
                startActivity(intent);
            }
        });

        flight_maghsad_lnear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightsActivity.this, SearchCityActivity.class);
                intent.putExtra("isMaghsad", true); // yani maghsad
                startActivity(intent);
            }
        });

        flight_day_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightsActivity.this, DatePickerActivityFlight.class);
                startActivity(intent);
            }
        });

        flight_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightsActivity.this, TicketListActivity.class);
                startActivity(intent);
            }
        });
        flight_passenger_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogConfig();
            }
        });
    }

    private void DialogConfig(){
        AlertDialog.Builder builder ;
        builder = new AlertDialog.Builder(FlightsActivity.this);
        LayoutInflater inflater = (FlightsActivity.this).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_passengers, null);
        builder.setView(v);
        builder.setCancelable(true);
        builder.create();
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                flight_passenger_numbers.setText(Adault + Children + " مسافر");
            }
        });
        builder.show();

        NumberPicker adault_number_picker = (NumberPicker) v.findViewById(R.id.adault_number_picker);
        adault_number_picker.setValue(Adault);
        adault_number_picker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                Adault = value;
            }
        });

        NumberPicker children_number_picker = (NumberPicker) v.findViewById(R.id.children_number_picker);
        children_number_picker.setValue(Children);
        children_number_picker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                Children = value;

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        dateVorood = new AliPersianCalendar(DateEnter.getYear(), DateEnter.getMonth(), DateEnter.getDayOfMonth());
        dateKhorooj = new AliPersianCalendar(DateExit.getYear(), DateExit.getMonth(), DateExit.getDayOfMonth());
        flight_day.setText(dateVorood.getPersianDayName() + " " + dateVorood.getFullDateStyle() + " - "+ dateKhorooj.getPersianDayName()
            + " " + dateKhorooj.getFullDateStyle());
        flight_passenger_numbers.setText(Adault + Children + " مسافر");
        if (MabdaCity != null) {
            flight_mabda.setText(MabdaCity);
            flight_mabda_airport.setText("فرودگاه مبدا");
            flight_mabda_airport_location.setText("فرودگاه مبدا");
        }
        if (MaghsadCity != null) {
            flight_maghsad.setText(MaghsadCity);
            flight_maghsad_airport.setText("فرودگاه مقصد");
            flight_maghsad_airport_location.setText("فرودگاه مقصد");
        }
    }
}
