package com.example.subject.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.base.BaseFragment;
import com.example.bean.HotMovieBean;
import com.example.bean.ListItem;
import com.example.cuishanyi.R;
import com.example.subject.activity.MovieDetailActivity;
import com.example.subject.adapter.MovieListAdapter;
import com.example.subject.adapter.MovieRecommendAdapter;
import com.example.utils.Constants;
import com.example.utils.CornerTransform;
import com.example.utils.SpacesItemDecoration;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SubjectFragment extends BaseFragment {
    private List<HotMovieBean.DataDTO> movieList = new ArrayList<>();
    private ImageView iv_find_movie;
    private ImageView iv_douban_top;
    private ImageView iv_douban_guess;
    private ImageView iv_douban_film_list;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> title_list;
    private List<ListItem> itemList;
    private HotMovieBean hotMovieBean;
    private List<BaseFragment> fragmentList;
    SharedPreferences userInfo;
    String token;
    OkHttpClient client = new OkHttpClient();
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_subject,null);
        //第一部分:四个按钮
        iv_find_movie = view.findViewById(R.id.iv_find_movie);
        iv_douban_top = view.findViewById(R.id.iv_douban_top);
        iv_douban_guess = view.findViewById(R.id.iv_douban_guess);
        iv_douban_film_list = view.findViewById(R.id.iv_douban_film_list);
        userInfo = getActivity().getSharedPreferences("userInfo",MODE_PRIVATE);
        token = userInfo.getString("token",null);
        initData(token);

        iv_find_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(),"点击了找电影",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
        });
        iv_douban_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(),"点击了豆瓣榜单",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
        iv_douban_guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(),"点击了豆瓣猜",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
        iv_douban_film_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getActivity(),"点击了豆瓣片单",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });

        //第二部分:两个fragment
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        //设置影院热映和即将上映的电影
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new HotMovieFragment());
        fragmentList.add(new UpcomingMovieFragment());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //第三部分,三张图片的recyclerview
        itemList = new ArrayList<>();
        ListItem listItem1 = new ListItem(R.drawable.card1);
        itemList.add(listItem1);
        ListItem listItem2 = new ListItem(R.drawable.card2);
        itemList.add(listItem2);
        ListItem listItem3 = new ListItem(R.drawable.card3);
        itemList.add(listItem3);
        RecyclerView rv_topMovie = view.findViewById(R.id.rv_topMovie);
        int space = 10;
        rv_topMovie.addItemDecoration(new SpacesItemDecoration(space));
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_topMovie.setLayoutManager(layoutManager1);
        MovieListAdapter adapter1 = new MovieListAdapter(itemList);
        rv_topMovie.setAdapter(adapter1);


        //第四部分,为你推荐
        RecyclerView rv_recommend = view.findViewById(R.id.rv_recommend);
        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_recommend.setLayoutManager(layoutManager2);
        MovieRecommendAdapter adapter2 = new MovieRecommendAdapter(movieList, getActivity());
        rv_recommend.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new MovieRecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HotMovieBean.DataDTO dataDTO = movieList.get(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movie_id",dataDTO.getId());
                startActivity(intent);
            }
        });
        return view;
    }



    public void initData(String token) {
        super.initData();
        //联网请求主页的数据
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
                if(hotMovieBean.isSuccess()) {
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 3; j++) {
                            movieList.add(hotMovieBean.getData().get(j));
                        }
                    }
                }
            }
        });
    }


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> baseFragmentList;

        public ViewPagerAdapter(@NonNull FragmentManager fm, List<BaseFragment> baseFragmentList) {
            super(fm);
            this.baseFragmentList = baseFragmentList;
        }

        @Override
        public int getCount() {
            return baseFragmentList.size();
        }

        @NonNull
        @Override
        public BaseFragment getItem(int position) {
            return baseFragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "剧院热映";
                case 1:
                    return "即将上映";
            }
            return null;
        }


    }
