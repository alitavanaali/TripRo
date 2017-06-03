package alitavana.com.tripro.database;

/**
 * Created by ali on 11/24/2015.
 * Database Shomareye 1
 * Database sakhte shode dar ghabl ra be barname add mikonad
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "DatabaseHelper";
    private final Context myContext;
    private static final String DATABASE_NAME = "tripro_db.sqlite";
    private static final int DATABASE_VERSION = 2;
    private String pathToSaveDBFile;

    private String TABLE_WORD = "word";

    private String META_WORDS = "words";

    public DatabaseHelper(Context context, String filePath) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        pathToSaveDBFile = new StringBuffer(filePath).append("/").append(DATABASE_NAME).toString();
    }

    public void prepareDatabase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.d(TAG, "Database exists.");
            int currentDBVersion = getVersionId();
            if (DATABASE_VERSION > currentDBVersion) {
                Log.d(TAG, "Database version is higher than old.");
                deleteDb();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        } else {
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            File file = new File(pathToSaveDBFile);
            checkDB = file.exists();
        } catch (SQLiteException e) {
            Log.d(TAG, e.getMessage());
        }
        return checkDB;
    }

    private void copyDataBase() throws IOException {
        OutputStream os = new FileOutputStream(pathToSaveDBFile);
        InputStream is = myContext.getAssets().open("sqlite/" + DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.flush();
        os.close();
    }

    public void deleteDb() {
        File file = new File(pathToSaveDBFile);
        if (file.exists()) {
            file.delete();
            Log.d(TAG, "Database deleted.");
        }
    }

    private int getVersionId() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT version_id FROM dbVersion";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int v = cursor.getInt(0);
        cursor.close();
        db.close();
        return v;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<String> getCities(){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT * FROM cities";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return list;
    }

    /*public void updateWeather(Weather weather){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();

        values.put("id", "1");
        values.put("city", weather.getCity());
        values.put("address", weather.getAddress());
        values.put("weather", weather.getWeather());
        values.put("wind", weather.getWind());
        values.put("temp", weather.getTemp());
        values.put("day1", weather.getDay1());
        values.put("day2", weather.getDay2());
        values.put("day3", weather.getDay3());
        values.put("day4", weather.getDay4());
        values.put("day5", weather.getDay5());
        values.put("image", weather.getImage());
        values.put("last_build", weather.getLast_build());
        String[] args = new String[]{weather.getId()};
        // updating row

        db.update(TABLE_WEATHER, values, "id =? ",
                args);
        Log.i("updateWeather", "updateWeather : done!!");
        db.close();
    }

    public Weather getWeather(){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String selectQuery = "SELECT * FROM weather WHERE id = '1'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        Weather weather = new Weather(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11),
                cursor.getString(12)
        );
        cursor.close();
        db.close();
        return weather;
    }
    public List<Question> getAllQuestion() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT konkoor_num, question_num, question_text, answer_one, answer_two, answer_three, answer_four, right_answer FROM Questions";
        Cursor cursor = db.rawQuery(query, null);
        List<Question> list = new ArrayList<Question>();
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setKonkoor_num(cursor.getString(0));
            question.setQuestion_num(cursor.getString(1));
            question.setQuestion_text(cursor.getString(2));
            question.setAnswer_one(cursor.getString(3));
            question.setAnswer_two(cursor.getString(4));
            question.setAnswer_three(cursor.getString(5));
            question.setAnswer_four(cursor.getString(6));
            question.setRight_answer(cursor.getString(7));
            list.add(question);
        }
        cursor.close();
        db.close();
        return list;
    }*/



}