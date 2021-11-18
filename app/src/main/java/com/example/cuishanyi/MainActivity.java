package com.example.cuishanyi;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.base.BaseFragment;
import com.example.bean.LoginBean;
import com.example.bean.LogoutBean;
import com.example.profile.fragment.AfterLoginFragment;
import com.example.profile.fragment.ProfileFragment;
import com.example.subject.fragment.PreSubjectFragment;
import com.example.subject.fragment.SubjectFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private ArrayList<BaseFragment> fragments;
    RadioGroup rgMain;
    RadioButton rbSubject;
    RadioButton rbProfile;

    LoginBean loginBean;
    LogoutBean logoutBean;
    private String username;
    private String token;

    private int position = 0;
    private boolean flag = false;
    //缓存的fragment
    private Fragment tempFragment;
    PreSubjectFragment preSubjectFragment = new PreSubjectFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgMain = findViewById(R.id.rg_main);
        rbSubject = findViewById(R.id.rb_subject);
        rbProfile = findViewById(R.id.rb_profile);
        //下方按钮
        setBounds(R.drawable.subject_button_selector,rbSubject);
        setBounds(R.drawable.profile_button_selector,rbProfile);
        //两个页面
        Intent intent = getIntent();
        flag = intent.getBooleanExtra("flag",false);
        initFragment();
        initListener();

    }


    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_subject:
                        position = 0;
                        break;
                    case R.id.rb_profile:
                        position = 1;
                        break;
                    default:
                        position = 0;
                        break;
                }

                //根据位置取不同的Fragment
                BaseFragment baseFragment = getFragment(position);
                //第一个参数是上次显示的fragment,第二个参数是现在要显示的fragment
                switchFragment(tempFragment, baseFragment);
            }
        });
        rgMain.check(R.id.rb_subject);
    }

    private void initFragment() {

        //这四个Fragment其实都是运行在MainActivity里的
        //但是我不知道在登录后是否会立刻刷新Fragment
        if(flag) {
            fragments = new ArrayList<>();
            fragments.add(new SubjectFragment());
            fragments.add(new AfterLoginFragment());
        } else {
            fragments = new ArrayList<>();
            fragments.add(new PreSubjectFragment());
            fragments.add(new ProfileFragment());
        }

    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void setBounds(int drawableId, RadioButton radioButton) {
        //定义底部标签图片大小和位置
        Drawable drawable_news = getResources().getDrawable(drawableId);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 150, 150);
        //设置图片在文字的哪个方向
        radioButton.setCompoundDrawables(null,drawable_news,null, null);
    }



}