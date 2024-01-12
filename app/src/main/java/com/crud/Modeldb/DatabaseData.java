package com.crud.Modeldb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.crud.Modeldb.DatabaseData;

public class DatabaseData extends SQLiteOpenHelper {
    
    private static int VERSI_DATABASE = 1;
    private static String NAMA_DATABASE = "DataKaryawan.db";
    public String NAMA_TABEL = "TabelKaryawan";
    public String KOLOM_NAMA = "NAMA";
    public String KOLOM_NIP = "NIP";
    public String ID = "id";
    
    public DatabaseData(Context ctx){
        
        super(ctx, NAMA_DATABASE, null, VERSI_DATABASE);

    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        
        String TABEL = String.format("CREATE TABLE %s", NAMA_TABEL);
        String KOLOM = String.format("(%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT)",ID, KOLOM_NAMA, KOLOM_NIP);
        database.execSQL(TABEL+KOLOM);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        
        // TODO: Implement this method
        String DROP_TABLE = "DROP TABLE IF EXISTS students";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
    
}
