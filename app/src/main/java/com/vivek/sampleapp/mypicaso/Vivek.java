package com.vivek.sampleapp.mypicaso;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;

import java.io.File;

/**
 * Created by vivek-pc on 12/14/2015.
 */
public class Vivek {
    private Context context;
    private Vivek(Context context) {
        this.context = context;
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());

    }

    public static Vivek with(Context context) {
        return new Vivek(context);
    }

    public ImageLoadTask load(int resourceId) {
        return new ImageLoadTask(resourceId,context);
    }

    public ImageLoadTask load(File file) {
        return new ImageLoadTask(file,context);
    }

    public ImageLoadTask load(String path) {
        return new ImageLoadTask(path,context);
    }

    public ImageLoadTask load(Uri uri) {
        return new ImageLoadTask(uri,context);
    }


}
