package com.example.goodreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String databaseName = "Userdata.db";
    private    static final int databaseVersion = 1;
    public static final String TABLE_NAME = "Childent";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_SCHOOL = "School";
    public static final String COLUMN_NAMESUR = "Name";
    public static final String COLUMN_AGE = "Age";
    public static final String COLUMN_BIRTHDAY = "Birthday";
    public static final String COLUMN_SCORE1 = "Score1";
    public static final String COLUMN_SCORE2 = "Score2";
    Context myContext;
    private static final String tableCreateSQL = "CREATE TABLE " + TABLE_NAME +" ("+
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_NAMESUR + " TEXT, " +
            COLUMN_SCHOOL + " TEXT, " +
            COLUMN_AGE + " TEXT, " +
            COLUMN_BIRTHDAY + " TEXT, "+
            COLUMN_SCORE1 + " INT, " +
            COLUMN_SCORE2 + " INT " + ");";

    public DbHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.myContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tableCreateSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
//    public boolean insertDataUser(String username ,String name ,String school ,String age ,String birthday){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues= new ContentValues();
//
//        contentValues.put(COLUMN_USERNAME,username);
//        contentValues.put(COLUMN_NAMESUR,name);
//        contentValues.put(COLUMN_SCHOOL,school);
//        contentValues.put(COLUMN_AGE,age);
//        contentValues.put(COLUMN_BIRTHDAY,birthday);
//        long result = db.insert(TABLE_NAME, null,contentValues);
//        return  result != -1;
//    }

}
