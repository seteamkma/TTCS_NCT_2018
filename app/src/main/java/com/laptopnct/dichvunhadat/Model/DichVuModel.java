package com.laptopnct.dichvunhadat.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.laptopnct.dichvunhadat.Control.Interfaces.DichVuInterface;

import java.util.ArrayList;
import java.util.List;



public class DichVuModel {
    String madichvu;
    String tendichvu;
    List<PhongModel> phongModelList;

    public List<PhongModel> getPhongModelList() {
        return phongModelList;
    }

    public void setPhongModelList(List<PhongModel> phongModelList) {
        this.phongModelList = phongModelList;
    }

    public String getMadichvu() {
        return madichvu;
    }

    public void setMadichvu(String madichvu) {
        this.madichvu = madichvu;
    }

    public String getTendichvu() {
        return tendichvu;
    }

    public void setTendichvu(String tendichvu) {
        this.tendichvu = tendichvu;
    }

    public void getDanhSachDichVuCongTyTheoMa(String macongty, final DichVuInterface dichVuInterface){
        DatabaseReference nodeDichVuCongTy = FirebaseDatabase.getInstance().getReference().child("danhsachdichvus").child(macongty);
        nodeDichVuCongTy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                final List<DichVuModel> dichVuModels = new ArrayList<>();

                for (DataSnapshot valueDichVu : dataSnapshot.getChildren()){
                    final DichVuModel dichVuModel = new DichVuModel();

                    DatabaseReference nodeDichVu = FirebaseDatabase.getInstance().getReference().child("dichvus").child(valueDichVu.getKey());
                    nodeDichVu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotDichVu) {
                            String madichvu = dataSnapshotDichVu.getKey();
                            dichVuModel.setMadichvu(madichvu);
                            dichVuModel.setTendichvu(dataSnapshotDichVu.getValue(String.class));
                            List<PhongModel> phongModels = new ArrayList<>();

                            for (DataSnapshot valuePhong : dataSnapshot.child(madichvu).getChildren()){
                                PhongModel phongModel = valuePhong.getValue(PhongModel.class);
                                phongModel.setMaphong(valuePhong.getKey());
                                phongModels.add(phongModel);

                            }

                            dichVuModel.setPhongModelList(phongModels);
                            dichVuModels.add(dichVuModel);
                            dichVuInterface.getDichVuThanhCong(dichVuModels);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
