package com.laptopnct.dichvunhadat.Control.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.laptopnct.dichvunhadat.View.Fragment.TimNhaFragment;
import com.laptopnct.dichvunhadat.View.Fragment.OdauFragment;

/**
 * Created by Cong Thanh
 */

public class AdapterViewPagerTrangChu extends FragmentStatePagerAdapter {
    TimNhaFragment timNhaFragment;
    OdauFragment odauFragment;

    public AdapterViewPagerTrangChu(FragmentManager fm) {
        super(fm);
        timNhaFragment = new TimNhaFragment();
        odauFragment = new OdauFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return odauFragment;

            case 1:
                return timNhaFragment;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
