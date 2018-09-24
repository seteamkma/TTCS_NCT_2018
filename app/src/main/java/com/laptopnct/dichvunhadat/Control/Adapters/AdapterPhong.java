package com.laptopnct.dichvunhadat.Control.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laptopnct.dichvunhadat.Model.DatPhong;
import com.laptopnct.dichvunhadat.Model.PhongModel;
import com.laptopnct.dichvunhadat.R;

import java.util.ArrayList;
import java.util.List;



public class AdapterPhong extends RecyclerView.Adapter<AdapterPhong.HolderPhong> {

    Context context;
    List<PhongModel> monAnModelList;
    public static List<DatPhong> datMonList = new ArrayList<>();

    public AdapterPhong(Context context, List<PhongModel> monAnModelList){
        this.context = context;
        this.monAnModelList = monAnModelList;

    }

    @Override
    public HolderPhong onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_phong,parent,false);
        return new HolderPhong(view);
    }

    @Override
    public void onBindViewHolder(final HolderPhong holder, int position) {
        final PhongModel phongModel = monAnModelList.get(position);
        holder.txtTenPhong.setText(phongModel.getTenphong());

        holder.txtSoLuong.setTag(0);
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                dem++;
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);

                DatPhong datPhongTag = (DatPhong) holder.imgGiamSoLuong.getTag();
                if(datPhongTag != null){
                    AdapterPhong.datMonList.remove(datPhongTag );
                }

                DatPhong datPhong = new DatPhong();
                datPhong.setSoLuong(dem);
                datPhong.setTenPhong(phongModel.getTenphong());

                holder.imgGiamSoLuong.setTag(datPhong);

                AdapterPhong.datMonList.add(datPhong);

            }
        });

        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                if(dem != 0){
                    dem--;
                    if(dem == 0){
                        DatPhong datPhong= (DatPhong) v.getTag();
                        AdapterPhong.datMonList.remove(datPhong);
                    }
                }

                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);

            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class HolderPhong extends RecyclerView.ViewHolder {
        TextView txtTenPhong,txtSoLuong;
        ImageView imgGiamSoLuong,imgTangSoLuong;

        public HolderPhong(View itemView) {
            super(itemView);
            txtTenPhong = (TextView) itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuong = (TextView) itemView.findViewById(R.id.txtSoLuong);
            imgGiamSoLuong = (ImageView) itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong = (ImageView) itemView.findViewById(R.id.imgTangSoLuong);
        }
    }
}
