package com.example.myapplication.bdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREACIÓN DE LAS TABLAS
        db.execSQL("CREATE TABLE tblUsuarios"+"(" +
                "usu_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "usu_funcionario text NOT NULL,"+
                "usu_cargo text NOT NULL," +
                "usu_atrasos text NOT NULL," +
                "usu_hijos integer NOT NULL," +
                "usu_estado text NOT NULL)");

    }


   /* @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CAMBIE LA VERSIÓN DE LA TABLA DE LA BDD
        db.execSQL("DROP TABLE tblUsuarios");
        onCreate(db);
    }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists tblUsuarios");
        db.execSQL("CREATE TABLE tblUsuarios"+"(" +
                "usu_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "usu_funcionario text NOT NULL,"+
                "usu_cargo text NOT NULL," +
                "usu_atrasos text NOT NULL," +
                "usu_despartamento text NOT NULL," +
                "usu_hijos integer NOT NULL," +
                "usu_estado text NOT NULL)");
    }



    public ArrayList<String> getAllRegistros()
    {

        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from  tblUsuarios", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add("funcionario:" + res.getString(0) +" | "+"Cargo"+" "+
                    res.getString(1) + " | "+ " Departamento" + res.getString(5) );
            res.moveToNext();}

        return array_list;
    }
}

