package com.vivek.sampleapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivek.sampleapp.R;

/**
 * Created by v.vekariya on 11/24/2015.
 */

public class MySimpleArrayAdapter extends BaseAdapter implements View.OnClickListener{
    private final Context context;
    private final String[] values;
    int position ;
    ClickListener clicklistener;

    LayoutInflater inflater;

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
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
        clicklistener.itemClicked(position,type);

    }
    public interface ClickListener {
        void itemClicked(int position, String type);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public MySimpleArrayAdapter(Context context, String[] values) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.values = values;
        this.clicklistener = (ClickListener)context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("getview","getview list view " + position);
        View rowView = convertView;
        ViewHolder vh;
        if(rowView == null) {
            rowView = inflater.inflate(R.layout.item, parent, false);
            vh = new ViewHolder();
            vh.textView = (TextView) rowView.findViewById(R.id.text);
            vh.imageView = (ImageView) rowView.findViewById(R.id.image);
            rowView.setTag(vh);
        } else {
            vh = (ViewHolder) rowView.getTag();
        }
        vh.imageView.setOnClickListener(this);
        vh.textView.setOnClickListener(this);
        vh.textView.setText(values[position]);
        // change the icon for Windows and iPhone
        String s = values[position];
        vh.imageView.setImageResource(R.drawable.ic_doctor_default_blue);
        return rowView;
    }

    /**
     * Created by v.vekariya on 11/24/2015.
     */
    public static class RVItemClickListener implements RecyclerView.OnItemTouchListener {

        public interface ItemClickListener {
            public void itemclicked(View v, int position);
        }

        Context context;
        ItemClickListener itemClickListener;
        GestureDetector gestureDetector;

        public RVItemClickListener(Context ctx, ItemClickListener itemClickListener) {
            this.context = ctx;
            this.itemClickListener = itemClickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View v = rv.findChildViewUnder(e.getX(),e.getY());
            if(v != null && gestureDetector.onTouchEvent(e)) {
                itemClickListener.itemclicked(v,rv.getChildAdapterPosition(v));
                return false;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
