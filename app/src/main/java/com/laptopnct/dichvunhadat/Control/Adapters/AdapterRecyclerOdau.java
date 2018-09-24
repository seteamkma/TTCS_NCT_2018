package com.laptopnct.dichvunhadat.Control.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.laptopnct.dichvunhadat.Model.BinhLuanModel;
import com.laptopnct.dichvunhadat.Model.ChiNhanhCongTyModel;
import com.laptopnct.dichvunhadat.Model.CongTyModel;
import com.laptopnct.dichvunhadat.R;
import com.laptopnct.dichvunhadat.View.ChiTietCongTyActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cong Thanh  */

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {

    List<CongTyModel> congTyModelList;
    int resource;
    Context context;

    public AdapterRecyclerOdau(Context context, List<CongTyModel> congTyModelList, int resource){
        this.congTyModelList = congTyModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenCongTyOdau,txtTieudebinhluan2,txtTieudebinhluan,txtNodungbinhluan2,txtNodungbinhluan,txtChamDiemBinhLuan,txtChamDiemBinhLuan2,txtTongBinhLuan,txtTongHinhBinhLuan, txtDiemTrungBinhCongTy, txtDiaChiCongTyODau, txtKhoanCachCongTyODau;
        Button btnDatMonOdau;
        ImageView imageHinhCongTyODau;
        CircleImageView cicleImageUser2,cicleImageUser;
        LinearLayout containerBinhLuan,containerBinhLuan2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenCongTyOdau = (TextView) itemView.findViewById(R.id.txtTenCongTyOdau);
            btnDatMonOdau = (Button) itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhCongTyODau = (ImageView) itemView.findViewById(R.id.imageHinhCongTyOdau);
            txtNodungbinhluan = (TextView) itemView.findViewById(R.id.txtNodungbinhluan);
            txtNodungbinhluan2 = (TextView) itemView.findViewById(R.id.txtNodungbinhluan2);
            txtTieudebinhluan = (TextView) itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2 = (TextView) itemView.findViewById(R.id.txtTieudebinhluan2);
            cicleImageUser = (CircleImageView) itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2 = (CircleImageView) itemView.findViewById(R.id.cicleImageUser2);
            containerBinhLuan = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan2);
            txtChamDiemBinhLuan = (TextView) itemView.findViewById(R.id.txtChamDiemBinhLuan);
            txtChamDiemBinhLuan2 = (TextView) itemView.findViewById(R.id.txtChamDiemBinhLuan2);
            txtTongBinhLuan = (TextView) itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhBinhLuan = (TextView) itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiemTrungBinhCongTy = (TextView) itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtDiaChiCongTyODau = (TextView) itemView.findViewById(R.id.txtDiaChiCongTyODau);
            txtKhoanCachCongTyODau = (TextView) itemView.findViewById(R.id.txtKhoangCachCongTyODau);
            cardView = (CardView) itemView.findViewById(R.id.cardViewOdau);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CongTyModel congTyModel = congTyModelList.get(position);
        holder.txtTenCongTyOdau.setText(congTyModel.getTencongty());
        if(congTyModel.isDatcoc()){
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
        }

        if(congTyModel.getBitmapList().size() > 0){
            holder.imageHinhCongTyODau.setImageBitmap(congTyModel.getBitmapList().get(0));
        }
        //Lấy danh sách bình luận của công ty
        if(congTyModel.getBinhLuanModelList().size() > 0){

            BinhLuanModel binhLuanModel = congTyModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.cicleImageUser,binhLuanModel.getThanhVienModel().getHinhanh());
            if(congTyModel.getBinhLuanModelList().size() > 2){
                BinhLuanModel binhLuanModel2 = congTyModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.cicleImageUser2,binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            holder.txtTongBinhLuan.setText(congTyModel.getBinhLuanModelList().size() + "");

            int tongsohinhbinhluan = 0;
            double tongdiem = 0;
            //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
            for (BinhLuanModel binhLuanModel1 : congTyModel.getBinhLuanModelList()){
                tongsohinhbinhluan += binhLuanModel1.getHinhanhBinhLuanList().size();
                tongdiem += binhLuanModel1.getChamdiem();
            }

            double diemtrungbinh = tongdiem/congTyModel.getBinhLuanModelList().size();
            holder.txtDiemTrungBinhCongTy.setText(String.format("%.1f",diemtrungbinh));

            if(tongsohinhbinhluan > 0){
                holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            }

        }else{
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }

        //Lấy chi nhánh Công Ty và hiển thị địa chỉ và m
        if(congTyModel.getChiNhanhCongTyModelList().size() > 0){
            ChiNhanhCongTyModel chiNhanhCongTyModelTam = congTyModel.getChiNhanhCongTyModelList().get(0);
            for (ChiNhanhCongTyModel chiNhanhCongTyModel : congTyModel.getChiNhanhCongTyModelList()){
                if(chiNhanhCongTyModelTam.getKhoangcach() > chiNhanhCongTyModel.getKhoangcach()){
                    chiNhanhCongTyModelTam = chiNhanhCongTyModel;
                }
            }

            holder.txtDiaChiCongTyODau.setText(chiNhanhCongTyModelTam.getDiachi());
            holder.txtKhoanCachCongTyODau.setText(String.format("%.1f",chiNhanhCongTyModelTam.getKhoangcach()) + " m");
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietCongTy = new Intent(context, ChiTietCongTyActivity.class);
                iChiTietCongTy.putExtra("congty",congTyModel);
                context.startActivity(iChiTietCongTy);
            }
        });
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return congTyModelList.size();
    }


}
