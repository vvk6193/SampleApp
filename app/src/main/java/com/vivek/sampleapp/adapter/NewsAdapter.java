package com.vivek.sampleapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.interfaces.ClickListener;
import com.vivek.sampleapp.modal.NewsItem;
import com.vivek.sampleapp.mypicaso.Vivek;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek-pc on 12/12/2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    LayoutInflater layoutInflater;
    public static Context context;
    List<NewsItem> newsList;
    ClickListener listener;
    public List<String> visitedIds;

    public NewsAdapter(Context ctx, List<NewsItem> news, ClickListener clickListener) {
        this.layoutInflater = LayoutInflater.from(ctx);
        context = ctx;
        this.newsList = news;
        this.listener = clickListener;
        visitedIds = new ArrayList<>();
    }
    static class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView headLine;
        TextView time;
        ClickListener listener;
        public NewsHolder(View itemView,ClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.news_image);
            headLine = (TextView) itemView.findViewById(R.id.news_headline);
            time = (TextView) itemView.findViewById(R.id.news_time);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            headLine.setTextColor(Color.GRAY);
            if(listener != null) {
                listener.itemClicked(getAdapterPosition(), "");
            } else {
                Toast.makeText(context,"clicked " + getAdapterPosition(),Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.layout_news_item,null);
        return new NewsHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        NewsItem news = newsList.get(position);
        holder.headLine.setText(news.getHeadLine());
        holder.time.setText(news.getDateLine());
        if(visitedIds.contains(news.getNewsItemId())) {
            holder.headLine.setTextColor(Color.GRAY);
        }
        if(news.getImage() != null) {
            Vivek.with(context).load(R.drawable.ic_doctor_default_blue).into(holder.image);
//            Picasso.with(context).l
//            Picasso.with(context).load(news.getImage().getThumb()).error(R.drawable.ic_doctor_default_blue).into(holder.image);
        } else {
            Vivek.with(context).load(R.drawable.ic_doctor_default_blue).into(holder.image);
//            Picasso.with(context).load(R.drawable.ic_doctor_default_blue).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}








