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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vivek-pc on 12/14/2015.
 */
public class ImageLoadTask extends BaseTask {

    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;
    private static InternalHandler sHandler;
    private int resourceId = -1;
    private File file;
    private String path;
    private Uri uri;
    private int type;
    private ImageView imageView;
    private String tag;
    private Context context;
    private int height;
    private int width;
    private boolean isLoaded = false;
    private URL url;
    private String cachekey;
    /**
     *  Whether or not to cache this image.
     *  you should pass false for larger size image like full screen image.
     * {@link shouldCache}
     */
    private boolean shouldCache = true;
    private int priorityCount;

    private String futureString;

    private static AtomicInteger counter = new AtomicInteger();

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

//    private DiskLruCache mDiskLruCache;
//    private final Object mDiskCacheLock = new Object();
//    private boolean mDiskCacheStarting = true;
//    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
//    private static final String DISK_CACHE_SUBDIR = "thumbnails";


    public ImageLoadTask() {
//        priorityCount = counter.incrementAndGet();
    }

    public ImageLoadTask(int resourceId, Context context) {
        this();
        this.resourceId = resourceId;
        this.type = 0;
        this.context = context;
    }

    public ImageLoadTask(File file, Context context) {
        this();
        this.file = file;
        this.type = 1;
        this.cachekey = file.getAbsolutePath();
        this.context = context;
        this.tag = file.getAbsolutePath();
    }

    public ImageLoadTask(String path, Context context) {
        this();
        this.path = path;
        this.cachekey = path;
        try {
            this.url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.type = 2;
        this.context = context;
    }

    public ImageLoadTask(Uri uri, Context context) {
        this();
        this.uri = uri;
        this.type = 3;
        this.context = context;
    }

    /**
     * Initial image to be shown while it loads actual image, kind of place holder
     * @param resourceId
     * @return
     */
    public ImageLoadTask initial(int resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    /**
     * Whether you want to keep image in cache or not.
     * should be false for larger image like full screen images.
     * @param shouldCache - true if you want to store image in cache, false otherwise,
     *                    default true;
     * @return ImageLoadTask returns ImageLoadTask
     */
    public ImageLoadTask keepInCache(Boolean shouldCache) {
        this.shouldCache = shouldCache;
        return this;
    }

    public void into(final ImageView image) {
        this.imageView = image;
        ViewGroup.LayoutParams param = imageView.getLayoutParams();
        height = param.height;
        width = param.width;
        if (height == 0 || width == 0 || height == -1 || width == -1) {
            image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {

                    Log.d("vvk", "on global layout" + height + " " + width);
                    // This method was called multiple times..so added this boolean
                    // making sure that only one task is submitted for execution
                    if (!isLoaded) {
                        // Ensure you call it only once :
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        height = image.getHeight();
                        width = image.getWidth();
                        isLoaded = true;
                        handleReqeustType(type);
                    }
                }
            });
        } else {
            handleReqeustType(type);
        }

    }

    private void handleReqeustType(int type) {

        switch (type) {
            case 0:
                imageView.setImageResource(resourceId);
                break;
            case 1:
                if (resourceId != -1)
                    imageView.setImageResource(resourceId);
//                    imageView.setTag(file.getAbsolutePath());
                Bitmap bitmap = getBitmapFromMemCache(file.getAbsolutePath());
                if (bitmap == null) {
                    Log.d("vvk", "cache miss");
                    WorkerClass<ImageLoadTask> worker = new WorkerClass<ImageLoadTask>(ImageLoadTask.this, priorityCount);
                    Log.d("maripriority", "submitting " + priorityCount);
                    Future<?> future = (Future<?>) imageView.getTag();
                    if (future != null && !future.isDone() && !future.isCancelled()) {
                        future.cancel(true);
                        Log.d("vvk", "cancelled");
                    } else {
                        Log.d("vvk", "null or cancelled");
                    }
                    Future<?> f = MyExecutor.getInstance().submit(worker);
                    futureString = f.toString();
                    imageView.setTag(f);
                } else {
                    Log.d("vvk", "cache hit yooooo!!!");
                    imageView.setImageBitmap(bitmap);
                }
                break;
            case 2:
                if (resourceId != -1)
                    imageView.setImageResource(resourceId);
//                    imageView.setTag(file.getAbsolutePath());
                 bitmap = getBitmapFromMemCache(path);
                if (bitmap == null) {
                    Log.d("vvk", "cache miss");
                    WorkerClass<ImageLoadTask> worker = new WorkerClass<ImageLoadTask>(ImageLoadTask.this, priorityCount);
                    Log.d("maripriority", "submitting " + priorityCount);
                    Future<?> future = (Future<?>) imageView.getTag();
                    if (future != null && !future.isDone() && !future.isCancelled()) {
                        future.cancel(true);
                        Log.d("vvk", "cancelled");
                    } else {
                        Log.d("vvk", "null or cancelled");
                    }
                    Future<?> f = MyExecutor.getInstance().submit(worker);
                    futureString = f.toString();
                    imageView.setTag(f);
                } else {
                    Log.d("vvk", "cache hit yooooo!!!");
                    imageView.setImageBitmap(bitmap);
                }
                break;
            case 3:
                break;
            default:
                break;
        }

    }

