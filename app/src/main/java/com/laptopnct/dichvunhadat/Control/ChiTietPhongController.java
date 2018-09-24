package com.laptopnct.dichvunhadat.Control;

import android.widget.TextView;


import com.laptopnct.dichvunhadat.Control.Interfaces.WifiCongTyInterface;
import com.laptopnct.dichvunhadat.Model.WifiCongTyModel;

import java.util.ArrayList;
import java.util.List;


public class ChiTietPhongController {

    WifiCongTyModel wifiCongTyModel;
    List<WifiCongTyModel> wifiwifiCongTyModelList;

    public ChiTietPhongController(){
        wifiCongTyModel = new WifiCongTyModel();
        wifiwifiCongTyModelList = new ArrayList<>();
    }

    public void HienThiDanhSachWifiCongTy(String macongty, final TextView txtTenWifi, final TextView txtMatKhauWifi, final TextView txtNgayDangWifi){

        WifiCongTyInterface wifiCongTyInterface = new WifiCongTyInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiCongTyModel wifiQuanAnModel) {
                wifiwifiCongTyModelList.add(wifiCongTyModel);
                txtTenWifi.setText(wifiCongTyModel.getTen());
                txtMatKhauWifi.setText(wifiCongTyModel.getMatkhau());
                txtNgayDangWifi.setText(wifiCongTyModel.getNgaydang());
            }
        };

        wifiCongTyModel.LayDanhSachWifiCongTy(macongty, wifiCongTyInterface);
    }
}
