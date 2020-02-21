package com.example.goodreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String databaseName = "todolist.sqlite";
    private  static final int databaseVersion = 1;
    public static final String TABLE_NAME= "Childent";
    public static final String COLUMN_USERNAME= "name";
    public static final String COLUMN_SCHOOL= "school";
    public static final String COLUMN_NAMESUR= "nameNsur";
    public static final String COLUMN_AGE= "10";
    public static final String COLUMN_BIRTHDAY= "birthday";
    Context myContext;
    private static final String tableCreateSQL = "CREATE TABLE TABLE_NAME("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "COLUMN_USERNAME TEXT,"+
            "COLUMN_SCHOOL TEXT,"+
            "COLUMN_NAMESUR TEXT,"+
            "COLUMN_AGE TEXT,"+
            "COLUMN_BIRTHDAY TEXT"+
            ");";

    public DbHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.myContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tableCreateSQL);
        String insertData1 = "INSERT INTO "+ TABLE_NAME +
                "(COLUMN_USERNAME) VALUES ($COLUMN_USERNAME);" +
                "(COLUMN_NAMESUR) VALUES ($COLUMN_NAMESUR);" +
                "(COLUMN_SCHOOL) VALUES ($COLUMN_SCHOOL);" +
                "(COLUMN_AGE) VALUES ($COLUMN_AGE);" +
                "(COLUMN_BIRTHDAY) VALUES ($COLUMN_BIRTHDAY);" ;

        sqLiteDatabase.execSQL(insertData1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
