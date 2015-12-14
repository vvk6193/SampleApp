package com.vivek.sampleapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.interfaces.ClickListener;

/**
 * Created by v.vekariya on 11/23/2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;

    ClickListener clickListener;


    public RVAdapter (Context ctx, ClickListener clickListener) {
        inflater = LayoutInflater.from(ctx);
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.itemClicked(500, "Holder");
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView image;
        ClickListener ClickListener;
        int position;

        public ViewHolder(View itemView, ClickListener ClickListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);
            image.setOnClickListener(this);
            textView.setOnClickListener(this);
            this.ClickListener = ClickListener;
        }

        @Override
        public void onClick(View v) {
            String type= "";
            switch (v.getId()) {
                case R.id.text:
                    type = "text";
                    break;
                case R.id.image:
                    type = "image";
                    break;
            }
            ClickListener.itemClicked(getAdapterPosition(),type + " " + getLayoutPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("getview","create view recycler view ");
        View v = inflater.inflate(R.layout.item,null);
        v.setOnClickListener(this);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("vvk","bindview recycler view " + position);
                holder.position = position;
                holder.textView.setText("item number " + position);
        holder.image.setImageResource(R.drawable.ic_doctor_default_blue);
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
