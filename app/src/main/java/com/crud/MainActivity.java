package com.crud;

import android.os.Bundle;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.crud.Model.ModelUserData;
import com.crud.Modeldb.KontrolDataBase;
import com.crud.ViewModel.CostumAdapter;
import com.crud.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public List<ModelUserData> DataKaryawan;
    public KontrolDataBase Database_Karyawan;
    public CostumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        DataKaryawan = new ArrayList<ModelUserData>();
        adapter = new CostumAdapter(DataKaryawan);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("JUMLAH VIEW", String.valueOf(DataKaryawan.size()));

        Database_Karyawan = new KontrolDataBase(this);
        DataKaryawan.addAll(Database_Karyawan.getKolom());

        binding.submit.setOnClickListener(
                (v) -> {
                    String nama = binding.inputNama.getText().toString();
                    String nip = binding.inputNip.getText().toString();
                    InsertData(nama, nip);
                });
        
        binding.clearDataBase.setOnClickListener(
                (v) -> {
                    DataKaryawan.clear();
                    binding.recyclerView.removeAllViews();
                    Database_Karyawan.clear(); 
                });
    }

    void InsertData(String NAMA, String NIP) {
        DataKaryawan.clear();
        
        //Memasukkan DataKaryawan ke database
        Database_Karyawan.InputData(new ModelUserData(NAMA, NIP));
        
        //DataKaryawan dari Database dikeluarkan dan ditampilkan ke dalam ListView
        DataKaryawan.addAll(Database_Karyawan.getKolom());
        
        //TrigerPembaharuanListView
        adapter.notifyDataSetChanged();
    }
}
