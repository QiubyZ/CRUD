package com.crud.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog.*;
import com.crud.Modeldb.KontrolDataBase;
import com.crud.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.crud.Model.ModelUserData;

import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class CostumAdapter extends RecyclerView.Adapter<CostumAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id_karyawan, nama, nip;
        public View v;
        public Button update, delete;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            this.id_karyawan = v.findViewById(R.id.id_karyawan);
            this.nama = v.findViewById(R.id.nama_karyawan);
            this.nip = v.findViewById(R.id.nip_karyawan);
            this.delete = v.findViewById(R.id.delete);
            this.update = v.findViewById(R.id.update);
        }

        public TextView getId_karyawan() {
            return this.id_karyawan;
        }

        public void setId_karyawan(TextView id_karyawan) {
            this.id_karyawan = id_karyawan;
        }

        public TextView getNama() {
            return this.nama;
        }

        public void setNama(TextView nama) {
            this.nama = nama;
        }

        public TextView getNip() {
            return this.nip;
        }

        public void setNip(TextView nip) {
            this.nip = nip;
        }

        public Button getUpdate() {
            return this.update;
        }

        public void setUpdate(Button update) {
            this.update = update;
        }

        public Button getDelete() {
            return this.delete;
        }

        public void setDelete(Button delete) {
            this.delete = delete;
        }
    }

    List<ModelUserData> adapter = new ArrayList<ModelUserData>();

    public CostumAdapter(List<ModelUserData> list) {
        adapter = list;
    }

    @Override
    public com.crud.ViewModel.CostumAdapter.ViewHolder onCreateViewHolder(
            ViewGroup arg0, int arg1) {
        View v = LayoutInflater.from(arg0.getContext()).inflate(R.layout.row_list, arg0, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return adapter.size();
    }

    @Override
    public void onBindViewHolder(CostumAdapter.ViewHolder viewmodel, int posisi) {
        ModelUserData datakaryawan = adapter.get(viewmodel.getAdapterPosition());
        KontrolDataBase kontrolDatabase = new KontrolDataBase(viewmodel.v.getContext());

        viewmodel.getId_karyawan().setText(String.valueOf(datakaryawan.getID()));
        // viewmodel.getId_karyawan().setText(String.valueOf(posisi));
        viewmodel.getNama().setText(datakaryawan.getNAMA());
        viewmodel.getNip().setText(datakaryawan.getNIP());

        viewmodel
                .getDelete()
                .setOnClickListener(
                        (v) -> {

                            // Hapus Dari Database
                            kontrolDatabase.delete(String.valueOf(datakaryawan.getID()));

                            // Hapus Dari ViewList
                            adapter.remove(datakaryawan);

                            // TrigerPembaharuanListView
                            notifyDataSetChanged();
                //notifyItemRemoved(posisi);
                        });

        viewmodel
                .getUpdate()
                .setOnClickListener(
                        (v) -> {
                            new UpgradePopUp(v.getContext(), viewmodel, kontrolDatabase);
                            
                            notifyDataSetChanged();
                            //notifyItemChanged(posisi);
                            
                        });
    }

    class UpgradePopUp extends AlertDialog.Builder {
        public TextInputEditText ubah_nama, ubah_nip;
        KontrolDataBase kontrol;
        ViewHolder viewHolder;
        AlertDialog dialog;

        public UpgradePopUp(Context ctx, ViewHolder v, KontrolDataBase kontrol) {
            super(ctx);
            this.viewHolder = v;
            this.kontrol = kontrol;
            onCreate();
        }

        void onCreate() {
            View v =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.costum_dialog_update, null, true);
            setView(v);
            setCancelable(false);
            setTitle("Update Data Karyawan");
            ubah_nama = v.findViewById(R.id.ubah_nama);
            ubah_nip = v.findViewById(R.id.ubah_nip);

            ubah_nama.setText(viewHolder.getNama().getText().toString());
            ubah_nip.setText(String.valueOf(viewHolder.getNip().getText().toString()));

            dialog = create();
            dialog.show();

            v.findViewById(R.id.cancel)
                    .setOnClickListener(
                            (can) -> {
                                dialog.dismiss();
                            });
            v.findViewById(R.id.perbaharui)
                    .setOnClickListener(
                            (per) -> {
                                ModelUserData updateData = new ModelUserData();
                                updateData.setID(
                                        Integer.valueOf(
                                                viewHolder.getId_karyawan().getText().toString()));
                                updateData.setNAMA(ubah_nama.getText().toString());
                                updateData.setNIP(ubah_nip.getText().toString());
                                
                    viewHolder.getNama().setText(ubah_nama.getText().toString());
                    viewHolder.getNip().setText(ubah_nip.getText().toString());
                    
                                Toast.makeText(getContext(),"Ubah ke: " + ubah_nama.getText().toString(), Toast.LENGTH_LONG).show();
                                kontrol.Update(updateData);
                                dialog.dismiss();
                            });
        }
    }
}
