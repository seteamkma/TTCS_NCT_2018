package com.laptopnct.dichvunhadat.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.laptopnct.dichvunhadat.Control.CapNhatWifiController;
import com.laptopnct.dichvunhadat.Model.WifiCongTyModel;
import com.laptopnct.dichvunhadat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class PopupCapNhatWifiActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edTenWifi, edMatKhauWifi;
    Button btnDongY;

    CapNhatWifiController capNhatWifiController;
    String maquanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_catnhatwifi);

        maquanan = getIntent().getStringExtra("macongty");

        edMatKhauWifi = (EditText) findViewById(R.id.edNhatKhauWifi);
        edTenWifi = (EditText) findViewById(R.id.edTenWifi);
        btnDongY = (Button) findViewById(R.id.btnDongYCatNhatWifi);

        btnDongY.setOnClickListener(this);

        capNhatWifiController = new CapNhatWifiController(this);
    }

    @Override
    public void onClick(View v) {
        String tenwifi = edTenWifi.getText().toString();
        String matkhauwifi = edMatKhauWifi.getText().toString();

        if(tenwifi.trim().length() > 0 && matkhauwifi.trim().length() > 0){

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngaydang = simpleDateFormat.format(calendar.getTime());

            WifiCongTyModel wifiCongTyModel = new WifiCongTyModel();
            wifiCongTyModel.setTen(tenwifi);
            wifiCongTyModel.setMatkhau(matkhauwifi);
            wifiCongTyModel.setNgaydang(ngaydang);

            capNhatWifiController.ThemWifi(this,wifiCongTyModel,maquanan);
        }else{
            Toast.makeText(this,getResources().getString(R.string.loithemwifi),Toast.LENGTH_SHORT).show();
        }

    }
}
