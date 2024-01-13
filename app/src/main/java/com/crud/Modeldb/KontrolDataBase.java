package com.crud.Modeldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.crud.Model.ModelUserData;
import java.util.ArrayList;
import java.util.List;

public class KontrolDataBase extends DatabaseData {

    public KontrolDataBase(Context ctx) {
        super(ctx);
    }

    public void InputData(ModelUserData modeluserdata) {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(KOLOM_NAMA, modeluserdata.getNAMA());
        contentvalues.put(KOLOM_NIP, modeluserdata.getNIP());
        getWritableDatabase().insert(NAMA_TABEL, null, contentvalues);
        close();
    }
    public boolean delete(String id){
        return getWritableDatabase().delete(NAMA_TABEL, String.format("id = '%s'", id), null) > 0;
    }
    
    public boolean Update(ModelUserData modeluserdata){
        String[] args = {String.valueOf(modeluserdata.getID())};
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(KOLOM_NAMA, modeluserdata.getNAMA());
        contentvalues.put(KOLOM_NIP, modeluserdata.getNIP());
        
        String where = "id = ? ";
        return getWritableDatabase().update(NAMA_TABEL, contentvalues, where, args) > 0;
    }
    public String[] getbyId(int id){
        String[] kolom = new String[3];
        
        return kolom;
    }
    public List<ModelUserData> getKolom() {
        List<ModelUserData> DataKaryawan = new ArrayList<ModelUserData>();

        /*
        ORDER BY id DESC
        Mengambil data dengan Urutan Paling bawah (Akhir) ke Atas
        */
        String cmd = "SELECT * FROM " + NAMA_TABEL + " ORDER BY id DESC";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(cmd, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String NAMA = cursor.getString(cursor.getColumnIndex(KOLOM_NAMA));
                String NIP = cursor.getString(cursor.getColumnIndex(KOLOM_NIP));
                
                ModelUserData Modeluserdata = new ModelUserData();
                Modeluserdata.setNAMA(NAMA);
                Modeluserdata.setNIP(NIP);
                Modeluserdata.setID(id);
                
                DataKaryawan.add(Modeluserdata);

            } while (cursor.moveToNext());
        }
        close();
        return DataKaryawan;
    }
    public boolean clear(){
        for(ModelUserData data_user : getKolom()){
            delete(String.valueOf(data_user.getID()));
        }
        return getKolom().size() < 1;
    }
}
