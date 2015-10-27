package com.milena.lamejoraplicacion.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 15/09/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    public final static int ANIME = 1;
    public final static int TV_SHOW = 2;
    public final static int NOVELA = 3;
    public final static int LIBRO = 4;

    private String tableSerie = "CREATE TABLE t_serie (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_type INTEGER, " +
            "name TEXT," +
            "image TEXT," +
            "chapter INTEGER," +
            "season INTEGER" +
            ");";

    private String tableType = "CREATE TABLE t_chapter (" +
            "id_type INTEGER PRIMARY KEY, " +
            "description TEXT" +
            ");";

    public DbHelper(Context context) {
        super(context, "db_lamejor", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableSerie);
        db.execSQL(tableType);
        initType(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void initType( SQLiteDatabase db ){
        ContentValues initialValues = new ContentValues();
        initialValues.put("id_type",ANIME);
        initialValues.put("description","Serie de Anime");
        db.insert("t_chapter", null, initialValues);
        initialValues = new ContentValues();
        initialValues.put("id_type",TV_SHOW);
        initialValues.put("description","Serie de Television");
        db.insert("t_chapter", null, initialValues);
        initialValues = new ContentValues();
        initialValues.put("id_type",NOVELA);
        initialValues.put("description","Novela");
        db.insert("t_chapter", null, initialValues);
        initialValues = new ContentValues();
        initialValues.put("id_type",LIBRO);
        initialValues.put("description","Libro");
        db.insert("t_chapter", null, initialValues);
    }

}
