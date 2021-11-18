package com.example.profile.fragment;

import android.view.View;

import com.example.base.BaseFragment;
import com.example.cuishanyi.R;

public class ProfileFragment extends BaseFragment {
    private static final String TAG = "ProfileFragment";
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_profile, null);
        return view;
    }
    public void initData() {
        super.initData();
    }
}
