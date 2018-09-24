package com.laptopnct.dichvunhadat.Control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.laptopnct.dichvunhadat.Control.Adapters.AdapterRecyclerOdau;
import com.laptopnct.dichvunhadat.Control.Interfaces.OdauInterface;
import com.laptopnct.dichvunhadat.Model.CongTyModel;
import com.laptopnct.dichvunhadat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binh on 4/17/17.
 */

public class OdauController {
    Context context;
    CongTyModel congTyModel;
    AdapterRecyclerOdau adapterRecyclerOdau;
    int itemdaco = 3;

    public OdauController(Context context){
        this.context = context;
        congTyModel = new CongTyModel();
    }

    public void getDanhSachCongTyController(Context context, NestedScrollView nestedScrollView, RecyclerView recyclerOdau, final ProgressBar progressBar, final Location vitrihientai){

        final List<CongTyModel> congTyModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(context,congTyModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);

        progressBar.setVisibility(View.VISIBLE);

        final OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachCongTyModel(final CongTyModel congTyModel) {
                final List<Bitmap> bitmaps = new ArrayList<>();
                for(String linkhinh : congTyModel.getHinhanhcongty()){

                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhcongty").child(linkhinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            bitmaps.add(bitmap);
                            congTyModel.setBitmapList(bitmaps);

                            if(congTyModel.getBitmapList().size() == congTyModel.getHinhanhcongty().size()){
                                congTyModelList.add(congTyModel);
                                adapterRecyclerOdau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }


            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) !=null){
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        itemdaco += 3;
                        congTyModel.getDanhSachCongTy(odauInterface,vitrihientai,itemdaco,itemdaco-3);
                    }
                }
            }
        });

        congTyModel.getDanhSachCongTy(odauInterface,vitrihientai,itemdaco,0);

    }
}
