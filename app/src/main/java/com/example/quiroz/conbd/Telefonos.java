package com.example.quiroz.conbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.sql.SQLException;

/**
 * Created by Quiroz on 25/02/2015.
 */
public class Telefonos {

    public static final String ID_FILA="_id";
    public static final String ID_PERSONA = "nombre_persona";
    public static final String ID_TELEFONO="telefono_persona";

    public static final String N_BD= "Telefonos";
    public static final String N_TABLA="Tabla_Telefonos";
    public static final int VERSION_DB= 1;

    private BDHelper nHelper;
    private final Context nContexto;
    private SQLiteDatabase nBD;

    public String getN(Long lb) {
        String[] columnas = new String[] {ID_FILA, ID_PERSONA, ID_TELEFONO};
            Cursor c = nBD.query(N_TABLA, columnas, ID_FILA + "=" + lb, null, null, null, null);
        if(c != null){
            c.moveToFirst();
            String nb= c.getString(1);
            return nb;
        }

        return null;
    }

    public String getT(Long lb) {
        String[] columnas = new String[] {ID_FILA, ID_PERSONA, ID_TELEFONO};
        Cursor c = nBD.query(N_TABLA, columnas, ID_FILA + "=" + lb, null, null, null, null);
        if(c != null){
            c.moveToFirst();
            String nb= c.getString(2);
            return nb;
        }
        return null;
    }

    public void editar(long eFilal, String eNom, String eTel) throws SQLException{
        ContentValues cvEditar = new ContentValues();
        cvEditar.put(ID_PERSONA,eNom);
        cvEditar.put(ID_TELEFONO,eTel);
        nBD.update(N_TABLA, cvEditar, ID_FILA + "=" + eFilal, null);

    }

    public void borrar(long elFilal) {
        nBD.delete(N_TABLA, ID_FILA + "=" + elFilal, null);
    }

    public static class BDHelper extends SQLiteOpenHelper{

        public BDHelper(Context context) {
            super(context, N_BD, null, VERSION_DB);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(" Create table "+ N_TABLA + "(" + ID_FILA + " integer primary key autoincrement, " +
                    ID_PERSONA + " text not null, "+ ID_TELEFONO + " text not null);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists" + N_TABLA);
            onCreate(db);
        }
    }

    public Telefonos (Context C){
        nContexto=C;
    }

    public Telefonos abrir() throws Exception{
        nHelper= new BDHelper(nContexto);
        nBD= nHelper.getWritableDatabase();
        return this;
    }
    public void cerrar(){
        nHelper.close();
    }

    public long crearEntrada(String nom, String tel){
        ContentValues cv= new ContentValues();
        cv.put(ID_PERSONA, nom);
        cv.put(ID_TELEFONO, tel);
        return nBD.insert(N_TABLA, null, cv);

    }

    public String recibir(){
        String[] columnas = new String[]{ID_FILA, ID_PERSONA, ID_TELEFONO};
        Cursor c= nBD.query(N_TABLA, columnas, null, null, null, null, null);
        String resultado="";


        int iFila = c.getColumnIndex(ID_FILA);
        int iNombre= c.getColumnIndex(ID_PERSONA);
        int iTelefono= c.getColumnIndex(ID_TELEFONO);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            resultado= resultado +c.getString(iFila) + " " + c.getString(iNombre)+ " "+ c.getString(iTelefono)+ "\n";
        }
       return resultado;
    }

}

