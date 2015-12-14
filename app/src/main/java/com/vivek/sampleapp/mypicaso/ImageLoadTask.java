package com.vivek.sampleapp.mypicaso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.vivek.sampleapp.ExecutorFramework.MyExecutor;
import com.vivek.sampleapp.networktask.BaseTask;
import com.vivek.sampleapp.networktask.WorkerClass;
import com.vivek.sampleapp.resttemplate.RequestExecutor;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by vivek-pc on 12/14/2015.
 */
public class ImageLoadTask extends BaseTask {


    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    final static int cacheSize = maxMemory / 8;

    private static LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };

    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;
    private static InternalHandler sHandler;
    int resourceId = -1;
    File file;
    String path;
    Uri uri;
    int type;
    ImageView imageView;
    String tag;
    Context context;
    int height;
    int width;
    boolean isLoaded = false;
    boolean shouldCache = true;
    public ImageLoadTask() {

    }

    public ImageLoadTask(int resourceId,Context context) {
        this();
        this.resourceId = resourceId;
        this.type = 0;
        this.context = context;
    }

    public ImageLoadTask(File file,Context context) {
        this();
        this.file = file;
        this.type = 1;
        this.context = context;
        this.tag = file.getAbsolutePath();
    }

    public ImageLoadTask(String path,Context context) {
        this();
        this.path = path;
        this.type = 2;
        this.context = context;
    }

    public ImageLoadTask(Uri uri,Context context) {
        this();
        this.uri = uri;
        this.type = 3;
        this.context = context;
    }

    public ImageLoadTask initial(int resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public ImageLoadTask keepInCache(Boolean shouldCache) {
        this.shouldCache = shouldCache;
        return this;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key+height, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key+height);
    }

    public void into(final ImageView image) {
        this.imageView = image;
        ViewGroup.LayoutParams param = imageView.getLayoutParams();
        height = param.height;
        width = param.width;
        if(height == 0 || width == 0 || height == -1 || width == -1 ) {
            image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    // Ensure you call it only once :
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    else {
                        image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    height = image.getHeight();
                    width = image.getWidth();
                    Log.d("vvk","on global layout" + height + " " +width);
                    if(!isLoaded) {
                        isLoaded = true;
                        switch (type) {
                            case 0:
                                imageView.setImageResource(resourceId);
                                break;
                            case 1:
                                if(resourceId != -1)
                                    imageView.setImageResource(resourceId);
                                imageView.setTag(file.getAbsolutePath());
                                Bitmap bitmap = getBitmapFromMemCache(file.getAbsolutePath());
                                if(bitmap == null) {
                                    Log.d("vvk","cache miss");
                                    WorkerClass<ImageLoadTask> worker = new WorkerClass<ImageLoadTask>(ImageLoadTask.this);
                                    MyExecutor.getInstance().submit(worker);
                                } else {
                                    Log.d("vvk","cache hit yooooo!!!");
                                    imageView.setImageBitmap(bitmap);
                                }
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        } else {
            switch (type) {
                case 0:
                    imageView.setImageResource(resourceId);
                    break;
                case 1:
                    if(resourceId != -1)
                        imageView.setImageResource(resourceId);
                    imageView.setTag(file.getAbsolutePath());
                    Bitmap bitmap = getBitmapFromMemCache(file.getAbsolutePath());
                    if(bitmap == null) {
                        Log.d("vvk","cache miss");
                        WorkerClass<ImageLoadTask> worker = new WorkerClass<ImageLoadTask>(this);
                        MyExecutor.getInstance().submit(worker);
                    } else {
                        Log.d("vvk","cache hit yooooo!!!");
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }



//        imageView.setImageResource(resourceId);

    }


    @Override
    public void runTask() {
        try {
            Bitmap bitmap = resizeBitmap();
            if(bitmap != null && shouldCache) {
                addBitmapToMemoryCache(file.getAbsolutePath(),bitmap);
            }
            if (tag.equalsIgnoreCase((String)imageView.getTag())) {
                postResult(bitmap);
            } else {
                Log.d("vvk" , "fatal !!! tag didnt match");
                bitmap = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Bitmap resizeBitmap() throws FileNotFoundException {
        Log.d("vvk","height " + height + " width " +width);
        Bitmap unscaledBitmap = decodeFile(file.getAbsolutePath(), (int) (width/2), (int)(height/2), 1);
        Bitmap scaledBitmap = createScaledBitmap(unscaledBitmap, (int) (width/2), (int)(height/2), 1);

        return scaledBitmap;
    }

    public Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int
            dstHeight, int scalingLogic) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static Bitmap decodeFile(String pathName, int dstWidth, int dstHeight, int scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);

        return unscaledBitmap;
    }

    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, int scalingLogic) {
        if (scalingLogic == 0) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcWidth / dstWidth;
            } else {
                return srcHeight / dstHeight;
            }
        } else {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcHeight / dstHeight;
            } else {
                return srcWidth / dstWidth;
            }
        }
    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, int scalingLogic) {
        if (scalingLogic == 1) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                final int srcRectWidth = (int) (srcHeight * dstAspect);
                final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
            } else {
                final int srcRectHeight = (int) (srcWidth / dstAspect);
                final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
                return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
            }
        } else {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, int scalingLogic) {
        if (scalingLogic == 0) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
            } else {
                return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
            }
        } else {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
    }

    private static Handler getHandler() {
        synchronized (RequestExecutor.class) {
            if (sHandler == null) {
                sHandler = new InternalHandler();
            }
            return sHandler;
        }
    }

    private Object postResult(Bitmap result) {
        @SuppressWarnings("unchecked")
        Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT,
                new ResultObject(result, imageView));
        message.sendToTarget();
        return result;
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    ResultObject result = (ResultObject) msg.obj;
                    result.imageView.setImageBitmap(result.bitmap);
                    break;
                case MESSAGE_POST_PROGRESS:
//                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }

    private class ResultObject {
        public Bitmap bitmap;
        public ImageView imageView;

        ResultObject(Bitmap bitmap, ImageView imageView) {
            this.bitmap = bitmap;
            this.imageView = imageView;
        }
    }

}
