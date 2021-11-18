package com.example.profile.fragment;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.base.BaseFragment;
import com.example.bean.LogoutBean;
import com.example.cuishanyi.R;
import com.example.utils.Constants;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AfterLoginFragment extends BaseFragment {
    private static final String TAG = "AfterLoginFragment";
    private String username;
    private String token;
    LogoutBean logoutBean;
    OkHttpClient client = new OkHttpClient();
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.activity_after_login, null);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        TextView tv_text = view.findViewById(R.id.tv_text);
        SharedPreferences sp = mContext.getSharedPreferences("userInfo",MODE_PRIVATE);
        token = sp.getString("token",null);
        username = sp.getString("username",null);

        tv_text.setText(username);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout(token);
            }
        });
        return view;
    }
    public void initData() {
        super.initData();
    }

    public void Logout(String token) {
        Request request = new Request.Builder()
                .addHeader("token",token)
                .url(Constants.SIGH_OUT_URL)
                .post(RequestBody.create(null,""))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                Gson gson = new Gson();
                logoutBean = gson.fromJson(str, LogoutBean.class);
                if(logoutBean.isSuccess()){
                    SharedPreferences userInfo = mContext.getSharedPreferences("userInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = userInfo.edit();
                    editor.clear();
                    editor.apply();
                    Log.i("登出","清空数据");
                    getActivity().finish();
                }
            }
        });
    }
}