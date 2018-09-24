package com.laptopnct.dichvunhadat.View;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import com.laptopnct.dichvunhadat.Model.ChiNhanhCongTyModel;
import com.laptopnct.dichvunhadat.Model.CongTyModel;
import com.laptopnct.dichvunhadat.Model.PhongModel;
import com.laptopnct.dichvunhadat.Model.TienIchModel;
import com.laptopnct.dichvunhadat.R;
import com.laptopnct.dichvunhadat.Model.DichVuModel;
import com.laptopnct.dichvunhadat.Model.ThemDichVuModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class ThemCongTyActivity extends AppCompatActivity implements  View.OnClickListener,AdapterView.OnItemSelectedListener{

    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGCONGTY = 117;
    final int RESULT_VIDEO = 200;

    Button btnGioMoCua,btnGioDongCua, btnThemCongTy;
    Spinner spinnerKhuVuc;
    LinearLayout khungTienIch,khungChiNhanh,khungChuaChiNhanh, khungChuaDichVu;
    String gioMoCua,gioDongCua,khuvuc;
    RadioGroup rdgTrangThai;
    EditText edTenCongTy,edGiaToiDa,edGiaThoiThieu;

    List<DichVuModel> dichVuModelList;
    List<String> selectedTienIchList;
    List<String> khuVucList, dichVuList;
    List<String> chiNhanhList;
    List<ThemDichVuModel> themDichVuModelList;
    List<Bitmap> hinhDaChup;
    List<Uri> hinhCongTy;
    Uri videoSelected;

    ArrayAdapter<String> adapterKhuVuc;
    ImageView imgTam,imgHinhQuan1,imgHinhQuan2,imgHinhQuan3,imgHinhQuan4,imgHinhQuan5,imgHinhQuan6,imgVideo;
    VideoView videoView;

    String maCongTy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themcongty);

        btnGioDongCua = (Button) findViewById(R.id.btnGioDongCua);
        btnGioMoCua = (Button) findViewById(R.id.btnGioMoCua);
        spinnerKhuVuc = (Spinner) findViewById(R.id.spinnerKhuVuc);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienTich);
        khungChiNhanh = (LinearLayout) findViewById(R.id.khungChiNhanh);
        khungChuaChiNhanh = (LinearLayout) findViewById(R.id.khungChuaChiNhanh);
        khungChuaDichVu = (LinearLayout) findViewById(R.id.khungChuaDichVu);
        imgHinhQuan1 = (ImageView) findViewById(R.id.imgHinhQuan1);
        imgHinhQuan2 = (ImageView) findViewById(R.id.imgHinhQuan2);
        imgHinhQuan3 = (ImageView) findViewById(R.id.imgHinhQuan3);
        imgHinhQuan4 = (ImageView) findViewById(R.id.imgHinhQuan4);
        imgHinhQuan5 = (ImageView) findViewById(R.id.imgHinhQuan5);
        imgHinhQuan6 = (ImageView) findViewById(R.id.imgHinhQuan6);
        imgVideo = (ImageView) findViewById(R.id.imgVideo);
        videoView = (VideoView) findViewById(R.id.videoView);
        btnThemCongTy = (Button) findViewById(R.id.btnThemCongTy);
        rdgTrangThai = (RadioGroup) findViewById(R.id.rdgTrangThai);
        edGiaThoiThieu = (EditText) findViewById(R.id.edGiaToiThieu);
        edGiaToiDa = (EditText) findViewById(R.id.edGiaToiDa);
        edTenCongTy = (EditText) findViewById(R.id.edTenCty);

        dichVuModelList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        dichVuList = new ArrayList<>();
        selectedTienIchList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        themDichVuModelList = new ArrayList<>();
        hinhDaChup = new ArrayList<>();
        hinhCongTy = new ArrayList<>();

        adapterKhuVuc = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        CloneChiNhanh();
        CloneDichVu();

        LayDanhSachKhuVuc();
        LayDanhSachTienIch();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        imgHinhQuan1.setOnClickListener(this);
        imgHinhQuan2.setOnClickListener(this);
        imgHinhQuan3.setOnClickListener(this);
        imgHinhQuan4.setOnClickListener(this);
        imgHinhQuan5.setOnClickListener(this);
        imgHinhQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        btnThemCongTy.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_IMG1:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan1.setImageURI(uri);
                    hinhCongTy.add(uri);
                }
                break;

            case RESULT_IMG2:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan2.setImageURI(uri);
                    hinhCongTy.add(uri);
                }
                break;

            case RESULT_IMG3:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan3.setImageURI(uri);
                    hinhCongTy.add(uri);
                }
                break;

            case RESULT_IMG4:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan4.setImageURI(uri);
                    hinhCongTy.add(uri);
                }
                break;

            case RESULT_IMG5:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan5.setImageURI(uri);
                    hinhCongTy.add(uri);
                }
                break;

            case RESULT_IMG6:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan6.setImageURI(uri);
                    hinhCongTy.add(uri);
                }
                break;

            case RESULT_IMGCONGTY:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTam.setImageBitmap(bitmap);
                hinhDaChup.add(bitmap);
                break;

            case RESULT_VIDEO:
                if(RESULT_OK == resultCode){
                    imgVideo.setVisibility(View.GONE);
                    Uri uri = data.getData();
                    videoSelected = uri;
                    videoView.setVideoURI(uri);
                    videoView.start();

                }
                break;
        }

    }

    private void CloneDichVu(){
        View view = LayoutInflater.from(ThemCongTyActivity.this).inflate(R.layout.layout_clone_dichvu,null);
        final Spinner spinnerThucDon = (Spinner) view.findViewById(R.id.spinnerThucDon);
        Button btnThemThucDOn = (Button) view.findViewById(R.id.btnThemThucDon);
        final EditText edTenMon = (EditText) view.findViewById(R.id.edTenMon);
        final EditText edGiaTien = (EditText) view.findViewById(R.id.edGiaTien);
        ImageView imageChupHinh = (ImageView) view.findViewById(R.id.imgChupHinh);
        imgTam = imageChupHinh;

        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dichVuList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
        if(dichVuModelList.size() == 0){
            LayDanhSachDichVu(adapterThucDon);
        }

        imageChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_IMGCONGTY);
            }
        });

        btnThemThucDOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);

                long thoigian = Calendar.getInstance().getTimeInMillis();
                String tenhinh = String.valueOf(thoigian)+".jpg";

                int position = spinnerThucDon.getSelectedItemPosition();
                String maThucDon = dichVuModelList.get(position).getMadichvu();

                PhongModel monAnModel = new PhongModel();
                monAnModel.setTenphong(edTenMon.getText().toString());
                monAnModel.setGiatien(Long.parseLong(edGiaTien.getText().toString()));
                monAnModel.setHinhanh(tenhinh);

                ThemDichVuModel themThucDonModel = new ThemDichVuModel();
                themThucDonModel.setMadichvu(maThucDon);
                themThucDonModel.setPhongModel(monAnModel);

                themDichVuModelList.add(themThucDonModel);
                CloneDichVu();
            }
        });

        khungChuaDichVu.addView(view);
    }

    private void CloneChiNhanh(){
        final View view = LayoutInflater.from(ThemCongTyActivity.this).inflate(R.layout.layout_clone_chinhanh,null);
        ImageButton btnThemChiNhanh = (ImageButton) view.findViewById(R.id.btnThemChiNhanh);
        final ImageButton btnXoaChiNhanh = (ImageButton) view.findViewById(R.id.btnXoaChiNhanh);

        btnThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edTenChiNhanh = (EditText) view.findViewById(R.id.edTenChiNhanh);
                String tenChiNhanh = edTenChiNhanh.getText().toString();

                v.setVisibility(View.GONE);
                btnXoaChiNhanh.setVisibility(View.VISIBLE);
                btnXoaChiNhanh.setTag(tenChiNhanh);


                chiNhanhList.add(tenChiNhanh);

                CloneChiNhanh();
            }
        });

        btnXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiNhanh = v.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                khungChuaChiNhanh.removeView(view);

            }
        });
        khungChuaChiNhanh.addView(view);
    }

    private void LayDanhSachKhuVuc(){
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String tenKhuVuc = snapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                }

                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachDichVu(final ArrayAdapter<String> adapterDichVu){
        FirebaseDatabase.getInstance().getReference().child("dichvus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DichVuModel thucDonModel = new DichVuModel();
                    String key = snapshot.getKey();
                    String value = snapshot.getValue(String.class);

                    thucDonModel.setTendichvu(value);
                    thucDonModel.setMadichvu(key);

                    dichVuModelList.add(thucDonModel);
                    dichVuList.add(value);

                }

                adapterDichVu.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachTienIch(){
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String maTienIch = snapshot.getKey();
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);

                    CheckBox checkBox = new CheckBox(ThemCongTyActivity.this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String maTienIch = buttonView.getTag().toString();
                            if(isChecked){
                                selectedTienIchList.add(maTienIch);
                            }else{
                                selectedTienIchList.remove(maTienIch);
                            }
                        }
                    });
                    khungTienIch.addView(checkBox);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(final View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);

        switch (v.getId()){
            case R.id.btnGioDongCua:

                TimePickerDialog timePickerDialog = new TimePickerDialog(ThemCongTyActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       gioDongCua = hourOfDay +":"+minute;
                        ((Button)v).setText(gioDongCua);
                    }
                },gio,phut,true);

                timePickerDialog.show();
                break;

            case R.id.btnGioMoCua:

                TimePickerDialog moCuaTimePickerDialog = new TimePickerDialog(ThemCongTyActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay +":"+minute;
                        ((Button)v).setText(gioMoCua);
                    }
                },gio,phut,true);

                moCuaTimePickerDialog.show();
                break;

            case R.id.imgHinhQuan1:
                ChonHinhTuGallary(RESULT_IMG1);
                break;

            case R.id.imgHinhQuan2:
                ChonHinhTuGallary(RESULT_IMG2);
                break;

            case R.id.imgHinhQuan3:
                ChonHinhTuGallary(RESULT_IMG3);
                break;

            case R.id.imgHinhQuan4:
                ChonHinhTuGallary(RESULT_IMG4);
                break;

            case R.id.imgHinhQuan5:
                ChonHinhTuGallary(RESULT_IMG5);
                break;

            case R.id.imgHinhQuan6:
                ChonHinhTuGallary(RESULT_IMG6);
                break;

            case R.id.imgVideo:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn video..."),RESULT_VIDEO);
                break;

            case R.id.btnThemCongTy:
                ThemCongTy();
                break;
        }
    }

    private void ThemCongTy(){
        String tenQuanAn = edTenCongTy.getText().toString();
        long giaToiDa = Long.parseLong(edGiaToiDa.getText().toString());
        long giaToiThieu = Long.parseLong(edGiaThoiThieu.getText().toString());
        int idRadioSelected = rdgTrangThai.getCheckedRadioButtonId();
        boolean giaoHang = false;
        if(idRadioSelected == R.id.rdGiaoHang){
            giaoHang = true;
        }else{
            giaoHang = false;
        }

        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodeCongTy = nodeRoot.child("congtys");
        maCongTy = nodeCongTy.push().getKey();

        nodeRoot.child("khuvucs").child(khuvuc).push().setValue(maCongTy);

        for(String chinhanh : chiNhanhList){
            String urlGeoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address="+chinhanh.replace(" ","%20")+"&key=AIzaSyBVd2D3evAh1Ip_f5nuN1P6ad-14G3Ns0g";
            DownloadToaDo downloadToaDo = new DownloadToaDo();
            downloadToaDo.execute(urlGeoCoding);

        }

        CongTyModel quanAnModel = new CongTyModel();
        quanAnModel.setTencongty(tenQuanAn);
        quanAnModel.setGiatoida(giaToiDa);
        quanAnModel.setGiatoithieu(giaToiThieu);
        quanAnModel.setDatcoc(giaoHang);
        quanAnModel.setVideogioithieu(videoSelected.getLastPathSegment());
        quanAnModel.setTienich(selectedTienIchList);
        quanAnModel.setLuotthich(0);

        nodeCongTy.child(maCongTy).setValue(quanAnModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        FirebaseStorage.getInstance().getReference().child("videos/"+videoSelected.getLastPathSegment()).putFile(videoSelected);
        for(Uri hinhcty : hinhCongTy){
            FirebaseStorage.getInstance().getReference().child("hinhcongty"+hinhcty.getLastPathSegment()).putFile(hinhcty);
            nodeRoot.child("hinhanhcongtys").child(maCongTy).push().child(hinhcty.getLastPathSegment());
        }

        for (int i = 0; i< themDichVuModelList.size() ; i++){
            nodeRoot.child("danhsachdichvus").child(maCongTy).child(themDichVuModelList.get(i).getMadichvu()).push().setValue(themDichVuModelList.get(i).getPhongModel());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = hinhDaChup.get(i);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            FirebaseStorage.getInstance().getReference().child("hinhcongty/"+ themDichVuModelList.get(i).getPhongModel().getHinhanh()).putBytes(data);
        }


    }

    class DownloadToaDo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i =0 ;i<results.length();i++){
                    JSONObject object = results.getJSONObject(i);
                    String address = object.getString("formatted_address");
                    JSONObject geometry = object.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double latitude = (double) location.get("lat");
                    double longitude = (double) location.get("lng");

                    ChiNhanhCongTyModel chiNhanhCongTyModel = new ChiNhanhCongTyModel();
                    chiNhanhCongTyModel.setDiachi(address);
                    chiNhanhCongTyModel.setLatitude(latitude);
                    chiNhanhCongTyModel.setLongitude(longitude);

                    FirebaseDatabase.getInstance().getReference().child("chinhanhcongtys").child(maCongTy).push().setValue(chiNhanhCongTyModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChonHinhTuGallary(int requestCode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình..."),requestCode);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spinnerKhuVuc:
               khuvuc = khuVucList.get(position);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
