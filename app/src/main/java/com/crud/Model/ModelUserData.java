package com.crud.Model;
import com.crud.Model.ModelUserData;

public class ModelUserData {
    public String NAMA;
    public String NIP;
    public int ID;
    
    public ModelUserData(String nama, String nip){
        this.NAMA = nama;
        this.NIP = nip;
    }
    
    public ModelUserData(){
        
    }
    
    public String getNAMA() {
        return this.NAMA;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public String getNIP() {
        return this.NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
