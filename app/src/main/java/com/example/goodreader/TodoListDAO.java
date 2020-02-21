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
        Cursor cursor = database.rawQuery("SELECT * FROM todo_list;",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            todoList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return todoList;
    }

    public void insertData(String nameStudent2, String classStudent2) {
        // TODO Auto-generated method stub
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(DbHelper.COLUMN_NAME, NameStudent);
        content.put(DbHelper.COLUMN_CLASS, ClassStudent);

        database.insert(AndroidDbHelper.TABLE_NAME, null, content);
    }
}
