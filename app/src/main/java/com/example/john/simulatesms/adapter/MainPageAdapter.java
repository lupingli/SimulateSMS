package com.example.john.simulatesms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by John on 2016/11/20.
 */

public class MainPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MainPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * 显示的视图
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    /**
     * 显示条目
     *
     * @return
     */
    @Override
    public int getCount() {
        return fragments.size();
    }
}
