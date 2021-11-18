package com.example.subject.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.bean.CommentBean;
import com.example.bean.IsFavorBean;
import com.example.bean.ListItem;
import com.example.bean.MovieDetailBean;
import com.example.bean.UpdateFavorBean;
import com.example.cuishanyi.R;
import com.example.utils.Constants;
import com.example.utils.CornerTransform;
import com.example.utils.SpacesItemDecoration;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MovieDetailActivity extends Activity {

    LinearLayout titlebar_ll;
    RelativeLayout content_ll;
    //隐藏的动画
    ImageButton activity_movie_detail_back1;
    ImageButton activity_movie_detail_back2;
    ImageView activity_movie_detail_title_image;
    TextView activity_movie_detail_title_name;
    RatingBar activity_movie_detail_title_rating;
    TextView activity_movie_detail_title_grade;
    //第一部分
    ImageView movie_detail_movie_image;
    TextView movie_detail_movie_name;
    TextView movie_detail_brief_introduction;
    Button btn_movie_detail_favor;
    //第二部分
    TextView grade_item_grade;
    RatingBar grade_item_rating;
    ProgressBar grade_item_five_grade_progressbar;
    ProgressBar grade_item_four_grade_progressbar;
    ProgressBar grade_item_three_grade_progressbar;
    ProgressBar grade_item_two_grade_progressbar;
    ProgressBar grade_item_one_grade_progressbar;
    //第三部分
    TextView introduction_item_text;
    //第四部分
    RecyclerView rv_actor_list;
    //第五部分
    RecyclerView rv_preview;
    //第六部分
    RecyclerView rv_comment;

    private String token;
    OkHttpClient client = new OkHttpClient();
    private MovieDetailBean movieDetailBean;
    private CommentBean commentBean;
    private UpdateFavorBean updateFavorBean;
    private IsFavorBean isFavorBean;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.LayoutManager layoutManager3;
    private PreviewAdapter adapter2;
    private CommentAdapter adapter3;
    private ActorAdapter adapter1;
    private MovieDetailBean.DataDTO data;
    private List<ListItem> itemList;
    private List<CommentBean.DataDTO> comment_data;
    private List<MovieDetailBean.DataDTO.ActorListDTO> actor_data;
    boolean flag1 = false;
    boolean flag2 = false;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;
    int total;
    int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie_id = getIntent().getIntExtra("movie_id",3);
        Log.i("movie_id",movie_id+"");
        //隐藏动画
        titlebar_ll = findViewById(R.id.titlebar_ll);
        content_ll = findViewById(R.id.content_ll);
        content_ll.setVisibility(View.GONE);
        activity_movie_detail_back1 = findViewById(R.id.activity_movie_detail_back1);
        activity_movie_detail_back2 = findViewById(R.id.activity_movie_detail_back2);
        activity_movie_detail_title_image = findViewById(R.id.activity_movie_detail_title_image);
        activity_movie_detail_title_name = findViewById(R.id.activity_movie_detail_title_name);
        activity_movie_detail_title_rating = findViewById(R.id.activity_movie_detail_title_rating);
        activity_movie_detail_title_grade = findViewById(R.id.activity_movie_detail_title_grade);
        //第一部分
        movie_detail_movie_image = findViewById(R.id.movie_detail_movie_image);
        movie_detail_movie_name = findViewById(R.id.movie_detail_movie_name);
        movie_detail_brief_introduction = findViewById(R.id.movie_detail_brief_introduction);
        btn_movie_detail_favor = findViewById(R.id.btn_movie_detail_favor);
        //第二部分
        grade_item_grade = findViewById(R.id.grade_item_grade);
        grade_item_rating = findViewById(R.id.grade_item_rating);
        grade_item_five_grade_progressbar = findViewById(R.id.grade_item_five_star_progressbar);
        grade_item_four_grade_progressbar = findViewById(R.id.grade_item_four_star_progressbar);
        grade_item_three_grade_progressbar = findViewById(R.id.grade_item_three_star_progressbar);
        grade_item_two_grade_progressbar = findViewById(R.id.grade_item_two_star_progressbar);
        grade_item_one_grade_progressbar = findViewById(R.id.grade_item_one_star_progressbar);
        //第三部分
        introduction_item_text = findViewById(R.id.introduction_item_text);
        //第四部分
        rv_actor_list = findViewById(R.id.rv_actor_list);
        //第五部分
        rv_preview = findViewById(R.id.rv_preview);
        //第六部分
        rv_comment = findViewById(R.id.rv_comment);
        //加载动画效果
        initListener();
        initData(movie_id);
        //获取返回的数据
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity_movie_detail_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activity_movie_detail_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_movie_detail_favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody body = new FormBody.Builder()
                        .add("movieId",movie_id+"")
                        .build();
                //创建request对象
                Request request = new Request.Builder()
                        .url(Constants.UPDATE_FAVOR_URL)
                        .addHeader("token",token)
                        .post(body)
                        .build();
                //获取Response对象
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("postHttp","post failure");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String str = response.body().string();
                        Gson gson = new Gson();
                        updateFavorBean = gson.fromJson(str,UpdateFavorBean.class);
                        if(updateFavorBean.isSuccess() == true) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    flag1 = !flag1;
                                    Drawable drawable;
                                    if(flag1) {
                                        drawable = getResources().getDrawable(R.drawable.star_yellow);
                                    } else {
                                        drawable = getResources().getDrawable(R.drawable.star_white);
                                    }
                                    btn_movie_detail_favor.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

                                }
                            });
                        }
                    }
                });

            }
        });

        //设置隐藏标题和第一部分
        Glide.with(MovieDetailActivity.this)
                .load(data.getPicUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(activity_movie_detail_title_image);
        loadRoundImage(MovieDetailActivity.this,data.getPicUrl(),movie_detail_movie_image);
        activity_movie_detail_title_name.setText(data.getName());
        movie_detail_movie_name.setText(data.getName());
        activity_movie_detail_title_rating.setRating((float) data.getGrade()/2);
        movie_detail_brief_introduction.setText(data.getShowPlace()+ " / " +data.getLable()+" / "+data.getShowDate()+ " / " +data.getLastTime());
        activity_movie_detail_title_grade.setText(data.getGrade()+"");
        //第二部分
        grade_item_grade.setText(data.getGrade()+"");
        grade_item_rating.setRating((float) data.getGrade()/2);
        for (int i = 0; i < data.getGradeInfo().size(); i++) {
            total += data.getGradeInfo().get(i);
        }
        grade_item_five_grade_progressbar.setProgress(data.getGradeInfo().get(0)*100/total);
        grade_item_four_grade_progressbar.setProgress(data.getGradeInfo().get(1)*100/total);
        grade_item_three_grade_progressbar.setProgress(data.getGradeInfo().get(2)*100/total);
        grade_item_two_grade_progressbar.setProgress(data.getGradeInfo().get(3)*100/total);
        grade_item_one_grade_progressbar.setProgress(data.getGradeInfo().get(4)*100/total);
        //第三部分
        introduction_item_text.setText(data.getIntroduction());
        //第四部分
        int space = 10;
        layoutManager1 = new LinearLayoutManager(MovieDetailActivity.this, RecyclerView.HORIZONTAL,false);
        adapter1 = new ActorAdapter(actor_data);
        rv_actor_list.setLayoutManager(layoutManager1);
        rv_actor_list.setAdapter(adapter1);
        rv_actor_list.addItemDecoration(new SpacesItemDecoration(space));


        //第五部分,一些图片
        layoutManager2 = new LinearLayoutManager(MovieDetailActivity.this,RecyclerView.HORIZONTAL,false);
        adapter2 = new PreviewAdapter(data.getStagePhotoUrl());
        rv_preview.setLayoutManager(layoutManager2);
        rv_preview.setAdapter(adapter2);
        rv_preview.addItemDecoration(new SpacesItemDecoration(space));
        //第六部分,评论区
        layoutManager3 = new LinearLayoutManager(MovieDetailActivity.this,RecyclerView.VERTICAL,false);
        adapter3 = new CommentAdapter(comment_data);
        rv_comment.setLayoutManager(layoutManager3);
        rv_comment.setAdapter(adapter3);


    }

    private void initData(int movie_id) {
        SharedPreferences userInfo = getSharedPreferences("userInfo",MODE_PRIVATE);
        token = userInfo.getString("token",null);
        Request request1 = new Request.Builder()
                .url(Constants.MOVIE_ID_URL + "/" + movie_id)
                .get()
                .addHeader("token",token)
                .build();
        Call call1 = client.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                Gson gson = new Gson();
                movieDetailBean = gson.fromJson(str,MovieDetailBean.class);

                if (movieDetailBean.isSuccess() == true) {
                    data = movieDetailBean.getData();
                    actor_data = data.getActorList();
                }
            }
        });

        HttpUrl.Builder urlBuilder1 = HttpUrl.parse(Constants.LIST_REMARK_URL).newBuilder();
        urlBuilder1.addQueryParameter("id",movie_id+"");
        Request request2 = new Request.Builder()
                .url(urlBuilder1.build())
                .get()
                .addHeader("token",token)
                .build();
        Call call2 = client.newCall(request2);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                Gson gson = new Gson();
                commentBean = gson.fromJson(str,CommentBean.class);
                if (commentBean.isSuccess()) {
                    comment_data = commentBean.getData();
                }
            }
        });

        HttpUrl.Builder urlBuilder2 = HttpUrl.parse(Constants.IS_FAVOR_URL).newBuilder();
        urlBuilder2.addQueryParameter("movieId",movie_id+"");
        Request request3 = new Request.Builder()
                .url(urlBuilder2.build())
                .get()
                .addHeader("token",token)
                .build();
        Call call3 = client.newCall(request3);
        call3.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                Gson gson = new Gson();
                isFavorBean = gson.fromJson(str,IsFavorBean.class);
                if(isFavorBean.isSuccess() == true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            flag2 = isFavorBean.isData();
                            Drawable drawable;
                            drawable = getResources().getDrawable(R.drawable.star_white);
                            if(isFavorBean.isData()){
                                drawable = getResources().getDrawable(R.drawable.star_yellow);

                            }
                            btn_movie_detail_favor.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

                        }
                    });
                }
            }
        });
    }

    private MyOnTouchListener myOnTouchListener;
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean dispatchTouchEvent(MotionEvent ev);
    }

    private void initListener() {
        myOnTouchListener = new MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        registerMyOnTouchListener(myOnTouchListener);
    }

    private boolean isDown = false;
    private boolean isUp = false;
    private boolean dispathTouchEvent(MotionEvent event){
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 12 && !mIsTitleHide && !down ;
                isDown = dX < 8 && dY > 12 && mIsTitleHide && down;
                if (isUp) {
                    HideCell();
                } else if (isDown) {
                    ShowCell();
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;
    }


    @SuppressLint("NewApi")
    private void HideCell(){
        int TitleHeight = titlebar_ll.getMeasuredHeight();
        content_ll.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(titlebar_ll, "translationY", 0,-TitleHeight),
                ObjectAnimator.ofFloat(content_ll, "translationY", 0,-TitleHeight)
        );
        set.setDuration(300).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsAnim = false;
            }
        }, 300);
    }

    @SuppressLint("NewApi")
    private void ShowCell() {
        int TitleHeight = titlebar_ll.getMeasuredHeight();
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(titlebar_ll, "translationY", -TitleHeight, 0),
                ObjectAnimator.ofFloat(content_ll, "translationY", -TitleHeight, 0)
        );
        set.setDuration(300).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsAnim = false;
            }
        }, 300);
    }

    //加载圆角图片
    //加载圆角图片
    public static void loadRoundImage(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new CenterCrop(), new CornerTransform(context,10))
                .into(imageView);
    }

    public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {
        private List<String> data;
        public PreviewAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            loadRoundImage(MovieDetailActivity.this,data.get(position), holder.imageView);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.preview_image);
            }
        }
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
        private List<CommentBean.DataDTO> comment_data;
        public CommentAdapter(List<CommentBean.DataDTO> comment_data) {
            this.comment_data = comment_data;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            loadRoundImage(MovieDetailActivity.this,comment_data.get(position).getUserPicUrl(), holder.comment_item_image);
            holder.comment_item_name.setText(comment_data.get(position).getUserName());
            holder.comment_item_remark.setText(comment_data.get(position).getContent());
            holder.comment_item_rating.setRating((float) comment_data.get(position).getGrade()/2);
            holder.comment_item_people.setText(comment_data.get(position).getFavorPeople() + "人");

        }

        @Override
        public int getItemCount() {
            return comment_data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView comment_item_image;
            TextView comment_item_name;
            RatingBar comment_item_rating;
            TextView comment_item_remark;
            TextView comment_item_people;
            public ViewHolder(View itemView) {
                super(itemView);
                comment_item_image = itemView.findViewById(R.id.comment_item_image);
                comment_item_name = itemView.findViewById(R.id.comment_item_name);
                comment_item_rating = itemView.findViewById(R.id.comment_item_rating);
                comment_item_remark = itemView.findViewById(R.id.comment_item_remark);
                comment_item_people = itemView.findViewById(R.id.comment_item_people);
            }
        }
    }

    public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {
        private List<MovieDetailBean.DataDTO.ActorListDTO> actor_data;
        public ActorAdapter(List<MovieDetailBean.DataDTO.ActorListDTO> actor_data) {
            this.actor_data = actor_data;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            loadRoundImage(MovieDetailActivity.this,actor_data.get(position).getPicUrl(), holder.actor_item_image);
            holder.actor_item_cname.setText(actor_data.get(position).getNameC());
            holder.actor_item_ename.setText(actor_data.get(position).getNameE());

        }

        @Override
        public int getItemCount() {
            return actor_data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView actor_item_image;
            TextView actor_item_cname;
            TextView actor_item_ename;
            public ViewHolder(View itemView) {
                super(itemView);
                actor_item_image = itemView.findViewById(R.id.actor_item_image);
                actor_item_cname = itemView.findViewById(R.id.actor_item_cname);
                actor_item_ename = itemView.findViewById(R.id.actor_item_ename);
            }
        }
    }

}