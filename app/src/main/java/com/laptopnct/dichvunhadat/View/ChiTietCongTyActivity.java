package com.laptopnct.dichvunhadat.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.laptopnct.dichvunhadat.Control.Adapters.ApdaterBinhLuan;
import com.laptopnct.dichvunhadat.Control.ChiTietPhongController;
import com.laptopnct.dichvunhadat.Control.DichVuController;
import com.laptopnct.dichvunhadat.Model.CongTyModel;
import com.laptopnct.dichvunhadat.Model.TienIchModel;
import com.laptopnct.dichvunhadat.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class ChiTietCongTyActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView txtTenCongTy,txtDiaChi,txtThoiGianHoatDong,txtTrangThaiHoatDong,txtTongSoHinhAnh,txtTongSoBinhLuan,txtTongSoCheckIn,txtTongSoLuuLai,txtTieuDeToolbar,txtGioiHanGia,txtTenWifi,txtMatKhauWifi,txtNgayDangWifi;
    ImageView imHinhAnhCongTy,imgPlayTrailer;
    Button btnBinhLuan;
    LinearLayout khungWifi;
    CongTyModel congTyModel;
    Toolbar toolbar;
    RecyclerView recyclerViewBinhLuan;
    ApdaterBinhLuan adapterBinhLuan;
    GoogleMap googleMap;
    MapFragment mapFragment;
    LinearLayout khungTienIch;
    View khungTinhNang;
    VideoView videoView;
    RecyclerView recyclerDichVu;

    ChiTietPhongController chiTietPhongController;
    DichVuController dichVuController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietcongty);
        congTyModel = new CongTyModel();
        congTyModel = getIntent().getParcelableExtra("congty");

        txtTenCongTy = (TextView) findViewById(R.id.txtTenCongTy);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChiCongTy);
        txtThoiGianHoatDong = (TextView) findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = (TextView) findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoBinhLuan = (TextView) findViewById(R.id.tongSoBinhLuan);
        txtTongSoCheckIn = (TextView) findViewById(R.id.tongSoCheckIn);
        txtTongSoHinhAnh = (TextView) findViewById(R.id.tongSoHinhAnh);
        txtTongSoLuuLai = (TextView) findViewById(R.id.tongSoLuuLai);
        imHinhAnhCongTy = (ImageView) findViewById(R.id.imHinhCongTy);
        txtTieuDeToolbar = (TextView) findViewById(R.id.txtTieuDeToolbar);
        txtGioiHanGia = (TextView) findViewById(R.id.txtGioiHanGia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerViewBinhLuan = (RecyclerView) findViewById(R.id.recyclerBinhLuanChiTietCongTy);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienTich);
        txtTenWifi = (TextView) findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = (TextView) findViewById(R.id.txtMatKhauWifi);
        khungWifi = (LinearLayout) findViewById(R.id.khungWifi);
        txtNgayDangWifi = (TextView) findViewById(R.id.txtNgayDangWifi);
        khungTinhNang = (View) findViewById(R.id.khungTinhNang);
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        videoView = (VideoView) findViewById(R.id.videoTrailer);
        imgPlayTrailer = (ImageView) findViewById(R.id.imgPLayTrailer);
        recyclerDichVu = (RecyclerView) findViewById(R.id.recyclerThucDon);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chiTietPhongController = new ChiTietPhongController();
        dichVuController = new DichVuController();

        mapFragment.getMapAsync(this);

        HienThiChiTietCongTy();

        khungTinhNang.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void HienThiChiTietCongTy(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());

        String giomocua = congTyModel.getGiomocua();
        String giodongcua = congTyModel.getGiodongcua();

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if(dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)){
                //gio mo cua
                txtTrangThaiHoatDong.setText(getString(R.string.dangmocua));
            }else{
                //dong cua
                txtTrangThaiHoatDong.setText(getString(R.string.dadongcua));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTieuDeToolbar.setText(congTyModel.getTencongty());

        txtTenCongTy.setText(congTyModel.getTencongty());
        txtDiaChi.setText(congTyModel.getChiNhanhCongTyModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(congTyModel.getGiomocua() + " - " + congTyModel.getGiodongcua());
        txtTongSoHinhAnh.setText(congTyModel.getHinhanhcongty().size() + "");
        txtTongSoBinhLuan.setText(congTyModel.getBinhLuanModelList().size() + "");
        txtThoiGianHoatDong.setText(giomocua + " - " + giodongcua);

        DownLoadHinhTienIch();


        if(congTyModel.getGiatoida() != 0 && congTyModel.getGiatoithieu() != 0){
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(congTyModel.getGiatoithieu()) + " đ";
            String giatoida = numberFormat.format(congTyModel.getGiatoida()) + " đ";
            txtGioiHanGia.setText( giatoithieu + " - " + giatoida );
        }else{
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }

        StorageReference storageHinhCongTy = FirebaseStorage.getInstance().getReference().child("hinhcongty").child(congTyModel.getHinhanhcongty().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhCongTy.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imHinhAnhCongTy.setImageBitmap(bitmap);
            }
        });

        if(congTyModel.getVideogioithieu() != null){
            videoView.setVisibility(View.VISIBLE);
            imgPlayTrailer.setVisibility(View.VISIBLE);
            imHinhAnhCongTy.setVisibility(View.GONE);
            StorageReference storageVideo = FirebaseStorage.getInstance().getReference().child("videos").child(congTyModel.getVideogioithieu());
            storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVideoURI(uri);
                    videoView.seekTo(1);
                }
            });

            imgPlayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.start();
                    MediaController mediaController = new MediaController(ChiTietCongTyActivity.this);
                    videoView.setMediaController(mediaController);
                    imgPlayTrailer.setVisibility(View.GONE);
                }
            });

        }else{
            videoView.setVisibility(View.GONE);
            imgPlayTrailer.setVisibility(View.GONE);
            imHinhAnhCongTy.setVisibility(View.VISIBLE);
        }

        //Load danh sach binh luan cua cty
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new ApdaterBinhLuan(this,R.layout.custom_layout_binhluan, congTyModel.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);

        chiTietPhongController.HienThiDanhSachWifiCongTy(congTyModel.getMacongty(),txtTenWifi,txtMatKhauWifi,txtNgayDangWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDanhSachWifi = new Intent(ChiTietCongTyActivity.this,CapNhatDanhSachWifiActivity.class);
                iDanhSachWifi.putExtra("macongty", congTyModel.getMacongty());
                startActivity(iDanhSachWifi);
            }
        });

        dichVuController.getDanhSachDichVuCongTyTheoMa(this, congTyModel.getMacongty(), recyclerDichVu);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = congTyModel.getChiNhanhCongTyModelList().get(0).getLatitude();
        double longitude = congTyModel.getChiNhanhCongTyModelList().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(congTyModel.getTencongty());

        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);
    }

    private void DownLoadHinhTienIch(){

        for (String matienich : congTyModel.getTienich()){
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhCongTy = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhCongTy.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietCongTyActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50,50);
                            layoutParams.setMargins(10,10,10,10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageTienIch.setPadding(5,5,5,5);


                            imageTienIch.setImageBitmap(bitmap);
                            khungTienIch.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.khungTinhNang:
                Intent iDanDuong = new Intent(this,DanDuongToiCongTyActivity.class);
                iDanDuong.putExtra("latitude", congTyModel.getChiNhanhCongTyModelList().get(0).getLatitude());
                iDanDuong.putExtra("longitude", congTyModel.getChiNhanhCongTyModelList().get(0).getLongitude());
                startActivity(iDanDuong);
                break;

            case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(this,BinhLuanActivity.class);
                iBinhLuan.putExtra("macongty", congTyModel.getMacongty());
                iBinhLuan.putExtra("tencty", congTyModel.getTencongty());
                iBinhLuan.putExtra("diachi", congTyModel.getChiNhanhCongTyModelList().get(0).getDiachi());
                startActivity(iBinhLuan);
        }
    }
}
