package com.laptopnct.dichvunhadat.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.laptopnct.dichvunhadat.Control.CapNhatWifiController;
import com.laptopnct.dichvunhadat.R;



public class CapNhatDanhSachWifiActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCapNhatWifi;
    RecyclerView recyclerDanhSachWifi;
    CapNhatWifiController capNhatWifiController;
    String macongty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);

        btnCapNhatWifi = (Button) findViewById(R.id.btnCapNhatWifi);
        recyclerDanhSachWifi = (RecyclerView) findViewById(R.id.recyclerDanhSachWifi);

        macongty = getIntent().getStringExtra("macongty");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerDanhSachWifi.setLayoutManager(layoutManager);

        capNhatWifiController = new CapNhatWifiController(this);

        capNhatWifiController.HienThiDanhSachWifi(macongty,recyclerDanhSachWifi);

        btnCapNhatWifi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent iPopup = new Intent(this,PopupCapNhatWifiActivity.class);
        iPopup.putExtra("macongty",macongty);
        startActivity(iPopup);
    }
}
