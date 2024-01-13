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

import com.crud.ViewModel.CostumAdapter;
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
        if (viewmodel != null) {
            ModelUserData datakaryawan = adapter.get(posisi);
            KontrolDataBase kontrolDatabase = new KontrolDataBase(viewmodel.v.getContext());
            viewmodel.getId_karyawan().setText(String.valueOf(posisi));
            viewmodel.getNama().setText(datakaryawan.getNAMA());
            viewmodel.getNip().setText(datakaryawan.getNIP());
            viewmodel.setIsRecyclable(true);
            viewmodel
                    .getDelete()
                    .setOnClickListener(
                            (v) -> {

                                // Hapus Dari ViewList

                                adapter.remove(posisi);

                                // Hapus Dari Database
                                kontrolDatabase.delete(String.valueOf(datakaryawan.getID()));

                                notifyDataSetChanged();
                            });

            viewmodel
                    .getUpdate()
                    .setOnClickListener(
                            (v) -> {
                                new UpgradePopUp(v.getContext(), datakaryawan, kontrolDatabase);
                            });
        }
    }

    class UpgradePopUp extends AlertDialog.Builder {
        public TextInputEditText ubah_nama, ubah_nip;
        public KontrolDataBase kontrol;
        public CostumAdapter.ViewHolder model;
        public AlertDialog dialog;

        public ModelUserData datakaryawan;
        public int posisi;

        public UpgradePopUp(Context ctx, ModelUserData datakaryawan, KontrolDataBase kontrol) {
            super(ctx);
            this.kontrol = kontrol;
            this.datakaryawan = datakaryawan;

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

            ubah_nama.setText(datakaryawan.getNAMA());
            ubah_nip.setText(datakaryawan.getNIP());

            v.findViewById(R.id.cancel)
                    .setOnClickListener(
                            (can) -> {
                                dialog.dismiss();
                            });
            v.findViewById(R.id.perbaharui)
                    .setOnClickListener(
                            (per) -> {
                                // Update Database
                                ModelUserData updateData = new ModelUserData();
                                updateData.setID(datakaryawan.getID());
                                updateData.setNAMA(ubah_nama.getText().toString());
                                updateData.setNIP(ubah_nip.getText().toString());
                                if (kontrol.Update(updateData)) {
                                    Toast.makeText(
                                                    getContext(),
                                                    "Ditambah Ke database: "
                                                            + ubah_nama.getText().toString(),
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // UpdateView
                                    datakaryawan.NAMA = ubah_nama.getText().toString();
                                    datakaryawan.NIP = ubah_nip.getText().toString();
                                    notifyDataSetChanged();
                                }
                                // KeluarDialog
                                dialog.dismiss();
                            });
            dialog = create();
            dialog.show();
        }
    }
}
