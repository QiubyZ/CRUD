package com.crud.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog.*;
import com.crud.Modeldb.KontrolDataBase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.crud.Model.ModelUserData;

import com.crud.databinding.CostumDialogUpdateBinding;
import com.crud.databinding.RowListBinding;
import java.util.ArrayList;
import java.util.List;

public class CostumAdapter extends RecyclerView.Adapter<CostumAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RowListBinding bind;
        public ViewHolder(RowListBinding v) {
            super(v.getRoot());
            this.bind = v;
        }
    }

    List<ModelUserData> adapter = new ArrayList<ModelUserData>();

    public CostumAdapter(List<ModelUserData> list) {
        adapter = list;
    }

    @Override
    public com.crud.ViewModel.CostumAdapter.ViewHolder onCreateViewHolder(
            ViewGroup arg0, int arg1) {
        RowListBinding binding = RowListBinding.inflate(LayoutInflater.from(arg0.getContext()), arg0, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return adapter.size();
    }

    @Override
    public void onBindViewHolder(CostumAdapter.ViewHolder viewmodel, int posisi) {
        if (viewmodel != null) {
            ModelUserData datakaryawan = adapter.get(posisi);
            KontrolDataBase kontrolDatabase = new KontrolDataBase(viewmodel.bind.getRoot().getContext());
            
            /*
            untuk bind.idKaryawan
            Saya setnilainya ke Posisi nomor urut listView bukan menampilkan hasil ID dari Database
            */
            viewmodel.bind.idKaryawan.setText(String.valueOf(posisi));
            
            viewmodel.bind.namaKaryawan.setText(datakaryawan.getNAMA());
            viewmodel.bind.nipKaryawan.setText(datakaryawan.getNIP());
            viewmodel.bind.delete
                    .setOnClickListener(
                            (v) -> {

                                // Hapus Dari ViewList
                                adapter.remove(posisi);

                                // Hapus Dari Database
                                kontrolDatabase.delete(String.valueOf(datakaryawan.getID()));
                    //UpdateItemList
                                notifyDataSetChanged();
                            });

            viewmodel
                    .bind.update
                    .setOnClickListener(
                            (v) -> {
                    //Memanggil Class UpgradePopUp(?)
                                new UpgradePopUp(v.getContext(), datakaryawan, kontrolDatabase);
                            });
        }
    }

    class UpgradePopUp extends AlertDialog.Builder {
        
        KontrolDataBase kontrol;
        AlertDialog dialog;
        CostumDialogUpdateBinding dialogbind;
        ModelUserData datakaryawan;

        public UpgradePopUp(Context ctx, ModelUserData datakaryawan, KontrolDataBase kontrol) {
            super(ctx);
            this.kontrol = kontrol;
            this.datakaryawan = datakaryawan;
            onCreate();
        }

        void onCreate() {
            this.dialogbind = CostumDialogUpdateBinding.inflate(LayoutInflater.from(getContext()));
            setTitle("Update Data Karyawan");
            setView(dialogbind.getRoot());
            setCancelable(false);
            dialog = create();
            dialog.show();
            
            dialogbind.ubahNama.setText(datakaryawan.getNAMA());
            dialogbind.ubahNip.setText(datakaryawan.getNIP());
            
            dialogbind.cancel
                    .setOnClickListener(
                            (can) -> {
                                dialog.dismiss();
                            });
            
                dialogbind.perbaharui
                    .setOnClickListener(
                            (per) -> {
                                // Update Database
                                ModelUserData updateData = new ModelUserData();
                                updateData.setID(datakaryawan.getID());
                                updateData.setNAMA(dialogbind.ubahNama.getText().toString());
                                updateData.setNIP(dialogbind.ubahNip.getText().toString());
                    
                                if (kontrol.Update(updateData)) {
                                    Toast.makeText(
                                                    getContext(),
                                                    "Ditambah Ke database: "
                                                            + dialogbind.ubahNama.getText().toString(),
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // UpdateView
                                    datakaryawan.NAMA = dialogbind.ubahNama.getText().toString();
                                    datakaryawan.NIP = dialogbind.ubahNip.getText().toString();
                                    notifyDataSetChanged();
                                }
                                // KeluarDialog
                                dialog.dismiss();
                            });
        }
    }
}
