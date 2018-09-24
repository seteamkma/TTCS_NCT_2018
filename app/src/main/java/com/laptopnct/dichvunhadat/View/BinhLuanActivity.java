package com.laptopnct.dichvunhadat.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.laptopnct.dichvunhadat.Control.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.laptopnct.dichvunhadat.Control.BinhLuanController;
import com.laptopnct.dichvunhadat.Model.BinhLuanModel;
import com.laptopnct.dichvunhadat.R;

import java.util.ArrayList;
import java.util.List;



public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenCongTy, txtDiaChiCongTy,txtDangBinhLuan;
    Toolbar toolbar;
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    ImageButton btnChonHinh;
    RecyclerView recyclerViewChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;

    final int REQUEST_CHONHINHBINHLUAN = 11;
    String macongty;
    SharedPreferences sharedPreferences;

    BinhLuanController binhLuanController;
    List<String> listHinhDuocChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        macongty = getIntent().getStringExtra("macongty");
        String tencongty = getIntent().getStringExtra("tencongty");
        String diachi = getIntent().getStringExtra("diachi");

        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);

        txtDiaChiCongTy = (TextView) findViewById(R.id.txtDiaChiCongTy);
        txtTenCongTy = (TextView) findViewById(R.id.txtTenCongTy);
        txtDangBinhLuan = (TextView) findViewById(R.id.txtDangBinhLuan);
        edTieuDeBinhLuan = (EditText) findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = (EditText) findViewById(R.id.edNoiDungBinhLuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinh);
        recyclerViewChonHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewChonHinhBinhLuan.setLayoutManager(layoutManager);

        binhLuanController = new BinhLuanController();
        listHinhDuocChon = new ArrayList<>();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        txtDiaChiCongTy.setText(diachi);
        txtTenCongTy.setText(tencongty);

        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnChonHinh:
                Intent iChonHinhBinhLuan = new Intent(this,ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBinhLuan,REQUEST_CHONHINHBINHLUAN);

                break;

            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edTieuDeBinhLuan.getText().toString();
                String noidung = edNoiDungBinhLuan.getText().toString();
                String mauser = sharedPreferences.getString("mauser","");

                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem(0);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setMauser(mauser);

                binhLuanController.ThemBinhLuan(macongty,binhLuanModel,listHinhDuocChon);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHONHINHBINHLUAN){
            if(resultCode == RESULT_OK){

                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this,R.layout.custom_layout_hienthibinhluanduocchon,listHinhDuocChon);
               recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
               adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();

            }
        }

    }
}
