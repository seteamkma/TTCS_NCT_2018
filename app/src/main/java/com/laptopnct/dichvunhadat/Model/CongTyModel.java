package com.laptopnct.dichvunhadat.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laptopnct.dichvunhadat.Control.Interfaces.OdauInterface;
import java.util.ArrayList;
import java.util.List;



public class CongTyModel implements Parcelable{
    boolean datcoc;
    String giodongcua,giomocua, tencongty,videogioithieu, macongty;
    List<String> tienich;
    List<String> hinhanhcongty;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhCongTyModel> chiNhanhCongTyModelList;
    List<Bitmap> bitmapList;
    List<DichVuModel> dichVus;
    DatabaseReference dataCongTy;

    long giatoida;
    long giatoithieu;
    long luotthich;

    public List<DichVuModel> getDichVus() {
        return dichVus;
    }

    public void setDichVus(List<DichVuModel> dichVus) {
        this.dichVus = dichVus;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }



    protected CongTyModel(Parcel in) {
        datcoc = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tencongty = in.readString();
        videogioithieu = in.readString();
        macongty = in.readString();
        tienich = in.createStringArrayList();
        hinhanhcongty = in.createStringArrayList();
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        chiNhanhCongTyModelList = new ArrayList<ChiNhanhCongTyModel>();
        in.readTypedList(chiNhanhCongTyModelList, ChiNhanhCongTyModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
        dataCongTy = FirebaseDatabase.getInstance().getReference().child("congtys");
    }


    public static final Creator<CongTyModel> CREATOR = new Creator<CongTyModel>() {
        @Override
        public CongTyModel createFromParcel(Parcel in) {
            return new CongTyModel(in);
        }

        @Override
        public CongTyModel[] newArray(int size) {
            return new CongTyModel[size];
        }
    };

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }



    public List<ChiNhanhCongTyModel> getChiNhanhCongTyModelList() {
        return chiNhanhCongTyModelList;
    }

    public void setChiNhanhCongTyModelList(List<ChiNhanhCongTyModel> chiNhanhCongTyModelList) {
        this.chiNhanhCongTyModelList = chiNhanhCongTyModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }


    private DatabaseReference nodeRoot ;

    public List<String> getHinhanhcongty() {
        return hinhanhcongty;
    }

    public void setHinhanhcongty(List<String> hinhanhcongty) {
        this.hinhanhcongty = hinhanhcongty;
    }


    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public CongTyModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isDatcoc() {
        return datcoc;
    }

    public void setDatcoc(boolean datcoc) {
        this.datcoc = datcoc;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTencongty() {
        return tencongty;
    }

    public void setTencongty(String tencongty) {
        this.tencongty = tencongty;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMacongty() {
        return macongty;
    }

    public void setMacongty(String macongty) {
        this.macongty = macongty;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataRoot;
    public void getDanhSachCongTy(final OdauInterface odauInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachCongTy(dataSnapshot,odauInterface,vitrihientai,itemtieptheo,itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            LayDanhSachCongTy(dataRoot,odauInterface,vitrihientai,itemtieptheo,itemdaco);
        }else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }

    private void LayDanhSachCongTy(DataSnapshot dataSnapshot, OdauInterface odauInterface, Location vitrihientai, int itemtieptheo, int itemdaco){
        DataSnapshot dataSnapshotCongTy = dataSnapshot.child("congtys");
        //Lấy danh sách Công Ty
        int i = 0;
        for (DataSnapshot valueCongTy : dataSnapshotCongTy.getChildren()){

            if(i == itemtieptheo){
                break;
            }
            if(i < itemdaco){
                i++;
                continue;
            }
            i++;
            CongTyModel congTyModel = valueCongTy.getValue(CongTyModel.class);
            assert congTyModel != null;
            congTyModel.setMacongty(valueCongTy.getKey());
            //Lấy danh sách hình ảnh của Cong Ty theo mã
            DataSnapshot dataSnapshotHinhCongTy = dataSnapshot.child("hinhanhcongtys").child(valueCongTy.getKey());

            List<String> hinhanhlist = new ArrayList<>();

            for (DataSnapshot valueHinhCongTy : dataSnapshotHinhCongTy.getChildren()){
                hinhanhlist.add(valueHinhCongTy.getValue(String.class));
            }
            congTyModel.setHinhanhcongty(hinhanhlist);

            //Lấy danh sách bình luân của Cong Ty
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(congTyModel.getMacongty());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();

            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);

                binhLuanModels.add(binhLuanModel);
            }
            congTyModel.setBinhLuanModelList(binhLuanModels);

            //Lấy chi nhánh công ty
            DataSnapshot snapshotChiNhanhCongTy = dataSnapshot.child("chinhanhcongtys").child(congTyModel.getMacongty());
            List<ChiNhanhCongTyModel> chiNhanhCongTyModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhCongTy : snapshotChiNhanhCongTy.getChildren()){
                ChiNhanhCongTyModel chiNhanhCongTyModel = valueChiNhanhCongTy.getValue(ChiNhanhCongTyModel.class);
                Location vitricongty = new Location("");
                vitricongty.setLatitude(chiNhanhCongTyModel.getLatitude());
                vitricongty.setLongitude(chiNhanhCongTyModel.getLongitude());

                double khoangcach = (double)(vitrihientai.distanceTo(vitricongty)/1000);
                chiNhanhCongTyModel.setKhoangcach(khoangcach);

                chiNhanhCongTyModels.add(chiNhanhCongTyModel);
            }

            congTyModel.setChiNhanhCongTyModelList(chiNhanhCongTyModels);

            odauInterface.getDanhSachCongTyModel(congTyModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (datcoc ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tencongty);
        dest.writeString(videogioithieu);
        dest.writeString(macongty);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhcongty);
        dest.writeLong(luotthich);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);
        dest.writeTypedList(chiNhanhCongTyModelList);
        dest.writeTypedList(binhLuanModelList);
    }
}
