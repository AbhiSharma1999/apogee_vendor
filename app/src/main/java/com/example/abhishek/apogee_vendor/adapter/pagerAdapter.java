package com.example.abhishek.apogee_vendor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.abhishek.apogee_vendor.fragment.finished_declined_fragment;
import com.example.abhishek.apogee_vendor.fragment.pending_accepted_fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek on 20/3/19.
 */

public class pagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();

    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment)
    {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
