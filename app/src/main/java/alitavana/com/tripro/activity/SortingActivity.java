package alitavana.com.tripro.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import alitavana.com.tripro.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.IsPrice1;
import static alitavana.com.tripro.activity.MainActivity.IsPrice2;
import static alitavana.com.tripro.activity.MainActivity.IsPrice3;
import static alitavana.com.tripro.activity.MainActivity.IsPrice4;
import static alitavana.com.tripro.activity.MainActivity.SortingModel;

/**
 * Created by Ali Tavana on 06/05/2017.
 */

public class SortingActivity extends AppCompatActivity {
    Button sorting_rotbebandi, sorting_nozooli, sorting_soudi, sorting_masafat, sorting_price4,
            sorting_price3, sorting_price2, sorting_price1, sorting_emal_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_model);
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
        toolbar_header.setText("مرتب سازی");

        sorting_rotbebandi = (Button) findViewById(R.id.sorting_rotbebandi);
        sorting_nozooli = (Button) findViewById(R.id.sorting_nozooli);
        sorting_soudi = (Button) findViewById(R.id.sorting_soudi);
        sorting_masafat = (Button) findViewById(R.id.sorting_masafat);
        sorting_price4 = (Button) findViewById(R.id.sorting_price4);
        sorting_price3 = (Button) findViewById(R.id.sorting_price3);
        sorting_price2 = (Button) findViewById(R.id.sorting_price2);
        sorting_price1 = (Button) findViewById(R.id.sorting_price1);
        sorting_emal_btn = (Button) findViewById(R.id.sorting_emal_btn);
    }

    private void setClicks() {
        sorting_price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsPrice1)
                    IsPrice1 = true;
                else
                    IsPrice1 = false;
                setColor();

            }
        });
        sorting_price2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsPrice2)
                    IsPrice2 = true;
                else
                    IsPrice2 = false;
                setColor();
            }
        });
        sorting_price3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsPrice3)
                    IsPrice3 = true;
                else
                    IsPrice3 = false;
                setColor();
            }
        });
        sorting_price4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsPrice4)
                    IsPrice4 = true;
                else
                    IsPrice4 = false;
                setColor();
            }
        });
        sorting_emal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","ok");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void clearButtons() {
        sorting_rotbebandi.setCompoundDrawables(null, null, null, null);
        sorting_rotbebandi.setTextColor(getResources().getColor(R.color.black));
        sorting_nozooli.setCompoundDrawables(null, null, null, null);
        sorting_nozooli.setTextColor(getResources().getColor(R.color.black));
        sorting_soudi.setCompoundDrawables(null, null, null, null);
        sorting_soudi.setTextColor(getResources().getColor(R.color.black));
        sorting_masafat.setCompoundDrawables(null, null, null, null);
        sorting_masafat.setTextColor(getResources().getColor(R.color.black));
    }

    private void setColor() {
        if (IsPrice1) {
            sorting_price1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            sorting_price1.setTextColor(getResources().getColor(R.color.white));
        } else {
            sorting_price1.setBackgroundColor(getResources().getColor(R.color.white));
            sorting_price1.setTextColor(getResources().getColor(R.color.black));
        }
        if (IsPrice2) {
            sorting_price2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            sorting_price2.setTextColor(getResources().getColor(R.color.white));
        } else {
            sorting_price2.setBackgroundColor(getResources().getColor(R.color.white));
            sorting_price2.setTextColor(getResources().getColor(R.color.black));
        }
        if (IsPrice3) {
            sorting_price3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            sorting_price3.setTextColor(getResources().getColor(R.color.white));
        } else {
            sorting_price3.setBackgroundColor(getResources().getColor(R.color.white));
            sorting_price3.setTextColor(getResources().getColor(R.color.black));
        }
        if (IsPrice4) {
            sorting_price4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            sorting_price4.setTextColor(getResources().getColor(R.color.white));
        } else {
            sorting_price4.setBackgroundColor(getResources().getColor(R.color.white));
            sorting_price4.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void sortingClicksHandler(View view) {
        clearButtons();
        setSort(view.getId());
    }

    private void setSort(int number) {
        switch (number) {
            case R.id.sorting_rotbebandi:
                SortingModel = 1;
                sorting_rotbebandi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                sorting_rotbebandi.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.sorting_nozooli:
                SortingModel = 2;
                sorting_nozooli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                sorting_nozooli.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.sorting_soudi:
                SortingModel = 3;
                sorting_soudi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                sorting_soudi.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.sorting_masafat:
                SortingModel = 4;
                sorting_masafat.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                sorting_masafat.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;

        }
    }

    private void BootUpCheckUp() {
        switch (SortingModel) {
            case 1:
                clearButtons();
                setSort(R.id.sorting_rotbebandi);
                break;
            case 2:
                clearButtons();
                setSort(R.id.sorting_nozooli);
                break;
            case 3:
                clearButtons();
                setSort(R.id.sorting_soudi);
                break;
            case 4:
                clearButtons();
                setSort(R.id.sorting_masafat);
                break;
        }
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
        BootUpCheckUp();
        setColor();
    }
}
