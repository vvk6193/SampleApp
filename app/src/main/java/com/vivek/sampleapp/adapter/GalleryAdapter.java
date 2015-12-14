package com.vivek.sampleapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.interfaces.ClickListener;
import com.vivek.sampleapp.mypicaso.Vivek;

import java.io.File;

/**
 * Created by vivek-pc on 12/14/2015.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    File[] files;
    LayoutInflater inflater;
    Context context;
    ClickListener clicklistener;
    public GalleryAdapter(Context context,File[] files,ClickListener clickListener) {
        this.files = files;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.clicklistener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_gallery,null),clicklistener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vivek.with(context).load(files[position]).initial(R.drawable.ic_doctor_default_blue).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return files.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        ClickListener listener;
        public ViewHolder(View itemView, ClickListener clicklistener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.gallery_recyclerview_Image);
            itemView.setOnClickListener(this);
            this.listener = clicklistener;
        }

        @Override
        public void onClick(View v) {
            listener.itemClicked(getAdapterPosition(),"");
        }
    }
}