    @Override
    public void runTask() {
        try {
            Bitmap bitmap = resizeBitmap();
            if(bitmap == null) {
                return;
            }
            if (bitmap != null && shouldCache) {
                addBitmapToMemoryCache(cachekey, bitmap);
            }
            Future<?> f = (Future<?>) imageView.getTag();
//            if (tag.equalsIgnoreCase((String)imageView.getTag())) {
            if (f != null && futureString.equalsIgnoreCase(f.toString())) {
                postResult(bitmap);
            } else {
                Log.d("vvk", "fatal !!! tag didnt match");
                bitmap = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key + height, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key + height);
    }

    /************************
     * Decode and scale file
     *******************/

    public Bitmap resizeBitmap() throws FileNotFoundException {
        Bitmap scaledBitmap = null;
        Bitmap unscaledBitmap = null;
        switch (type) {
            case 0:

                break;
            case 1:
                unscaledBitmap = decodeFile(file.getAbsolutePath(), (int) (width / 2), (int) (height / 2), 1);
                Log.d("vvk", "height1 " + height + " width1 " + width + " " + file.getAbsolutePath() + " height " +unscaledBitmap.getHeight());
                Log.d("vvk", "height2 " + height + " width2 " + width);
                break;
            case 2:
                Log.d("vvk","before unscalled");
                unscaledBitmap = decodeFile(url, (int) (width / 2), (int) (height / 2), 1);
                Log.d("vvk","after unscalled");
                break;
            case 3:
                break;
        }
        if(unscaledBitmap != null) {
            scaledBitmap = createScaledBitmap(unscaledBitmap, (int) (width / 2), (int) (height / 2), 1);
        } else {
            Log.d("vvk","unscalled bitmap null");
        }
        Log.d("vvk", "height " + height + " width " + width);

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

    public static Bitmap decodeFile(URL uri, int dstWidth, int dstHeight, int scalingLogic) {
        Log.d("vvk","decode file url");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        URL url = uri;
        URLConnection conection = null;
        try {
            conection = url.openConnection();
            conection.connect();
        } catch (IOException e) {
            Log.d("vvk","can't connect inputstream");
            e.printStackTrace();
        }

        // this will be useful so that you can show a tipical 0-100%
        // progress bar
//        int lenghtOfFile = conection.getContentLength();

        // download the file
        InputStream input = null;
        InputStream input2 = null;
        try {
            input = url.openStream();
            input2 = url.openStream();
        } catch (IOException e) {
            Log.d("vvk","can't open inputstream");
            e.printStackTrace();
        }
        BitmapFactory.decodeStream(input, null, options);
//        input.
        Log.d("vvk", "decoded first");
//        input.reset()
//        BitmapFactory.decodeFile(pathName, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        Bitmap unscaledBitmap = BitmapFactory.decodeStream(input2,null,options);
        Log.d("vvk","decoded 2nd");
        return unscaledBitmap;
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

    /***************
     * Post result in UI Thread---Handler work
     ***************/

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
