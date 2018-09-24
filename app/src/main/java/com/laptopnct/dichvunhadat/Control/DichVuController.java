package com.laptopnct.dichvunhadat.Control;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.laptopnct.dichvunhadat.Control.Adapters.AdapterDichVu;
import com.laptopnct.dichvunhadat.Control.Interfaces.DichVuInterface;
import com.laptopnct.dichvunhadat.Model.DichVuModel;

import java.util.List;



public class DichVuController {
    DichVuModel dichVuModel;

    public DichVuController(){
        dichVuModel = new DichVuModel();
    }

    public void getDanhSachDichVuCongTyTheoMa(final Context context, String macongty, final RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        DichVuInterface dichVuInterface = new DichVuInterface() {
            @Override
            public void getDichVuThanhCong(List<DichVuModel> dichVuModelList) {
                AdapterDichVu adapterThucDon = new AdapterDichVu(context,dichVuModelList);
                recyclerView.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        dichVuModel.getDanhSachDichVuCongTyTheoMa(macongty,dichVuInterface);
    }
}
