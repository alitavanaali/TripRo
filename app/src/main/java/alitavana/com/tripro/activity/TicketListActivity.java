package alitavana.com.tripro.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.TicketListAdapter;
import alitavana.com.tripro.model.Ticket;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.DateEnter;
import static alitavana.com.tripro.activity.MainActivity.DateExit;
import static alitavana.com.tripro.activity.MainActivity.MabdaCity;
import static alitavana.com.tripro.activity.MainActivity.MaghsadCity;

/**
 * Created by Ali Tavana on 13/05/2017.
 */

public class TicketListActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabLayout.Tab tabPrice, tabFastest, tabTime;
    Typeface irsans;
    ListView ticket_listview;
    TicketListAdapter ticketListAdapter;
    List<Ticket> ticketList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        getComponents();
        prepareDestinationsData();
        setTabs();
        setAdapter();
    }

    private void setTabs() {
        SpannableString spannablePrice = new SpannableString("قیمت");
        spannablePrice.setSpan(new CustomTypefaceSpan("", irsans), 0, spannablePrice.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString spannableFastest = new SpannableString("سریع ترین");
        spannableFastest.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableFastest.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString spannableTime = new SpannableString("اولین حرکت");
        spannableTime.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableTime.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        tabTime= tabLayout.newTab();
        tabTime.setText(spannableTime);
        tabFastest= tabLayout.newTab();
        tabFastest.setText(spannableFastest);
        tabPrice= tabLayout.newTab();
        tabPrice.setText(spannablePrice);


        tabLayout.addTab(tabTime);
        tabLayout.addTab(tabFastest);
        tabLayout.addTab(tabPrice);

        tabPrice.select();
    }

    private void setAdapter() {
        ticket_listview.setAdapter(ticketListAdapter);
    }

    private void getComponents() {
        irsans = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/irsans.ttf");

        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_header);
        /*toolbar_header.setText(MabdaCity.getName() + "-" + MaghsadCity.getName() + " " +
                DateEnter.getPersianMonth() + "/" + DateEnter.getPersianDay() + "-" +
                DateExit.getPersianMonth() + "/" + DateExit.getPersianDay());*/
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        ticket_listview = (ListView) findViewById(R.id.ticket_listview);
        ticketListAdapter = new TicketListAdapter(this, ticketList);

    }

    private void prepareDestinationsData() {
        Ticket ticket1 = new Ticket("تهران", "اصفهان", "1/112/000 ریال", "7:40 ق.ظ", "9:30 ق.ظ", "فرودگاه مهراباد", "فرودگاه نقش جهان", "01:35'");
        Ticket ticket2 = new Ticket("تهران", "اصفهان", "1/350/000 ریال", "7:40 ق.ظ", "9:30 ق.ظ", "فرودگاه مهراباد", "فرودگاه نقش جهان", "01:35'");
        Ticket ticket3 = new Ticket("تهران", "اصفهان", "1/470/000 ریال", "7:40 ق.ظ", "9:30 ق.ظ", "فرودگاه مهراباد", "فرودگاه نقش جهان", "01:35'");
        ticketList.add(ticket1);
        ticketList.add(ticket2);
        ticketList.add(ticket3);
        ticketList.add(ticket1);
        ticketList.add(ticket2);
        ticketList.add(ticket3);
        ticketList.add(ticket1);
        ticketList.add(ticket2);
        ticketList.add(ticket3);
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
