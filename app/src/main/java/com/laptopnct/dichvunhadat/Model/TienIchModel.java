package com.laptopnct.dichvunhadat.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.laptopnct.dichvunhadat.View.ChiTietCongTyActivity;



public class TienIchModel {
    String hinhtienich,tentienich;
    String maTienIch;

    public String getMaTienIch() {
        return maTienIch;
    }

    public void setMaTienIch(String maTienIch) {
        this.maTienIch = maTienIch;
    }


    public TienIchModel(){

    }

    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }

    public String getTentienich() {
        return tentienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }


}
