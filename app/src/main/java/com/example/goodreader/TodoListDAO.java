package com.example.goodreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TodoListDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public TodoListDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public  void close(){
        dbHelper.close();
    }

    public ArrayList<String> getAllTodoList(){
        ArrayList<String> todoList = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM Childent;",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            todoList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return todoList;
    }

    public void insertData(String Username, String  NameStudent,String School,String Age,String Birthday) {
        // TODO Auto-generated method stub
        ContentValues content = new ContentValues();
        content.put(DbHelper.COLUMN_USERNAME, Username);
        content.put(DbHelper.COLUMN_NAMESUR, NameStudent);
        content.put(DbHelper.COLUMN_SCHOOL, School);
        content.put(DbHelper.COLUMN_AGE, Age);
        content.put(DbHelper.COLUMN_BIRTHDAY, Birthday);

        database.insert(DbHelper.TABLE_NAME, null, content);
    }
}
