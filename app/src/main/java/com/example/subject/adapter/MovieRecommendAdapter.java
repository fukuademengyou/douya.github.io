package com.example.subject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.bean.HotMovieBean;
import com.example.cuishanyi.R;
import com.example.utils.CornerTransform;

import java.util.List;

public class MovieRecommendAdapter extends RecyclerView.Adapter<MovieRecommendAdapter.ViewHolder> {
    private List<HotMovieBean.DataDTO> movieVOList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View movieVOView;
        ImageView movieImage;
        TextView movieName;
        RatingBar movieRating;
        TextView movieGrade;

        public ViewHolder(View view) {
            super(view);
            movieVOView = view;
            movieImage = view.findViewById(R.id.recommend_movie_item_image);
            movieName = view.findViewById(R.id.recommend_movie_item_name);
            movieRating = view.findViewById(R.id.recommend_movie_item_rating);
            movieGrade = view.findViewById(R.id.recommend_movie_item_grade);
        }
    }

    public MovieRecommendAdapter(List<HotMovieBean.DataDTO> movieVOList, Context context) {
        this.movieVOList = movieVOList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_movie_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        HotMovieBean.DataDTO movieVO = movieVOList.get(position);
        loadRoundImage(mContext,movieVO.getPicUrl(),holder.movieImage);
        holder.movieName.setText(movieVO.getName());
        holder.movieGrade.setText(movieVO.getGrade()+"");
        holder.movieRating.setRating((float)movieVO.getGrade()/2);
        holder.movieVOView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieVOList.size();
    }

    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
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
