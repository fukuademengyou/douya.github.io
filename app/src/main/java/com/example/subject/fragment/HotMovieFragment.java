package com.example.subject.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.base.BaseFragment;
import com.example.bean.HotMovieBean;
import com.example.cuishanyi.MainActivity;
import com.example.cuishanyi.R;
import com.example.profile.activity.LoginActivity;
import com.example.subject.activity.MovieDetailActivity;
import com.example.utils.Constants;
import com.example.utils.CornerTransform;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotMovieFragment extends BaseFragment {
    private static final String TAG = "HotMovieFragment";
    ImageView fragment_hot_movie_image1;
    ImageView fragment_hot_movie_image2;
    ImageView fragment_hot_movie_image3;
    ImageView fragment_hot_movie_image4;
    ImageView fragment_hot_movie_image5;
    ImageView fragment_hot_movie_image6;

    TextView fragment_hot_movie_cname1;
    TextView fragment_hot_movie_cname2;
    TextView fragment_hot_movie_cname3;
    TextView fragment_hot_movie_cname4;
    TextView fragment_hot_movie_cname5;
    TextView fragment_hot_movie_cname6;

    RatingBar fragment_hot_movie_rating1;
    RatingBar fragment_hot_movie_rating2;
    RatingBar fragment_hot_movie_rating3;
    RatingBar fragment_hot_movie_rating4;
    RatingBar fragment_hot_movie_rating5;
    RatingBar fragment_hot_movie_rating6;

    TextView fragment_hot_movie_grade1;
    TextView fragment_hot_movie_grade2;
    TextView fragment_hot_movie_grade3;
    TextView fragment_hot_movie_grade4;
    TextView fragment_hot_movie_grade5;
    TextView fragment_hot_movie_grade6;
    HotMovieBean hotMovieBean;
    String token;
    List<HotMovieBean.DataDTO> data;
    private SharedPreferences userInfo;
    OkHttpClient client = new OkHttpClient();


    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_movie, null);
        fragment_hot_movie_image1 = view.findViewById(R.id.fragment_hot_movie_image1);
        fragment_hot_movie_image2 = view.findViewById(R.id.fragment_hot_movie_image2);
        fragment_hot_movie_image3 = view.findViewById(R.id.fragment_hot_movie_image3);
        fragment_hot_movie_image4 = view.findViewById(R.id.fragment_hot_movie_image4);
        fragment_hot_movie_image5 = view.findViewById(R.id.fragment_hot_movie_image5);
        fragment_hot_movie_image6 = view.findViewById(R.id.fragment_hot_movie_image6);

        fragment_hot_movie_cname1 = view.findViewById(R.id.fragment_hot_movie_cname1);
        fragment_hot_movie_cname2 = view.findViewById(R.id.fragment_hot_movie_cname2);
        fragment_hot_movie_cname3 = view.findViewById(R.id.fragment_hot_movie_cname3);
        fragment_hot_movie_cname4 = view.findViewById(R.id.fragment_hot_movie_cname4);
        fragment_hot_movie_cname5 = view.findViewById(R.id.fragment_hot_movie_cname5);
        fragment_hot_movie_cname6 = view.findViewById(R.id.fragment_hot_movie_cname6);

        fragment_hot_movie_rating1 = view.findViewById(R.id.fragment_hot_movie_rating1);
        fragment_hot_movie_rating2 = view.findViewById(R.id.fragment_hot_movie_rating2);
        fragment_hot_movie_rating3 = view.findViewById(R.id.fragment_hot_movie_rating3);
        fragment_hot_movie_rating4 = view.findViewById(R.id.fragment_hot_movie_rating4);
        fragment_hot_movie_rating5 = view.findViewById(R.id.fragment_hot_movie_rating5);
        fragment_hot_movie_rating6 = view.findViewById(R.id.fragment_hot_movie_rating6);

        fragment_hot_movie_grade1 = view.findViewById(R.id.fragment_hot_movie_grade1);
        fragment_hot_movie_grade2 = view.findViewById(R.id.fragment_hot_movie_grade2);
        fragment_hot_movie_grade3 = view.findViewById(R.id.fragment_hot_movie_grade3);
        fragment_hot_movie_grade4 = view.findViewById(R.id.fragment_hot_movie_grade4);
        fragment_hot_movie_grade5 = view.findViewById(R.id.fragment_hot_movie_grade5);
        fragment_hot_movie_grade6 = view.findViewById(R.id.fragment_hot_movie_grade6);

        userInfo = mContext.getSharedPreferences("userInfo",MODE_PRIVATE);
        token = userInfo.getString("token",null);
        initData(token);
        try {
             Thread.sleep(2000);
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        fragment_hot_movie_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id",data.get(0).getId());
                startActivity(intent);
            }
        });
        fragment_hot_movie_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id",data.get(1).getId());
                startActivity(intent);
            }
        });
        fragment_hot_movie_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id",data.get(2).getId());
                startActivity(intent);
            }
        });
        fragment_hot_movie_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id",data.get(0).getId());
                startActivity(intent);
            }
        });
        fragment_hot_movie_image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id",data.get(1).getId());
                startActivity(intent);
            }
        });
        fragment_hot_movie_image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("id",data.get(2).getId());
                startActivity(intent);
            }
        });
        loadRoundImage(mContext,data.get(0).getPicUrl(),fragment_hot_movie_image1);
        loadRoundImage(mContext,data.get(1).getPicUrl(),fragment_hot_movie_image2);
        loadRoundImage(mContext,data.get(2).getPicUrl(),fragment_hot_movie_image3);
        loadRoundImage(mContext,data.get(0).getPicUrl(),fragment_hot_movie_image4);
        loadRoundImage(mContext,data.get(1).getPicUrl(),fragment_hot_movie_image5);
        loadRoundImage(mContext,data.get(2).getPicUrl(),fragment_hot_movie_image6);
        fragment_hot_movie_cname1.setText(data.get(0).getName());
        fragment_hot_movie_grade1.setText(data.get(0).getGrade()+"");
        fragment_hot_movie_rating1.setRating((float)data.get(0).getGrade()/2);
        //第二张图片
        fragment_hot_movie_cname2.setText(data.get(1).getName());

        fragment_hot_movie_grade2.setText(data.get(1).getGrade()+"");
        fragment_hot_movie_rating2.setRating((float)data.get(1).getGrade()/2);
        //第三张图片
        fragment_hot_movie_cname3.setText(data.get(2).getName());

        fragment_hot_movie_grade3.setText(data.get(2).getGrade()+"");
        fragment_hot_movie_rating3.setRating((float)data.get(2).getGrade()/2);

        //第四张图片
        fragment_hot_movie_cname4.setText(data.get(0).getName());

        fragment_hot_movie_grade4.setText(data.get(0).getGrade()+"");
        fragment_hot_movie_rating4.setRating((float)data.get(0).getGrade()/2);
        //第五张图片
        fragment_hot_movie_cname5.setText(data.get(1).getName());

        fragment_hot_movie_grade5.setText(data.get(1).getGrade()+"");
        fragment_hot_movie_rating5.setRating((float)data.get(1).getGrade()/2);
        //第六张图片
        fragment_hot_movie_cname6.setText(data.get(2).getName());

        fragment_hot_movie_grade6.setText(data.get(2).getGrade()+"");
        fragment_hot_movie_rating6.setRating((float)data.get(2).getGrade()/2);
        return view;
    }
    public void initData(String token) {
        super.initData();
        Request request = new Request.Builder()
                .url(Constants.HOT_MOVIE_URL)
                .get()
                .addHeader("token",token)
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
                hotMovieBean = gson.fromJson(str, HotMovieBean.class);

                if(hotMovieBean.isSuccess() == true) {
                    //第一张图片
                    Log.i("HotMovieFragment","热门电影请求成功");
                    data = hotMovieBean.getData();
                }
            }
        });

    }
    public static void loadRoundImage(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new CenterCrop(), new CornerTransform(context,10))
                .into(imageView);
    }
}
