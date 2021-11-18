package com.example.subject.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.base.BaseFragment;
import com.example.cuishanyi.MainActivity;
import com.example.cuishanyi.R;
import com.example.profile.activity.LoginActivity;


public class PreSubjectFragment extends BaseFragment {
    private static final String TAG = "ProfileFragment";
    private Button goto_login;
    @Override
    public View initView() {
        Log.e(TAG,"主页面的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_pre_subject, null);
        goto_login = view.findViewById(R.id.goto_login);
        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void initData() {
        super.initData();
        Log.e(TAG, "主页面的Fragment数据被初始化了");
    }


}