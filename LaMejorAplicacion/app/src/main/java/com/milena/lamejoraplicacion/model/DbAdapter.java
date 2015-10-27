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
         //initDatosLocos();
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
        insertSerie(DbHelper.ANIME, "One Piece", 613, "", -1);
        insertSerie(DbHelper.ANIME, "Naruto", 450, "", 2);
        insertSerie(DbHelper.ANIME, "Bleach", 58, "",-1);
        insertSerie(DbHelper.ANIME, "Tokyio Ghoul", 5, "", 1);
        insertSerie(DbHelper.TV_SHOW, "The Walking Dead", 6, "", 5);
        insertSerie(DbHelper.TV_SHOW, "Terriers", 6, "", 1);
     }

    public long insertSerie( int idType, String name, int chapter, String image, int season){
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("chapter", chapter);
        initialValues.put("image", image);
        initialValues.put("id_type", idType);
        initialValues.put("season", season);
        return db.insert("t_serie", null, initialValues);
    }

    public void updateSerie( long idSerie, int idType, String name, int episode, int season ){
        ContentValues initialValues = new ContentValues();
        initialValues.put("season", season);
        initialValues.put("chapter", episode);
        initialValues.put("name", name);
        initialValues.put("id_type", idType);
        db.update("t_serie", initialValues, "id = ?", new String[]{idSerie + ""});
    }

    public void updateChapter( long idSerie, int chapter ){
        ContentValues initialValues = new ContentValues();
        initialValues.put("chapter", chapter);
        db.update("t_serie", initialValues, "id = ?", new String[]{idSerie + ""});
    }

    public void updateSeason( long idSerie, int season ){
        ContentValues initialValues = new ContentValues();
        initialValues.put("season", season);
        db.update("t_serie", initialValues, "id = ?", new String[]{idSerie + ""});
    }

    public Cursor getSerieById(long idSerie){
        return db.rawQuery("SELECT id as _id, name, chapter, season FROM t_serie WHERE id="+idSerie, null);
    }

    public Cursor getAllItems( ){
        return db.rawQuery("SELECT id as _id, name, chapter, season FROM t_serie", null);
    }

    public void deleteSerie(long id){
        db.delete("t_serie", "id=?", new String[]{id + ""});
    }
}
