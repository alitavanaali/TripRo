package alitavana.com.tripro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarConstants;

import alitavana.com.tripro.AliPersianCalendar;
import alitavana.com.tripro.R;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.ClickNumberPickerView;
import pl.polak.clicknumberpicker.PickerClickType;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.Adault;
import static alitavana.com.tripro.activity.MainActivity.Children;
import static alitavana.com.tripro.activity.MainActivity.DateEnter;
import static alitavana.com.tripro.activity.MainActivity.DateExit;
import static alitavana.com.tripro.activity.MainActivity.Room;
import static com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarConstants.persianMonthNames;

/**
 * Created by Ali Tavana on 16/04/2017.
 */

public class DatePickerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    LinearLayout enter_date_picker_layout, exit_date_picker_layout;
    TextView exit_date_textview, exit_date_weekday_textview, enter_date_textview, enter_date_weekday_textview;
    ClickNumberPickerView room_number_picker, adault_number_picker, children_number_picker;
    AliPersianCalendar dateVorood, dateKhorooj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker_activity);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getComponents();
        setClickListeners();
    }


    private void getComponents() {
        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_header);
        toolbar_header.setText("انتخاب روزها");

        enter_date_textview = (TextView) findViewById(R.id.enter_date_textview);
        enter_date_weekday_textview = (TextView) findViewById(R.id.enter_date_weekday_textview);
        exit_date_textview = (TextView) findViewById(R.id.exit_date_textview);
        exit_date_weekday_textview = (TextView) findViewById(R.id.exit_date_weekday_textview);

        enter_date_picker_layout = (LinearLayout) findViewById(R.id.enter_date_picker_layout);
        exit_date_picker_layout = (LinearLayout) findViewById(R.id.exit_date_picker_layout);

        room_number_picker = (ClickNumberPickerView) findViewById(R.id.room_number_picker);
        adault_number_picker = (ClickNumberPickerView) findViewById(R.id.adault_number_picker);
        children_number_picker = (ClickNumberPickerView) findViewById(R.id.children_number_picker);
    }

    private void setClickListeners() {
        enter_date_picker_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatePickerActivity.this, DatePickerActivityHotel.class);
                startActivity(intent);
            }
        });
        exit_date_picker_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatePickerActivity.this, DatePickerActivityHotel.class);
                startActivity(intent);
            }
        });
        room_number_picker.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                Room = (int) currentValue;
            }
        });
        adault_number_picker.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                Adault = (int) currentValue;
            }
        });
        children_number_picker.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                Children = (int) currentValue;
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        dateVorood = new AliPersianCalendar(DateEnter.getYear(), DateEnter.getMonth(), DateEnter.getDayOfMonth());
        dateKhorooj = new AliPersianCalendar(DateExit.getYear(), DateExit.getMonth(), DateExit.getDayOfMonth());

        enter_date_textview.setText(dateVorood.getDayOfMonth() + " " + dateVorood.getPersianMonthName());
        enter_date_weekday_textview.setText(dateVorood.getPersianDayName());
        exit_date_textview.setText(dateKhorooj.getDayOfMonth() + " " + dateKhorooj.getPersianMonthName());
        exit_date_weekday_textview.setText(dateKhorooj.getPersianDayName());

        room_number_picker.setPickerValue(Room);
        adault_number_picker.setPickerValue(Adault);
        children_number_picker.setPickerValue(Children);
    }
}
