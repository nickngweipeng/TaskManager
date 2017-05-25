package com.example.nickng.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 15056201 on 25/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASKNAME = "taskname";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASKNAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public void insertTask(String taskname, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, taskname);
        values.put(COLUMN_DESCRIPTION, desc);
        long result = db.insert(TABLE_TASK, null, values);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }

        db.close();
        Log.d("SQL Insert",""+ result); //id returned, shouldn’t be -1
    }

    public ArrayList<Task> getAllTask() {
        //TODO return records in Java objects
        ArrayList<Task> songList = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID +"," + COLUMN_TASKNAME + ", "
                + COLUMN_DESCRIPTION
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String taskname = cursor.getString(1);
                String description = cursor.getString(2);
                Task obj = new Task(id,taskname,description);
                songList.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songList;
    }

    public int updateTask(Task data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, data.getTaskName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }
}
