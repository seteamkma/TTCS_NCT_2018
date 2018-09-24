package com.laptopnct.dichvunhadat.Control.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laptopnct.dichvunhadat.Model.WifiCongTyModel;
import com.laptopnct.dichvunhadat.R;

import java.util.List;


public class AdapterDanhSachWifi extends RecyclerView.Adapter<AdapterDanhSachWifi.ViewHolderWifi> {
    Context context;
    int resource;
    List<WifiCongTyModel> wifiCongTyModelList;

    public AdapterDanhSachWifi(Context context, int resource, List<WifiCongTyModel> wifiCongTyModelList){
        this.context = context;
        this.resource = resource;
        this.wifiCongTyModelList = wifiCongTyModelList;
    }

    public class ViewHolderWifi extends RecyclerView.ViewHolder {

        TextView txtTenWifi,txtMatKhauWifi,txtNgayDang;

        public ViewHolderWifi(View itemView) {
            super(itemView);

            txtTenWifi = (TextView) itemView.findViewById(R.id.txtTenWifi);
            txtMatKhauWifi = (TextView) itemView.findViewById(R.id.txtMatKhauWifi);
            txtNgayDang = (TextView) itemView.findViewById(R.id.txtNgayDangWifi);
        }
    }

    @Override
    public ViewHolderWifi onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource,parent,false);
        ViewHolderWifi viewHolderWifi = new ViewHolderWifi(view);

        return viewHolderWifi;
    }

    @Override
    public void onBindViewHolder(ViewHolderWifi holder, int position) {
        WifiCongTyModel wifiCongTyModel = wifiCongTyModelList.get(position);

        holder.txtTenWifi.setText(wifiCongTyModel.getTen());
        holder.txtMatKhauWifi.setText(wifiCongTyModel.getMatkhau());
        holder.txtNgayDang.setText(wifiCongTyModel.getNgaydang());

    }

    @Override
    public int getItemCount() {
        return wifiCongTyModelList.size();
    }


}
