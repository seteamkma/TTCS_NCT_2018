package com.laptopnct.dichvunhadat.Control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.laptopnct.dichvunhadat.Control.Adapters.AdapterDanhSachWifi;
import com.laptopnct.dichvunhadat.Control.Interfaces.WifiCongTyInterface;

import com.laptopnct.dichvunhadat.R;
import com.laptopnct.dichvunhadat.Model.WifiCongTyModel;

import java.util.List;


public class CapNhatWifiController {
    WifiCongTyModel wifiCongTyModel;
    Context context;
    List<WifiCongTyModel> wifiCongTyModelList;

    public CapNhatWifiController(Context context){
        wifiCongTyModel = new WifiCongTyModel();
        this.context = context;
    }

    public void HienThiDanhSachWifi(String macongty, final RecyclerView recyclerView){

        wifiCongTyModel = new WifiCongTyModel();
        WifiCongTyInterface wifiCongTyInterface = new WifiCongTyInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiCongTyModel wifiQuanAnModel) {

                wifiCongTyModelList.add(wifiQuanAnModel);
                AdapterDanhSachWifi adapterDanhSachWifi = new AdapterDanhSachWifi(context, R.layout.layout_wifi_chitietcongty,wifiCongTyModelList);
                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
            }
        };

        wifiCongTyModel.LayDanhSachWifiCongTy(macongty, wifiCongTyInterface);
    }

    public void ThemWifi(Context context, WifiCongTyModel wifiCongTyModel, String macongty){
        wifiCongTyModel.ThemWifiQuanAn(context,wifiCongTyModel,macongty);
    }
}
