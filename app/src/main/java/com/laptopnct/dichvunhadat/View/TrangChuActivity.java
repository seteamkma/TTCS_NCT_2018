package com.laptopnct.dichvunhadat.View;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.laptopnct.dichvunhadat.Control.Adapters.AdapterViewPagerTrangChu;
import com.laptopnct.dichvunhadat.R;



public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, Button.OnClickListener {

    ViewPager viewPagerTrangChu;
    RadioButton rdOdau, rdtimNha;
    RadioGroup groupOdauTimnha;
    ImageButton btnThemCongTy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        viewPagerTrangChu = (ViewPager) findViewById(R.id.viewpager_trangchu);
        rdOdau = (RadioButton) findViewById(R.id.rd_odau);
        rdtimNha = (RadioButton) findViewById(R.id.rd_timnha);
        groupOdauTimnha = (RadioGroup) findViewById(R.id.group_odau_timnha);

        btnThemCongTy = (ImageButton) findViewById(R.id.btnThemCongTy);

        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangChu);

        viewPagerTrangChu.addOnPageChangeListener(this);
        groupOdauTimnha.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                rdOdau.setChecked(true);
                break;

            case 1:
                rdtimNha.setChecked(true);
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rd_timnha:
                viewPagerTrangChu.setCurrentItem(1);
                break;

            case R.id.rd_odau:
                viewPagerTrangChu.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}

