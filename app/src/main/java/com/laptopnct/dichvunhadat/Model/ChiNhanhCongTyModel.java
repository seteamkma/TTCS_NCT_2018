package com.laptopnct.dichvunhadat.Model;

import android.os.Parcel;
import android.os.Parcelable;



public class ChiNhanhCongTyModel implements Parcelable {
    String diachi;
    double latitude,longitude,khoangcach;

    public ChiNhanhCongTyModel(){

    }

    protected ChiNhanhCongTyModel(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khoangcach = in.readDouble();
    }

    public static final Creator<ChiNhanhCongTyModel> CREATOR = new Creator<ChiNhanhCongTyModel>() {
        @Override
        public ChiNhanhCongTyModel createFromParcel(Parcel in) {
            return new ChiNhanhCongTyModel(in);
        }

        @Override
        public ChiNhanhCongTyModel[] newArray(int size) {
            return new ChiNhanhCongTyModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diachi);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(khoangcach);
    }
}
