package com.vivek.sampleapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.mypicaso.Vivek;

import java.io.File;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class ImageFragment extends BaseFragment {


    File file;
    Context context;
    public ImageFragment(File file,Context context) {
        this.file = file;
        this.context = context;
    }

    ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        image = (ImageView) v.findViewById(R.id.pager_fragment_image_view);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Vivek.with(context).load(file).initial(R.drawable.ic_doctor_default_blue).keepInCache(false).into(image);
    }
}
