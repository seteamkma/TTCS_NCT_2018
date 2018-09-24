package com.laptopnct.dichvunhadat.Model;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laptopnct.dichvunhadat.R;
import com.laptopnct.dichvunhadat.Control.Interfaces.WifiCongTyInterface;



public class WifiCongTyModel {
    String ten,matkhau,ngaydang;

    public WifiCongTyModel(){

    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }


    private DatabaseReference nodeWifiCongTy;

    public void LayDanhSachWifiCongTy(String macty, final WifiCongTyInterface wifiCongTyInterface){
        nodeWifiCongTy = FirebaseDatabase.getInstance().getReference().child("wificongtys").child(macty);

        nodeWifiCongTy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot valueWifi : dataSnapshot.getChildren()){
                    WifiCongTyModel wifiCongTyModel = valueWifi.getValue(WifiCongTyModel.class);
                    wifiCongTyInterface.HienThiDanhSachWifi(wifiCongTyModel);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ThemWifiQuanAn(final Context context, WifiCongTyModel wifiCongTyModel, String macongty){
        DatabaseReference dataNodeWifiCongTy = FirebaseDatabase.getInstance().getReference().child("wificongtys").child(macongty);
        dataNodeWifiCongTy.push().setValue(wifiCongTyModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(context,context.getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
