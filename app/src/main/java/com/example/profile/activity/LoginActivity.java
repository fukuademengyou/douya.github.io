package com.example.profile.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.LoginBean;
import com.example.cuishanyi.MainActivity;
import com.example.cuishanyi.R;
import com.example.utils.Constants;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity {
    private SharedPreferences userInfo;
    OkHttpClient client = new OkHttpClient.Builder().build();
    LoginBean loginBean;
    private static final String TAG = "SharedPreferencesTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText etUserName = findViewById(R.id.et_in_username);
        EditText etPassword = findViewById(R.id.et_in_password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvRegister = findViewById(R.id.tv_goto_register);
        TextView tvCancel = findViewById(R.id.tv_cancel_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(etUserName,etPassword);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        userInfo = getSharedPreferences("userInfo",MODE_PRIVATE);
        String username = userInfo.getString("username",null);
        String password = userInfo.getString("password",null);
        Log.i(TAG,"??????????????????");
        Log.i(TAG,"username:"+ username +", password:" + password);
        if (username != null && password != null) {
            Login(username, password);

        }
    }

    private void login(EditText etUserName, EditText etPassword) {
        String userAccount = etUserName.getText().toString();
        String userPassword = etPassword.getText().toString();

        if (userAccount.isEmpty()) {
            Toast.makeText(LoginActivity.this, "?????????????????????",Toast.LENGTH_SHORT).show();
        } else if (userPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
        } else {
            Login(userAccount, userPassword);
        }
    }

    public void Login(String username, String password) {
        //??????????????????????????????
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonStr = "{\"name\":\""+username+"\",\"password\":\""+password+"\"}";
        RequestBody body = RequestBody.create(JSON, jsonStr);
        //??????request??????
        Request request = new Request.Builder()
                .url(Constants.LOGIN_URL)
                .post(body)
                .build();
        //??????Response??????
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("postHttp","post failure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                Log.e("?????????JSON??????",str);
                Gson gson = new Gson();
                loginBean = gson.fromJson(str, LoginBean.class);
                //????????????????????????,???MainActivity??????????????????,?????????fragment???
                if (loginBean.isSuccess() == true) {
                    userInfo = getSharedPreferences("userInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = userInfo.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.putString("token",loginBean.getData());
                    editor.apply();
                    Log.i(TAG,"????????????????????????");

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("flag",true);
                    startActivity(intent);
                    Log.i("????????????","??????????????????");

                }
            }
        });
    }
}