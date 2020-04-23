package com.jungel.test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TestFragmentAdapter extends FragmentPagerAdapter {

    public TestFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
