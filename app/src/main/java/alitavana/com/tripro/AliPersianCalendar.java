package alitavana.com.tripro;

import android.util.Log;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;

import ir.mirrajabi.persiancalendar.core.models.PersianDate;

/**
 * Created by Ali Tavana on 14/05/2017.
 */

public class AliPersianCalendar extends PersianDate {
    int year, month, day;

    public AliPersianCalendar(int year, int month, int day) {
        super(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
        Log.i("AliPersianCalendar", year + "/" +month + "/" +day );
    }

    public String getPersianDayName(){
        /*String persianDay = "";*/
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setPersianDate(year, month - 1, day);
        Log.i("AliPersianCalendar", persianCalendar.getPersianLongDate() );
        Log.i("AliPersianCalendar", persianCalendar.getPersianWeekDayName() );
        return persianCalendar.getPersianWeekDayName();
    }

    public String getFullDateStyle(){
        return year + "/" + month + "/" + day;
    }

    public String getPersianMonthName(){
        String month = "";
        switch (this.month) {
            case 1:
                month = "فروردین";
                break;
            case 2:
                month = "اردیبهشت";
            break;
            case 3:
                month = "خرداد";
            break;
            case 4:
                month = "تیر";
            break;
            case 5:
                month = "مرداد";
            break;
            case 6:
                month = "شهریور";
            break;
            case 7:
                month = "مهر";
            break;
            case 8:
                month = "آبان";
            break;
            case 9:
                month = "آذر";
            break;
            case 10:
                month = "دی";
            break;
            case 11:
                month = "بهمن";
            break;
            case 12:
                month = "اسفند";
            break;
            default:
                month = null;
            break;
        }
        return month;
    }

}
