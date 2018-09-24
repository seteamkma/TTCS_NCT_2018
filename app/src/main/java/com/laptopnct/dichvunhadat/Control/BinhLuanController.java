package com.laptopnct.dichvunhadat.Control;


import com.laptopnct.dichvunhadat.Model.BinhLuanModel;

import java.util.List;



public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public  BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maCongTy, BinhLuanModel binhLuanModel, List<String> listHinh){
        binhLuanModel.ThemBinhLuan(maCongTy,binhLuanModel,listHinh);
    }
}
