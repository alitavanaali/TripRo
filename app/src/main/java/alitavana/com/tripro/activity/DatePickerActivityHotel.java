package alitavana.com.tripro.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import alitavana.com.tripro.R;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.DateEnter;
import static alitavana.com.tripro.activity.MainActivity.DateExit;
import static alitavana.com.tripro.activity.MainActivity.DayBetween;

/**
 * Created by Ali Tavana on 13/05/2017.
 */

public class DatePickerActivityHotel extends AppCompatActivity {
    TextView datepicker_next_month, datepicker_previous_month, datepicker_month_textview, datepicker_raft_tv, datepicker_bargasht_tv;
    Button datepicker_donebtn;
    PersianCalendarView persianCalendarView;
    PersianCalendarHandler calendarHandler;
    PersianDate today;
    Boolean isRaft = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker_hotel);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getComponents();
        setClickListeners();
    }

    private void setClickListeners() {
        datepicker_next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persianCalendarView.goToPreviousMonth();
            }
        });
        datepicker_previous_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persianCalendarView.goToNextMonth();
            }
        });
        calendarHandler.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate persianDate) {
                datepicker_month_textview.setText(calendarHandler.getMonthName(persianDate) +
                        " " + persianDate.getYear());
            }
        });
        datepicker_raft_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRaft = true;
                changeTabColor();
            }
        });
        datepicker_bargasht_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRaft = false;
                changeTabColor();
            }
        });
        calendarHandler.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate persianDate) {
                if (isRaft) {
                    DateEnter = persianDate;
                    datepicker_raft_tv.setText("تاریخ ورود" + "\n" + getPersianDateStyle(persianDate));
                    isRaft = false;
                    changeTabColor();
                    Log.i("DatePicker", DateEnter.isLeapYear() + "");
                } else {
                    DateExit = persianDate;
                    datepicker_bargasht_tv.setText("تاریخ خروج" + "\n" + getPersianDateStyle(persianDate));

                    // calculate days between
                    DayBetween = dayBetweenTwoDate(DateEnter, DateExit);
                }
            }
        });
        datepicker_donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getComponents() {
        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_header);
        toolbar_header.setText("انتخاب تاریخ ورود و خروج");

        datepicker_next_month = (TextView) findViewById(R.id.datepicker_next_month);
        datepicker_previous_month = (TextView) findViewById(R.id.datepicker_previous_month);
        datepicker_month_textview = (TextView) findViewById(R.id.datepicker_month_textview);
        datepicker_bargasht_tv = (TextView) findViewById(R.id.datepicker_bargasht_tv);
        datepicker_raft_tv = (TextView) findViewById(R.id.datepicker_raft_tv);

        datepicker_donebtn = (Button) findViewById(R.id.datepicker_donebtn);

        persianCalendarView = (PersianCalendarView) findViewById(R.id.persian_calendar);
        calendarHandler = persianCalendarView.getCalendar();
        today = calendarHandler.getToday();
    }

    private void changeTabColor() {
        if (isRaft) {
            datepicker_raft_tv.setBackgroundResource(R.color.colorPrimary);
            datepicker_raft_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            datepicker_bargasht_tv.setBackgroundResource(R.color.white);
            datepicker_bargasht_tv.setTextColor(ContextCompat.getColor(this, R.color.text_header_color));
        } else {
            datepicker_bargasht_tv.setBackgroundResource(R.color.colorPrimary);
            datepicker_bargasht_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            datepicker_raft_tv.setBackgroundResource(R.color.white);
            datepicker_raft_tv.setTextColor(ContextCompat.getColor(this, R.color.text_header_color));
        }
    }

    private int dayBetweenTwoDate(PersianDate dateEnter, PersianDate dateExit) {
        int enterDayOfYear = 0;
        int exitDayOfYear = 0;
        if (dateEnter.getYear() == dateExit.getYear()) {
            Log.i("DatePicker", "dateEnter.getMonth(): "+dateEnter.getMonth() + " dateExit.getMonth(): " +dateExit.getMonth());
            // calculate how many days are from first day of year
            enterDayOfYear += dateEnter.getDayOfMonth();
            if (dateEnter.getMonth() <= 7) {
                for (int i = dateEnter.getMonth(); i > 1; i--) {
                    enterDayOfYear = enterDayOfYear + 31;
                }
            } else {
                for (int j = dateEnter.getMonth() - 7; j != 0; j--) {
                    enterDayOfYear = enterDayOfYear + 30;
                }
                enterDayOfYear = enterDayOfYear + 186;
            }

            // calculate how many days are from first day of year
            exitDayOfYear += dateExit.getDayOfMonth();
            if (dateExit.getMonth() <= 7) {
                for (int i = dateExit.getMonth(); i > 1; i--) {
                    exitDayOfYear += 31;
                }
            } else {
                for (int j = dateExit.getMonth() - 7; j != 0; j--) {
                    exitDayOfYear += 30;
                }
                exitDayOfYear = exitDayOfYear + 186;
            }
            Log.i("DatePicker", "enterDayOfYear: " + enterDayOfYear + " exitDayOfYear: " + exitDayOfYear);
            return exitDayOfYear - enterDayOfYear;
        }
        else {
            // calculate how many days are from first day of year
            if (!dateEnter.isLeapYear()){
                enterDayOfYear += dateEnter.getDayOfMonth();
                if (dateEnter.getMonth() <= 7) {
                    for (int i = dateEnter.getMonth(); i > 1; i--) {
                        enterDayOfYear = enterDayOfYear + 31;
                    }
                } else {
                    for (int j = dateEnter.getMonth() - 7; j != 0; j--) {
                        enterDayOfYear = enterDayOfYear + 30;
                    }
                    enterDayOfYear = enterDayOfYear + 186;
                }
                enterDayOfYear = 365 - enterDayOfYear;
            }
            else {
                enterDayOfYear += dateEnter.getDayOfMonth();
                if (dateEnter.getMonth() <= 7) {
                    for (int i = dateEnter.getMonth(); i > 1; i--) {
                        enterDayOfYear = enterDayOfYear + 31;
                    }
                } else {
                    for (int j = dateEnter.getMonth() - 7; j != 0; j--) {
                        enterDayOfYear = enterDayOfYear + 30;
                    }
                    enterDayOfYear = enterDayOfYear + 186;
                }
                enterDayOfYear = 366 - enterDayOfYear;
            }
            // calculate how many days are from first day of year
            exitDayOfYear += dateExit.getDayOfMonth();
            if (dateExit.getMonth() <= 7) {
                for (int i = dateExit.getMonth(); i > 1; i--) {
                    exitDayOfYear += 31;
                }
            } else {
                for (int j = dateExit.getMonth() - 7; j != 0; j--) {
                    exitDayOfYear += 30;
                }
                exitDayOfYear = exitDayOfYear + 186;
            }
            Log.i("DatePicker", "enterDayOfYear: " + enterDayOfYear + " exitDayOfYear: " + exitDayOfYear);
            return exitDayOfYear + enterDayOfYear;
        }
    }

    private String getPersianDateStyle(PersianDate persianDate) {
        return persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth();
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeTabColor();
        datepicker_raft_tv.setText("تاریخ ورود" + "\n" + getPersianDateStyle(DateEnter));
        datepicker_bargasht_tv.setText("تاریخ خروج" + "\n" + getPersianDateStyle(DateExit));
        datepicker_month_textview.setText(calendarHandler.getMonthName(today) +
                " " + today.getYear());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
