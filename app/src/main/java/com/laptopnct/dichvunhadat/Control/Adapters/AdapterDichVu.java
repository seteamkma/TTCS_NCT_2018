package com.laptopnct.dichvunhadat.Control.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laptopnct.dichvunhadat.Model.DichVuModel;
import com.laptopnct.dichvunhadat.R;

import java.util.List;



public class AdapterDichVu extends RecyclerView.Adapter<AdapterDichVu.HolderThucDon> {

    Context context;
    List<DichVuModel> dichVuModels;

    public AdapterDichVu(Context context, List<DichVuModel> dichVuModels){
        this.context = context;
        this.dichVuModels = this.dichVuModels;
    }

    @Override
    public HolderThucDon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_dichvu,parent,false);
        return new HolderThucDon(view);
    }

    @Override
    public void onBindViewHolder(HolderThucDon holder, int position) {
        DichVuModel dichVuModel = dichVuModels.get(position);
        holder.txtDichVu.setText(dichVuModel.getTendichvu());
        holder.recyclerViewPhong.setLayoutManager(new LinearLayoutManager(context));
        AdapterPhong adapterPhong = new AdapterPhong(context,dichVuModel.getPhongModelList());
        holder.recyclerViewPhong.setAdapter(adapterPhong);
        adapterPhong.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dichVuModels.size();
    }

    public class HolderThucDon extends RecyclerView.ViewHolder {
        TextView txtDichVu;
        RecyclerView recyclerViewPhong;
        public HolderThucDon(View itemView) {
            super(itemView);

            txtDichVu = (TextView) itemView.findViewById(R.id.txtTenDichVu);
            recyclerViewPhong = (RecyclerView) itemView.findViewById(R.id.recyclerPhong);
        }
    }
}
