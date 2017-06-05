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
        Log.i("AliPersianCalendar", year + "/" + month + "/" + day);
    }

    public String getPersianDayName() {
        /*String persianDay = "";*/
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setPersianDate(year, month - 1, day);
        Log.i("AliPersianCalendar", persianCalendar.getPersianLongDate());
        Log.i("AliPersianCalendar", persianCalendar.getPersianWeekDayName());
        return persianCalendar.getPersianWeekDayName();
    }

    public String getFullDateStyle() {
        return year + "/" + month + "/" + day;
    }

    public String getPersianMonthName() {
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

    public int dayBetweenTwoDate(PersianDate dateEnter, PersianDate dateExit) {
        int enterDayOfYear = 0;
        int exitDayOfYear = 0;
        if (dateEnter.getYear() == dateExit.getYear()) {
            Log.i("DatePicker", "dateEnter.getMonth(): " + dateEnter.getMonth() + " dateExit.getMonth(): " + dateExit.getMonth());
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
        } else {
            // calculate how many days are from first day of year
            if (!dateEnter.isLeapYear()) {
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
            } else {
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

    public PersianDate getTomorrow(PersianDate today) {
        int tomorrowYear, tomorrowMonth, tomorrowDay;
        // 6 maahe avvale saal
        tomorrowDay = today.getDayOfMonth();
        tomorrowMonth = today.getMonth();
        tomorrowYear = today.getYear();
        tomorrowDay++;
        if (today.getMonth() <= 6) {
            if (tomorrowDay > 31) {
                tomorrowDay = 1;
                tomorrowMonth++;
            }
        }
        // 6 maahe dovome saal
        else {
            if (tomorrowMonth != 12) {
                if (tomorrowDay > 30) {
                    tomorrowDay = 1;
                    tomorrowMonth++;
                }
            }
            // yani mahe 12 hastim va bayad check konim ke sale kabise nabashe
            else {
                // sale kabise
                if (today.isLeapYear()){
                    if (tomorrowDay > 30) {
                        tomorrowDay = 1;
                        tomorrowMonth = 1;
                        tomorrowYear ++;
                    }
                }
                // sale mamooli
                else {
                    if (tomorrowDay > 29) {
                        tomorrowDay = 1;
                        tomorrowMonth = 1;
                        tomorrowYear ++;
                    }
                }
            }
        }
        return new PersianDate(tomorrowYear, tomorrowMonth, tomorrowDay);
    }

}
