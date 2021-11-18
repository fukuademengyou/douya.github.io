package com.example.profile.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bean.RegisterBean;
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

public class RegisterActivity extends Activity {

    OkHttpClient client = new OkHttpClient.Builder().build();
    RegisterBean registerBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText et_username = findViewById(R.id.et_reg_username);
        EditText et_password = findViewById(R.id.et_reg_password);
        Button btn_register = findViewById(R.id.btn_register);
        ImageButton btn_cancel_register = findViewById(R.id.cancel_login_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(et_username, et_password);
            }
        });
        btn_cancel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    private void register(EditText et_username,EditText et_password) {
        String userAccount = et_username.getText().toString();
        String userPassword = et_password.getText().toString();

        if (userAccount.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入用户账号",Toast.LENGTH_SHORT).show();
        } else if (userPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            new Thread() {
                @Override
                public void run() {
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    String jsonStr = "{\"name\":\""+userAccount+"\",\"password\":\""+userPassword+"\"}";
                    RequestBody body = RequestBody.create(JSON, jsonStr);
                    Request request = new Request.Builder()
                            .url(Constants.SIGH_UP_URL)
                            .post(body)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e("register","register failure");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            registerBean = gson.fromJson(str, RegisterBean.class);
                            Log.i("返回的数据",str);
                            if(registerBean.isSuccess()) {
                                RegisterActivity.this.finish();
                            }
                        }
                    });

                }
            }.start();

        }
    }

}