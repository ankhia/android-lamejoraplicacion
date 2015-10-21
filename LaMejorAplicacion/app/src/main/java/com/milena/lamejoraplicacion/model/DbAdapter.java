package com.milena.lamejoraplicacion.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 29/09/2015.
 */
public class DbAdapter {

    private static DbAdapter dbAdapter;

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public static DbAdapter getInstance( Context context ){
        if( dbAdapter == null )
            dbAdapter = new DbAdapter( context );
        return dbAdapter;
    }

    private DbAdapter( Context ctx ){
         context = ctx;
         dbHelper = new DbHelper(context);
//         initDatosLocos();
    }

    public DbAdapter open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void initDatosLocos( ){
        open();
        insertSerie(DbHelper.ANIME, "One Piece", 1, "");
        insertSerie(DbHelper.ANIME, "Naruto", 2, "");
        insertSerie(DbHelper.ANIME, "Bleach", 3, "");
        insertSerie(DbHelper.ANIME, "Tokyio Ghoul", 4, "");
        insertSerie(DbHelper.ANIME, "Cowboy Bebop", 5, "");
        insertSerie(DbHelper.ANIME, "Una mas", 6, "");



    }

    public long insertSerie( int idType, String name, int chapter, String image){
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("chapter", chapter);
        initialValues.put("image", image);
        initialValues.put("id_type", idType);
        return db.insert("t_serie", null, initialValues);
    }

    public void updateChapter( long idSerie, int chapter ){
        ContentValues initialValues = new ContentValues();
        initialValues.put("chapter", chapter);
        db.update("t_serie", initialValues, "id = ?", new String[]{idSerie + ""});
    }

    public Cursor getAllItems( ){
        return db.rawQuery("SELECT id as _id, name, chapter FROM t_serie", null);
    }

}
