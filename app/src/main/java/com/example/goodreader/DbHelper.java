package com.example.goodreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String databaseName = "todolist.sqlite";
    private  static final int databaseVersion = 1;
    public static final String COLUMN_USERNAME= "name";
    public static final String COLUMN_SCHOOL= "school";
    public static final String COLUMN_NAME_SUR= "nameNsur";
    public static final String COLUMN_AGE= "age";
    public static final String COLUMN_BIRTHDAY= "birthday";
    Context myContext;

    private static final String tableCreateSQL = "CREATE TABLE todo_list("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "todo_text TEXT"+
            ");";

    public DbHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.myContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tableCreateSQL);
        String insertData1 = "INSERT INTO todo_list (todo_text) VALUES ('userkla');";
        sqLiteDatabase.execSQL(insertData1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
